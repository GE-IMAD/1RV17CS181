package pl.pomocnawirus.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.content_task_filter_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_task_filter_bottom_sheet.view.*
import pl.pomocnawirus.R
import pl.pomocnawirus.model.Task
import pl.pomocnawirus.utils.Filters
import pl.pomocnawirus.utils.collapse
import pl.pomocnawirus.utils.expand
import pl.pomocnawirus.utils.setLayoutFullHeight
import pl.pomocnawirus.view.adapters.FilterRecyclerAdapter
import pl.pomocnawirus.viewmodel.TasksViewModel

class TaskFilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mViewModel: TasksViewModel
    private lateinit var mAdapter: FilterRecyclerAdapter
    private lateinit var mFilters: Filters

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view = View.inflate(requireContext(), R.layout.fragment_task_filter_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setOnShowListener { dialog ->
            val bottomSheet = (dialog as BottomSheetDialog)
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            requireActivity().setLayoutFullHeight(bottomSheet)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        mViewModel = ViewModelProvider(requireActivity()).get(TasksViewModel::class.java)
        mFilters = mViewModel.filters.value!!
        view.taskStatusActiveTasks.isChecked = mFilters.selectedTaskStatus == Task.TASK_STATUS_ADDED
        view.taskStatusMyTasks.isChecked = mFilters.selectedTaskStatus != Task.TASK_STATUS_ADDED

        mAdapter = FilterRecyclerAdapter(mFilters.selectedTaskTypes)
        view.filterTaskTypeOptions.apply {
            layoutManager = LinearLayoutManager(view.context)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        view.taskStatusActiveTasks.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mFilters.selectedTaskStatus = Task.TASK_STATUS_ADDED
                view.taskStatusMyTasks.isChecked = false
            } else if (!view.taskStatusMyTasks.isChecked)
                view.taskStatusMyTasks.isChecked = true
        }
        view.taskStatusMyTasks.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mFilters.selectedTaskStatus = Task.TASK_STATUS_ACCEPTED
                view.taskStatusActiveTasks.isChecked = false
            } else if (!view.taskStatusActiveTasks.isChecked)
                view.taskStatusActiveTasks.isChecked = true
        }
        view.filterTaskStatusHeader.setOnClickListener {
            if (view.filterTaskStatusOptions.visibility == View.GONE) {
                view.filterTaskStatusOptions.expand()
                view.filterTaskStatusBtn.animate().rotation(180f).duration = 300
            } else {
                view.filterTaskStatusOptions.collapse()
                view.filterTaskStatusBtn.animate().rotation(0f).duration = 300
            }
        }
        view.filterTaskTypeHeader.setOnClickListener {
            if (view.filterTaskTypeOptions.visibility == View.GONE) {
                view.filterTaskTypeOptions.expand()
                view.filterTaskTypeBtn.animate().rotation(180f).duration = 300
            } else {
                view.filterTaskTypeOptions.collapse()
                view.filterTaskTypeBtn.animate().rotation(0f).duration = 300
            }
        }

        view.toolbarCancelBtn.setOnClickListener { dismiss() }
        view.toolbarSaveFilterBtn.setOnClickListener {
            mFilters.selectedTaskTypes = mAdapter.taskTypes
            mViewModel.filters.postValue(mFilters)
            dismiss()
        }

        return bottomSheetDialog
    }
}
