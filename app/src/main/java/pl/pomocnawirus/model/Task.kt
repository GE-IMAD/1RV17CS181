package pl.pomocnawirus.model

import com.google.firebase.Timestamp
import pl.pomocnawirus.R

data class Task(
    var id: String = "",
    var type: String = TASK_TYPE_OTHER,
    var description: String = "",
    var status: Int = 0,
    var realizationDate: Timestamp? = null,
    var volunteerId: String = ""
) {
    companion object {
        const val TASK_TYPE_SHOPPING = "SHOPPING"
        const val TASK_TYPE_PETS = "PETS"
        const val TASK_TYPE_HOME = "HOME"
        const val TASK_TYPE_OTHER = "OTHER"

        const val TASK_STATUS_ADDED = 0
        const val TASK_STATUS_ACCEPTED = 1
        const val TASK_STATUS_COMPLETE = 2
    }

    fun getIconDrawableId() = when (type) {
        TASK_TYPE_SHOPPING -> R.drawable.ic_task_shopping
        TASK_TYPE_PETS -> R.drawable.ic_task_pets
        TASK_TYPE_HOME -> R.drawable.ic_task_home
        else -> R.drawable.ic_task_other
    }
}