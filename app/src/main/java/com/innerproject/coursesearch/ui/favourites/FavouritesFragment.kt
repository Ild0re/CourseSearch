package com.innerproject.coursesearch.ui.favourites

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.FragmentCourseBinding
import com.innerproject.coursesearch.databinding.FragmentFavouritesBinding
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.ui.course.CourseViewModel
import com.innerproject.coursesearch.ui.home.HomeAdapter
import com.innerproject.coursesearch.utils.CourseState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavouritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    private val viewModel by viewModel<FavouritesViewModel> {
        parametersOf()
    }

    private var _binding: FragmentFavouritesBinding? = null
    private val binding: FragmentFavouritesBinding
        get() = _binding!!

    private val courseList = ArrayList<Course>()
    private val courseAdapter = FavouritesAdapter(courseList, ::onItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = courseAdapter

        viewModel.getCourses()
        courseAdapter.notifyDataSetChanged()
        viewModel.getObserve().observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.getFavourites().observe(viewLifecycleOwner) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCourses()
        courseAdapter.notifyDataSetChanged()
    }

    private fun render(state: CourseState) {
        when(state) {
            is CourseState.Empty -> {
                courseList.clear()
            }
            is CourseState.Content -> {
                courseList.clear()
                courseList.addAll(state.courseList)
                courseAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onItemClickListener(course: Course) {
        if (course.inFavourites) {
            viewModel.deleteCourseFromFavourites(course)
        } else {
            viewModel.addCourseToFavourites(course)
        }
        viewModel.getCourses()
        courseAdapter.notifyDataSetChanged()
    }
}