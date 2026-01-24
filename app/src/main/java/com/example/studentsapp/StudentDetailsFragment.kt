package com.example.studentsapp

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.example.studentsapp.databinding.FragmentStudentDetailsBinding
import com.example.studentsapp.model.Model

class StudentDetailsFragment : Fragment() {

    private var _binding: FragmentStudentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: StudentDetailsFragmentArgs by navArgs()
    private var studentId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentId = args.studentId
        disableForm()
        loadStudentData()


        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.add(0, R.id.edit_student_action, 0, "Edit").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.edit_student_action) {
                     val action = StudentDetailsFragmentDirections.actionStudentDetailsFragmentToEditStudentFragment(studentId ?: "")
                     findNavController().navigate(action)
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
        loadStudentData()
    }

    private fun loadStudentData() {
        val student = Model.instance.getStudentById(studentId ?: "")
        if (student != null) {
            binding.studentDetailsForm.newStudentNameEt.setText(student.name)
            binding.studentDetailsForm.newStudentIdEt.setText(student.idNumber)
            binding.studentDetailsForm.newStudentPhoneEt.setText(student.phone)
            binding.studentDetailsForm.newStudentAddressEt.setText(student.address)
            binding.studentDetailsForm.newStudentCheckedCb.isChecked = student.isChecked
            binding.studentDetailsForm.newStudentBirthDateEt.setText(student.birthDate)
            binding.studentDetailsForm.newStudentBirthTimeEt.setText(student.birthTime)
        } else {
             findNavController().popBackStack()
        }
    }

    private fun disableForm() {
        binding.studentDetailsForm.newStudentNameEt.isEnabled = false
        binding.studentDetailsForm.newStudentIdEt.isEnabled = false
        binding.studentDetailsForm.newStudentPhoneEt.isEnabled = false
        binding.studentDetailsForm.newStudentAddressEt.isEnabled = false
        binding.studentDetailsForm.newStudentCheckedCb.isEnabled = false
        binding.studentDetailsForm.newStudentBirthDateEt.isEnabled = false
        binding.studentDetailsForm.newStudentBirthDateTil.isEnabled = false
        
        binding.studentDetailsForm.newStudentNameEt.setTextColor(resources.getColor(android.R.color.black, null))
        binding.studentDetailsForm.newStudentIdEt.setTextColor(resources.getColor(android.R.color.black, null))
        binding.studentDetailsForm.newStudentPhoneEt.setTextColor(resources.getColor(android.R.color.black, null))
        binding.studentDetailsForm.newStudentAddressEt.setTextColor(resources.getColor(android.R.color.black, null))
        binding.studentDetailsForm.newStudentBirthDateEt.setTextColor(resources.getColor(android.R.color.black, null))
        binding.studentDetailsForm.newStudentBirthTimeEt.setTextColor(resources.getColor(android.R.color.black, null))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
