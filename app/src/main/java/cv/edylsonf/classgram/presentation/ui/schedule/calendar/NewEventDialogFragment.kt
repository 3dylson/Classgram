package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import cv.edylsonf.classgram.DATE_PICKER_DIALOG
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentNewEventDialogBinding
import cv.edylsonf.classgram.presentation.ui.utils.getColorFromTheme
import cv.edylsonf.classgram.presentation.ui.utils.isDarkTheme
import java.util.*

open class NewEventDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentNewEventDialogBinding
    private var toolbar: ActionBar? = null
    private lateinit var dpd: DatePickerDialog
    private lateinit var tpd: TimePickerDialog

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


        with(binding) {
            tilLocation.setEndIconDrawable(R.drawable.ic_baseline_location_pin)
            tilDate.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
            tilTime.setEndIconDrawable(R.drawable.ic_baseline_clock)
            tilDateTo.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
            tilTimeTo.setEndIconDrawable(R.drawable.ic_baseline_clock)
            tilNotes.setEndIconDrawable(R.drawable.ic_baseline_notes)

            datePicker.setOnClickListener { openDatePicker() }
            timePicker.setOnClickListener { openTimePicker() }
        }

        return binding.root
    }

    private fun openTimePicker() {
        return
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
        dpd.setTitle("Event Date")
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
        binding.datePicker.setText(date)
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        TODO("Not yet implemented")
    }

}