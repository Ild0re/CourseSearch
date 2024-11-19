package com.innerproject.coursesearch.ui.course

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.innerproject.coursesearch.R
import com.innerproject.coursesearch.databinding.FragmentCourseBinding
import com.innerproject.coursesearch.databinding.FragmentHomeBinding
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CourseFragment : Fragment() {

    companion object {
        const val COURSE_BUNDLE = "course"
        fun newInstance(json: String) = CourseFragment().apply {
            arguments = bundleOf(COURSE_BUNDLE to json)
        }
    }

    private val viewModel by viewModel<CourseViewModel> {
        parametersOf()
    }

    private var _binding: FragmentCourseBinding? = null
    private val binding: FragmentCourseBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val course = createCourseFromJson(arguments?.getString(COURSE_BUNDLE))

        viewModel.getFavourites().observe(viewLifecycleOwner) {

        }

        Glide.with(this)
            .load(course.image)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.image)
        Glide.with(this)
            .load(course.ownerImage)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.ivOwner)
        binding.tvName.text = course.title
        binding.tvDescription.text = Html.fromHtml(course.description)
        binding.owner.text = course.owner

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        checkFavourites(course)

        binding.ibFav.setOnClickListener {
            if (course.inFavourites) {
                viewModel.deleteCourseFromFavourites(course)
                checkFavourites(course)
            } else {
                viewModel.addCourseToFavourites(course)
                checkFavourites(course)
            }
        }

        binding.bRedirect.setOnClickListener {
            if (course.courseUrl != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(course.courseUrl.toString())
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Нет ссылки", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createCourseFromJson(json: String?): Course {
        return Gson().fromJson(json, Course::class.java)
    }

    private fun checkFavourites(course: Course) {
        if (course.inFavourites) {
            binding.ibFav.setImageResource(R.drawable.favourites_fill_icon_24)
        } else {
            binding.ibFav.setImageResource(R.drawable.favourites_layout)
        }
    }
}