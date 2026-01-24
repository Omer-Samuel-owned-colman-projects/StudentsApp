package com.example.studentsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.FragmentStudentsListBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentsListFragment : Fragment() {

    private var _binding: FragmentStudentsListBinding? = null
    private val binding get() = _binding!!
    private var adapter: StudentRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentsRecyclerView.setHasFixedSize(true)
        binding.studentsRecyclerView.layoutManager = LinearLayoutManager(context)
        
        adapter = StudentRecyclerAdapter(Model.instance.getAllStudents())
        adapter?.listener = object : StudentRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("TAG", "On click listener on position $position")
            }

            override fun onStudentClick(student: Student) {
                Log.d("TAG", "Clicked on student: ${student.name}")
                val action = StudentsListFragmentDirections.actionStudentsListFragmentToStudentDetailsFragment(student.id)
                findNavController().navigate(action)
            }
        }
        binding.studentsRecyclerView.adapter = adapter

        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.add(0, R.id.add_student_action, 0, "Add").setIcon(android.R.drawable.ic_menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                 return if (menuItem.itemId == R.id.add_student_action) {
                     findNavController().navigate(R.id.action_studentsListFragment_to_newStudentFragment)
                     true
                 } else {
                     false
                 }
            }
        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()
        adapter?.students = Model.instance.getAllStudents()
        adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
