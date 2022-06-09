package com.minhdtm.example.we_note.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.minhdtm.example.we_note.data.model.Note
import com.minhdtm.example.we_note.data.model.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FireStoreHelper {
    override fun register(userName: String): Flow<Boolean> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            db.collection("users").document(userName).set(User(userName)).addOnSuccessListener {
                cancellableContinuation.resume(true)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        })
    }

    override fun getNotes(userName: String): Flow<List<Note>> = callbackFlow {
        val listener =
            db.collection("users").document(userName).collection("notes").addSnapshotListener { value, error ->
                if (value != null) {
                    val response = value.toObjects(Note::class.java).mapIndexed { index, note ->
                        Note(
                            id = value.documents[index].id,
                            timeStamp = note?.timeStamp,
                            title = note?.title,
                            description = note?.description,
                        )
                    }

                    trySend(response)
                } else {
                    cancel(CancellationException("API Error", error))
                }
            }

        awaitClose { listener.remove() }
    }

    override fun getNote(userName: String, id: String): Flow<Note> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            db.collection("users").document(userName).collection("notes").document(id).get().addOnSuccessListener {
                val noteResponse = it.toObject(Note::class.java)
                if (noteResponse != null) {
                    val note = noteResponse.copy(id = it.id)
                    cancellableContinuation.resume(note)
                } else {
                    cancellableContinuation.resumeWithException(NullPointerException())
                }
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        })
    }

    override fun addNote(userName: String, title: String, description: String): Flow<Unit> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            val note = Note(
                title = title,
                description = description,
                timeStamp = System.currentTimeMillis(),
            )

            db.collection("users").document(userName).collection("notes").add(note).addOnSuccessListener {
                cancellableContinuation.resume(Unit)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        })
    }

    override fun updateNote(userName: String, id: String, title: String, description: String): Flow<Unit> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            val map = mutableMapOf<String, Any>()
            map["title"] = title
            map["description"] = description
            map["timeStamp"] = System.currentTimeMillis()

            db.collection("users").document(userName).collection("notes").document(id).update(map)
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }.addOnFailureListener {
                    cancellableContinuation.resumeWithException(it)
                }
        })
    }

    override fun getAllUser(): Flow<List<User>> = callbackFlow {
        val listener = db.collection("users").addSnapshotListener { value, error ->
            if (value != null) {
                trySend(value.toObjects(User::class.java))
            } else {
                cancel(CancellationException("API Error", error))
            }
        }
        awaitClose { listener.remove() }
    }

    override fun deleteNote(userName: String, id: String): Flow<Unit> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            db.collection("users").document(userName).collection("notes").document(id).delete().addOnSuccessListener {
                cancellableContinuation.resume(Unit)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        })
    }
}
