package com.example.a2project

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2project.databinding.FragmentEventListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class EventListFragment : Fragment() {
    private var _binding: FragmentEventListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?" }

    private val eventListViewModel: EventListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)



        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_event_list, menu)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                eventListViewModel.events.collect {events ->
                    binding.eventRecyclerView.adapter =
                        EventListAdapter(events){eventId->
                            findNavController().navigate(
                                EventListFragmentDirections.showEventDetail(eventId)
                            )
                        }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_event -> {
                showNewEvent()
                true }
            else -> super.onOptionsItemSelected(item) }
    }
    //TODO
    private fun showNewEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newEvent = Event(
                id = UUID.randomUUID(),
                type = "",
                title = "",
                location = "",
                date = "",
                attending = "",
                attended = false
            )
            eventListViewModel.addEvent(newEvent)
            findNavController().navigate(
                EventListFragmentDirections.showEventDetail(newEvent.id)
            )
        }
    }

}