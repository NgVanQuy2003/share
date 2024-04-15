package com.example.share

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.share.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var studentListAdapter: ArrayAdapter<String>
    private val studentsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("students_prefs", Context.MODE_PRIVATE)

        studentListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentsList)
        binding.listViewStudents.adapter = studentListAdapter

        binding.btnSave.setOnClickListener {
            saveStudent()
        }

        loadStudents()
    }

    private fun saveStudent() {
        val name = binding.etName.text.toString()
        val dateOfBirth = binding.etDateOfBirth.text.toString()
        val age = binding.etAge.text.toString()
        val gender = binding.etGender.text.toString()

        if (name.isNotEmpty() && dateOfBirth.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()) {
            val studentInfo = "Tên: $name, Ngày sinh: $dateOfBirth, Tuổi: $age, Giới tính: $gender"
            studentsList.add(0, studentInfo)
            studentListAdapter.notifyDataSetChanged()

            // Lưu danh sách học sinh vào SharedPreferences
            saveStudentsToSharedPreferences()

            // Xóa nội dung trong các EditText
            binding.etName.text.clear()
            binding.etDateOfBirth.text.clear()
            binding.etAge.text.clear()
            binding.etGender.text.clear()
        }
    }

    private fun loadStudents() {
        // Load danh sách học sinh từ SharedPreferences
        val savedStudents = sharedPreferences.getStringSet("students", HashSet()) ?: HashSet()
        studentsList.addAll(savedStudents)
        studentListAdapter.notifyDataSetChanged()
    }

    private fun saveStudentsToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("students", HashSet(studentsList))
        editor.apply()
    }
}
