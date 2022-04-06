package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import cv.edylsonf.classgram.DATE_PICKER_DIALOG
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.TIME_PICKER_DIALOG
import cv.edylsonf.classgram.databinding.FragmentNewEventDialogBinding
import cv.edylsonf.classgram.presentation.ui.utils.isDarkTheme
import java.util.*
import kotlin.properties.Delegates


open class NewEventDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentNewEventDialogBinding
    private var toolbar: ActionBar? = null
    private lateinit var tilTime: TextInputLayout
    private lateinit var tilTimeTo: TextInputLayout
    private lateinit var dpd: DatePickerDialog
    private lateinit var tpd: TimePickerDialog
    private var isFrom by Delegates.notNull<Boolean>()

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .show()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = (activity as AppCompatActivity).supportActionBar

        /* Handles system back press */
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewEventDialogBinding.inflate(layoutInflater)
        toolbar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close)
        setHasOptionsMenu(true)


        tilTime = binding.tilTime
        tilTimeTo = binding.tilTimeTo

        with(binding) {
            tilLocation.setEndIconDrawable(R.drawable.ic_baseline_location_pin)
            tilDate.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
            tilTime.setEndIconDrawable(R.drawable.ic_baseline_clock)
            tilDateTo.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
            tilTimeTo.setEndIconDrawable(R.drawable.ic_baseline_clock)
            tilNotes.setEndIconDrawable(R.drawable.ic_baseline_notes)

            datePicker.setOnClickListener {
                isFrom = true
                openDatePicker()
            }
            timePicker.setOnClickListener {
                isFrom = true
                openTimePicker()
            }
            datePickerTo.setOnClickListener {
                isFrom = false
                openDatePicker()
            }
            timePickerTo.setOnClickListener {
                isFrom = false
                openTimePicker()
            }

            allDayChip.setOnClickListener {
                if (allDayChip.isChecked) hideTime()
                else showTime()
            }


        }

        return binding.root
    }

    private fun showTime() {
        tilTime.visibility = View.VISIBLE
        tilTimeTo.visibility = View.VISIBLE
    }

    private fun hideTime() {
        tilTime.visibility = View.INVISIBLE
        tilTimeTo.visibility = View.INVISIBLE
    }

    private fun openTimePicker() {
        val calendar = Calendar.getInstance()
        tpd = TimePickerDialog.newInstance(
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        //tpd.setMinTime(Calendar.HOUR_OF_DAY,Calendar.MINUTE,Calendar.SECOND)
        tpd.isThemeDark = requireActivity().isDarkTheme()
        tpd.show(parentFragmentManager, TIME_PICKER_DIALOG)

    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        dpd.minDate = calendar
        /*// Setting Max Date to next 2 years
        calendar.set(Calendar.YEAR, calendar. + 2)
        dpd.maxDate = calendar*/
        dpd.isThemeDark = requireActivity().isDarkTheme()
        dpd.showYearPickerFirst(false)
        //dpd.setTitle("Event Date")
        dpd.show(parentFragmentManager, DATE_PICKER_DIALOG)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_event_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /* Handles toolbar back press */
            android.R.id.home -> onBackPressed()
            R.id.saveEvent -> saveEvent()
        }
        return true
    }

    open fun onBackPressed() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Discard draft?")
            .setPositiveButton("Discard") { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton("Cancel") { _, _ ->
            }
            .show()
    }

    private fun saveEvent() {
        return
    }


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date: String = "$dayOfMonth" + "/" + (monthOfYear+1) + "/" + year
        if (isFrom) binding.datePicker.setText(date)
        else binding.datePickerTo.setText(date)
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        val hourString: String = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
        val minuteString: String = if (minute < 10) "0$minute" else "$minute"
        val time: String = hourString + "h" + minuteString + "m"
        if (isFrom) binding.timePicker.setText(time)
        else binding.timePickerTo.setText(time)
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStop() {
        hideKeyboardFrom(requireContext(),binding.root)
        super.onStop()
    }

}