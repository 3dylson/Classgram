package cv.edylsonf.classgram.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cv.edylsonf.classgram.presentation.ui.profile.AboutTabFragment
import cv.edylsonf.classgram.presentation.ui.profile.PostsTabFragment

class ProfileTabAdapter(fragmentManager: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0    -> PostsTabFragment()
            1    -> AboutTabFragment()
            else -> PostsTabFragment()
        }
    }
}