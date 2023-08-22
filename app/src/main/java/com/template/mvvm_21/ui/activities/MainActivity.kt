package com.template.mvvm_21.ui.activities

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.template.mvvm_21.databinding.ActivityMainBinding
import com.template.mvvm_21.model.Task
import com.template.mvvm_21.ui.adapter.TaskAdapter
import com.template.mvvm_21.ui.viewModel.ViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private val adapter = TaskAdapter(
        this::onLongClickTask,
        this::isCheckedTask,
        this::onClickTask,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        binding.recyclerView.adapter = adapter
        initView()
        initClick()
        initSpinner()
    }

    private fun initClick() {
        binding.btnTask.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        viewModel.list.observe(this) { updatedList ->
            binding.recyclerView.adapter = adapter
            adapter.setTask(updatedList)
        }
    }

    private fun onLongClickTask(task: Task) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(com.template.mvvm_21.R.string.are_you_wont_delete))
            .setMessage(getString(com.template.mvvm_21.R.string.alertdialog_message))
            .setPositiveButton(getString(com.template.mvvm_21.R.string.ok)) { dialog: DialogInterface, _: Int ->
                viewModel.deleteTask(task)
                adapter.deleteTask(task)
                dialog.dismiss()
            }
            .setNegativeButton(getString(com.template.mvvm_21.R.string.cancel)) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        dialogBuilder.show()
    }

    private fun onClickTask(task: Task) {
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra(UPDATE_TASK_KEY, task)
        startActivity(intent)
    }

    private fun isCheckedTask(task: Task, isChecked: Boolean = false) {
        viewModel.checkedTask(task, isChecked)
    }

    private fun initSpinner() {
        val taskFilterList = arrayOf(
            getString(com.template.mvvm_21.R.string.all_task),
            getString(com.template.mvvm_21.R.string.false_task),
            getString(com.template.mvvm_21.R.string.true_task)
        )

        val adapterSpinner = ArrayAdapter(this, R.layout.simple_spinner_item, taskFilterList)
        adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (taskFilterList[position]) {
                    getString(com.template.mvvm_21.R.string.all_task) -> viewModel.getTask()
                    getString(com.template.mvvm_21.R.string.false_task) -> viewModel.filtrateFalseTasks()
                    getString(com.template.mvvm_21.R.string.true_task) -> viewModel.filtrateTrueTasks()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.getTask()
            }
        }
    }

    companion object {
        const val UPDATE_TASK_KEY = "update_task.key"
    }

    override fun onResume() {
        super.onResume()
        initSpinner()
    }
}
