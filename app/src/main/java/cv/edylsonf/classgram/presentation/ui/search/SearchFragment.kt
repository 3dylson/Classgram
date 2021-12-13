package cv.edylsonf.classgram.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import cv.edylsonf.classgram.EXTRA_TAB_TITLE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentSearchBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment


class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var filterItem: MenuItem
    private var fragTitle: String? = null
    private var toolbar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragTitle = it.getString(EXTRA_TAB_TITLE)
        }

        toolbar = (activity as AppCompatActivity).supportActionBar
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        toolbar?.title = fragTitle

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        toolbar?.title = fragTitle
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seacrh_menu, menu)

        filterItem = menu.findItem(R.id.action_open_filters)

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView

        // Configure the search info and add any event listeners...
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                filterItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                filterItem.isVisible = false
                return true
            }

        }

        searchItem.setOnActionExpandListener(expandListener)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_open_filters -> openFilter()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFilter() {
        return
        TODO("Not yet implemented")
    }
}