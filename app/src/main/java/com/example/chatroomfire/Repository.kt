package com.example.chatroomfire

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class Repository @Inject constructor(
    private val auth: FirebaseAuth,
    private val deb: DatabaseReference
) {

    @Throws(Exception::class)
    fun createUser(name: String, email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                createNewNodeUser(name,email,password, auth.currentUser!!.uid)
            }else{
                return@addOnCompleteListener
            }
        }
    }

    private fun createNewNodeUser(name: String, email: String, password: String,uid: String) {
        deb.child("Users").child(uid).setValue(UserDataClass(name, email, uid, password))
    }

    fun signInUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
        }
    }

    fun getUserList(callBack: (data: ArrayList<UserDataClass>, msg: String?) -> Unit) {

        val repArrayList: ArrayList<UserDataClass> = arrayListOf()

        deb.child("Users").addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (single in snapshot.children) {
                    val okati = single.getValue(UserDataClass::class.java)
                    if (okati?.uid != auth.currentUser?.uid) {
                        repArrayList.add(okati!!)
                    }
                }
                callBack.invoke(repArrayList, null)
            }

            override fun onCancelled(error: DatabaseError) {
                callBack.invoke(arrayListOf(), error.message)
            }
        })
    }

    fun getDetails(uid: String,callBack: (detail: UserDataClass, mesg: String?) -> Unit){
        val emptyList : UserDataClass = UserDataClass()
        deb.child("Users").child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(UserDataClass::class.java)
                callBack.invoke(data!!,null)
            }

            override fun onCancelled(error: DatabaseError) {
                callBack.invoke(emptyList,error.message)
            }

        })
    }
}