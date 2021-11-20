
package cv.edylsonf.classgram.presentation.ui.schedule
/*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.databinding.ItemScheduleDayIndicatorBinding


class DayIndicatorAdapter(
    private val scheduleViewModel: ScheduleViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<DayIndicator, DayIndicatorViewHolder>(IndicatorDiff) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).day.start.toEpochSecond()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayIndicatorViewHolder {
        val binding = ItemScheduleDayIndicatorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DayIndicatorViewHolder(binding, scheduleViewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: DayIndicatorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DayIndicatorViewHolder(
    private val binding: ItemScheduleDayIndicatorBinding,
    private val scheduleViewModel: ScheduleViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DayIndicator) {
        binding.executeAfter {
            indicator = item
            viewModel = scheduleViewModel
            lifecycleOwner = this@DayIndicatorViewHolder.lifecycleOwner
        }
    }
}

object IndicatorDiff : DiffUtil.ItemCallback<DayIndicator>() {
    override fun areItemsTheSame(oldItem: DayIndicator, newItem: DayIndicator) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DayIndicator, newItem: DayIndicator) =
        oldItem.areUiContentsTheSame(newItem)
}

@BindingAdapter("indicatorText", "inConferenceTimeZone", requireAll = true)
fun setIndicatorText(
    view: TextView,
    dayIndicator: DayIndicator,
    inConferenceTimeZone: Boolean
) {
    view.setText(TimeUtils.getShortLabelResForDay(dayIndicator.day, inConferenceTimeZone))
}*/
