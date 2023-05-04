package com.example.a2project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.a2project.databinding.FragmentEventDetailBinding
import kotlinx.coroutines.launch
import java.util.*

//private const val TAG = "EventDetailFragment"

class EventDetailFragment : Fragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?" }
    //private lateinit var event: Event

    private val args: EventDetailFragmentArgs by navArgs()

    private val eventDetailViewModel: EventDetailViewModel by viewModels {
        EventDetailViewModelFactory(args.eventId)
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event = Event (
            id = UUID.randomUUID(),
            type = "",
            title = "",
            location = "",
            date = "",
            attending = "",
            attended = false
        )
        Log.d(TAG, "The event ID is: ${args.eventId}")
    }

     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { _binding =
        FragmentEventDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            eventType.doOnTextChanged { text, _, _, _ ->
                eventDetailViewModel.updateEvent { oldEvent->
                    oldEvent.copy(type = text.toString())
                }
            }

            eventTitle.doOnTextChanged { text, _, _, _ ->
                eventDetailViewModel.updateEvent { oldEvent->
                    oldEvent.copy(title = text.toString())
                }
            }

            eventLocation.doOnTextChanged { text, _, _, _ ->
                eventDetailViewModel.updateEvent { oldEvent->
                    oldEvent.copy(location = text.toString())
                }
            }

            eventDate.doOnTextChanged { text, _, _, _ ->
                eventDetailViewModel.updateEvent { oldEvent->
                    oldEvent.copy(date = text.toString())
                }
            }

            eventAttending.doOnTextChanged { text, _, _, _ ->
                eventDetailViewModel.updateEvent { oldEvent->
                    oldEvent.copy(attending = text.toString())
                }
            }

            eventAttended.setOnCheckedChangeListener { _, isAttended ->
                eventDetailViewModel.updateEvent { oldEvent ->
                    oldEvent.copy(attended = isAttended)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                eventDetailViewModel.event.collect{event->
                    event?.let {updateUi(it)}
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(event: Event){
        binding.apply {
            if (eventType.text.toString() != event.type) {
                eventType.setText(event.type) }
            if (eventTitle.text.toString() != event.title) {
                eventTitle.setText(event.title) }
            if (eventLocation.text.toString() != event.location) {
                eventLocation.setText(event.location) }
            if (eventDate.text.toString() != event.date) {
                eventDate.setText(event.date) }
            if (eventAttending.text.toString() != event.attending) {
                eventAttending.setText(event.attending) }
            eventAttended.isChecked = event.attended
        }
    }
}