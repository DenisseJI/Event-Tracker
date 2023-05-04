package com.example.a2project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2project.databinding.ListItemEventBinding
import java.util.*

class EventHolder (
    private val binding: ListItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event, onEventClicked: (eventId: UUID) -> Unit) {
        binding.eventType.text = event.type
        binding.eventTitle.text = event.title
        binding.eventLocation.text = event.location
        binding.eventDate.text = event.date
        binding.eventAttending.text = event.attending
        binding.eventAttended.visibility = if (event.attended) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.eventAttended1.visibility = if (event.attended) {
            View.GONE
        } else {
            View.VISIBLE
        }
        binding.root.setOnClickListener {
            onEventClicked(event.id)
        }
    }
    }

class EventListAdapter(
    private val events: List<Event>,
    private val onEventClicked: (eventId: UUID) -> Unit
) : RecyclerView.Adapter<EventHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemEventBinding.inflate(inflater, parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.bind(event, onEventClicked)
    }
    override fun getItemCount() = events.size
}
