package cv.edylsonf.classgram.presentation.ui.schedule.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.databinding.ItemEventBinding
import cv.edylsonf.classgram.domain.models.Event
import kotlinx.coroutines.flow.StateFlow
import java.time.ZoneId

class EventsAdapter(
    private val timeZoneId: StateFlow<ZoneId>,
    private val lifecycleOwner: LifecycleOwner,
    private val onEventClickListener: OnEventClickListener
) : ListAdapter<Event, EventsAdapter.EventViewHolder>(EventDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply {
            timeZoneId = this@EventsAdapter.timeZoneId
            lifecycleOwner = this@EventsAdapter.lifecycleOwner
            eventClickListener = onEventClickListener

        }
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.binding.event = getItem(position)
        holder.binding.executePendingBindings()
    }

    class EventViewHolder(
        internal val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root)

    object EventDiff : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }


}