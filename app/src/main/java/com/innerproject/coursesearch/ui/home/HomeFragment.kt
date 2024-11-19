package com.innerproject.coursesearch.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.FragmentHomeBinding
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by viewModel<HomeViewModel> {
        parametersOf()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val courseList = ArrayList<Course>()
    private val courseAdapter = HomeAdapter(courseList, ::onItemClickListener)
    private var pageNumber = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = courseAdapter

        binding.tvSort.text = getString(R.string.by_date)
        viewModel.loadData(pageNumber)
        courseAdapter.notifyDataSetChanged()
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.getFavourites().observe(viewLifecycleOwner) {}

        binding.rv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                if (layoutManager.findLastCompletelyVisibleItemPosition() == totalItemCount - 1 && dy >= 0) {
                    pageNumber += 1
                    viewModel.loadNewData(pageNumber)
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(pageNumber)
        courseAdapter.notifyDataSetChanged()
    }

    private fun render(state: ScreenState) {
        when (state) {
            is ScreenState.Loading -> showLoading()
            is ScreenState.Content -> {
                showData(state.data)
            }

            is ScreenState.Empty -> {

            }

            is ScreenState.Error -> {

            }
        }
    }

    private fun showLoading() {
        binding.rv.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showData(data: List<Course>) {
        binding.progressBar.visibility = View.GONE
        binding.rv.visibility = View.VISIBLE
        courseList.addAll(data)
        courseAdapter.notifyDataSetChanged()
    }

    private fun onItemClickListener(course: Course) {
        if (course.inFavourites) {
            viewModel.deleteCourseFromFavourites(course)
        } else {
            viewModel.addCourseToFavourites(course)
        }
        courseAdapter.notifyDataSetChanged()
    }
}