package cv.edylsonf.classgram.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cv.edylsonf.classgram.ui.login.LoginTabFragment
import cv.edylsonf.classgram.ui.login.SignupTabFragment


class LoginAdapter(fragmentManager: FragmentManager, private var tabCount: Int) :
        FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
         return when (position) {
            0 -> {
                LoginTabFragment()
            }
            1 -> {
                SignupTabFragment()
            }
            else -> return Fragment(POSITION_NONE)
        }
    }




}



