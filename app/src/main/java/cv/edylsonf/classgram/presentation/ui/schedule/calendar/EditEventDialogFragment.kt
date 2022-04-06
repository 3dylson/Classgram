package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditEventDialogFragment : NewEventDialogFragment() {

    private val args: EditEventDialogFragmentArgs by navArgs()
    private lateinit var eventId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO fill the fields with event data
        eventId = args.eventId
    }

    override fun onBackPressed() {
        if (hasChange()) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("Discard your changes?")
                .setPositiveButton("Discard") { _, _ ->
                    findNavController().navigateUp()
                }
                .setNegativeButton("Cancel") { _, _ ->
                }
                .show()
        }
        else {
            findNavController().navigateUp()
        }
    }

    private fun hasChange(): Boolean {
        return false
    }
}