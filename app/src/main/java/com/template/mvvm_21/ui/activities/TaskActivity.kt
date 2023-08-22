package com.template.mvvm_21.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.template.mvvm_21.R
import com.template.mvvm_21.model.Task
import com.template.mvvm_21.ui.viewModel.ViewModel
import com.template.mvvm_21.databinding.ActivityTaskBinding

@Suppress("DEPRECATION")
class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: ViewModel
    private var task: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        task = intent.getSerializableExtra(MainActivity.UPDATE_TASK_KEY) as Task?
        if (task == null) {
            createTaskView()
        } else {
            editTaskView()
        }
    }

    private fun createTaskView() {
        binding.buttonSave.setOnClickListener {
            val data = Task(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
            )
            viewModel.addTask(data)
            finish()
        }
    }

    private fun editTaskView() {
        with(binding) {
            etTitle.setText(task?.title)
            etDescription.setText(task?.description)
            buttonSave.text = getString(R.string.update)
            buttonSave.setOnClickListener {
                val data = task!!.copy(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                )
                viewModel.updateTask(data)
                finish()
            }
        }
    }
}
