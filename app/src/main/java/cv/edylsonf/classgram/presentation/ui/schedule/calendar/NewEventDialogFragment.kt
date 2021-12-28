package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentNewEventDialogBinding

class NewEventDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentNewEventDialogBinding
    private var toolbar: ActionBar? = null

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .show()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = (activity as AppCompatActivity).supportActionBar

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewEventDialogBinding.inflate(layoutInflater)
        toolbar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close)
        binding.tilLocation.setEndIconDrawable(R.drawable.ic_baseline_location_pin)
        binding.tilDate.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
        binding.tilTime.setEndIconDrawable(R.drawable.ic_baseline_clock)
        binding.tilDateTo.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
        binding.tilTimeTo.setEndIconDrawable(R.drawable.ic_baseline_clock)
        binding.tilNotes.setEndIconDrawable(R.drawable.ic_baseline_notes)
        return binding.root
    }

}