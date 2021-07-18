@file:Suppress("UNUSED_VARIABLE", "UNUSED_ANONYMOUS_PARAMETER")

package cv.edylsonf.classgram.presentation.ui.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class DocSnippets (val db: FirebaseFirestore){

    companion object{

        private val TAG = "DocSnippets"

        private val EXECUTOR = ThreadPoolExecutor(2, 4,
            60, TimeUnit.SECONDS, LinkedBlockingQueue()
        )
    }


    internal fun runAll() {
        Log.d(TAG, "================= BEGIN RUN ALL ===============")

        // Write example data

        // Run all other methods
        addAdaLovelace()
        addAlanTuring()
        getAllUsers()
        docReference()
        collectionReference()
        subcollectionReference()

        // Run methods that should fail

    }

    private fun setup() {
        // [START get_firestore_instance]
        val db = Firebase.firestore
        // [END get_firestore_instance]
        // [START set_firestore_settings]
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
        // [END set_firestore_settings]
    }

    private fun setupCacheSize() {
        // [START fs_setup_cache]
        val settings = firestoreSettings {
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
        db.firestoreSettings = settings
        // [END fs_setup_cache]
    }

    private fun addAdaLovelace() {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        // [END add_ada_lovelace]
    }

    private fun addAlanTuring() {
        // [START add_alan_turing]
        // Create a new user with a first, middle, and last name
        val user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        // [END add_alan_turing]
    }

    private fun getAllUsers() {
        // [START get_all_users]
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        // [END get_all_users]
    }

    private fun docReference() {
        // [START doc_reference]
        val alovelaceDocumentRef = db.collection("users").document("alovelace")
        // [END doc_reference]
    }

    private fun collectionReference() {
        // [START collection_reference]
        val usersCollectionRef = db.collection("users")
        // [END collection_reference]
    }

    fun docReferenceAlternate() {
        // [START doc_reference_alternate]
        val alovelaceDocumentRef = db.document("users/alovelace")
        // [END doc_reference_alternate]
    }

    private fun subcollectionReference() {
        // [START subcollection_reference]
        val messageRef = db
            .collection("rooms").document("roomA")
            .collection("messages").document("message1")
        // [END subcollection_reference]
    }


}