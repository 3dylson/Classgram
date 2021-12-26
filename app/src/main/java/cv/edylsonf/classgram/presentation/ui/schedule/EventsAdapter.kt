package cv.edylsonf.classgram.presentation.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.databinding.ItemEventViewBinding
import cv.edylsonf.classgram.domain.models.Event

class EventsAdapter(val onClick: (Event) -> Unit) :
RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    val events = mutableListOf<Event>()


    inner class EventsViewHolder(private val binding: ItemEventViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        fun bind(event: Event) {
            binding.itemEventText.text = event.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        return EventsViewHolder(
            ItemEventViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

}