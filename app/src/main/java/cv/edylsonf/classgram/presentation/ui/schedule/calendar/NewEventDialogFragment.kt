package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        binding.tilLocation.setEndIconDrawable(R.drawable.ic_baseline_location_pin)
        binding.tilDate.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
        binding.tilTime.setEndIconDrawable(R.drawable.ic_baseline_clock)
        binding.tilDateTo.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
        binding.tilTimeTo.setEndIconDrawable(R.drawable.ic_baseline_clock)
        binding.tilNotes.setEndIconDrawable(R.drawable.ic_baseline_notes)
        return binding.root
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

    private fun onBackPressed() {
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

}