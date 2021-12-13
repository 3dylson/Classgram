package cv.edylsonf.classgram.presentation.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import cv.edylsonf.classgram.presentation.ui.home.HomeFragment

private const val TAG = "PostsTabFragment"

class PostsTabFragment : HomeFragment() {

    /*override fun loadPosts() {
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    0 -> {
                        fab.show()
                        bottomAppBar.performShow()
                    }
                    else -> {
                        fab.hide()
                        bottomAppBar.performHide()
                    }

                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return
    }

    override fun setQuery() {
        query = database.collection("posts")
            .limit(20)
            .orderBy("creationTime", Query.Direction.DESCENDING)
            .whereEqualTo("user.uid", uid)

    }
}