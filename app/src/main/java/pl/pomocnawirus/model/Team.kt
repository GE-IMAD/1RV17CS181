package pl.pomocnawirus.model

import com.google.firebase.firestore.DocumentId
import pl.pomocnawirus.utils.FirestoreUtils

data class Team(
    @DocumentId var id: String = "",
    var name: String = "",
    var city: String = "",
    var leaders: ArrayList<String> = arrayListOf(),
    var email: String = "",
    var phone: String = ""
) {
    fun createTeamHashMap(): HashMap<String, Any> = hashMapOf(
        FirestoreUtils.firestoreKeyName to this.name,
        FirestoreUtils.firestoreKeyCity to this.city,
        FirestoreUtils.firestoreKeyLeaders to this.leaders,
        FirestoreUtils.firestoreKeyEmail to this.email,
        FirestoreUtils.firestoreKeyPhone to this.phone
    )
}

data class TeamSimple(
    @DocumentId var id: String = "",
    var name: String = "",
    var city: String = "",
    var email: String = "",
    var phone: String = ""
)