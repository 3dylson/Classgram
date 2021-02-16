package cv.edylsonf.classgram.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.ClassgramApplication
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.presentation.adapters.FeedAdapter
import cv.edylsonf.classgram.presentation.viewmodels.TweetViewModel
import cv.edylsonf.classgram.presentation.viewmodels.TweetViewModelFactory

private const val TAG = "TweetFragment"

class TweetFragment : Fragment(){

    private val viewModel by viewModels<TweetViewModel>() {
        TweetViewModelFactory((requireActivity().application as ClassgramApplication).repositoryPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tweet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        viewModel.loadPost()
    }

    private fun setup(){
        requireView().findViewById<RecyclerView>(R.id.rv_tweets).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FeedAdapter()
        }

        viewModel.postLiveData.observe(viewLifecycleOwner) {
            val adapter = requireView().findViewById<RecyclerView>(R.id.rv_tweets).adapter as FeedAdapter
        adapter.submitList(it)
            }

    }


}