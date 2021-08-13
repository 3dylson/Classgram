package cv.edylsonf.classgram.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cv.edylsonf.classgram.PROFILE_TAB_PAGES
import cv.edylsonf.classgram.presentation.ui.profile.AboutTabFragment
import cv.edylsonf.classgram.presentation.ui.profile.PostsTabFragment

class ProfileTabAdapter(f: Fragment) :
    FragmentStateAdapter(f) {


    override fun getItemCount(): Int = PROFILE_TAB_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostsTabFragment()
            1 -> AboutTabFragment()
            else -> PostsTabFragment()
        }
    }

}