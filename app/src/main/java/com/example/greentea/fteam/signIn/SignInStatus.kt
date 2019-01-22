package com.example.greentea.fteam.signIn

import com.example.greentea.fteam.`object`.UserObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


open class SignInStatus {
    companion object {
        /** SignInしているかどうか */
        var isSignIn: Boolean = false
        /** どのユーザーでSignInしているか */
        var mUser: FirebaseUser? = null
        /** ユーザー情報 */
        var mUserID: String = ""
        var mUserName: String = ""
        /** フォロワーリスト */
        var followerList: ArrayList<String> = ArrayList()

        /** SignIn Status Check */
        fun isSignIn(user: FirebaseUser?) {
            if (user != null) {
                isSignIn = true
                mUser = user
                mUserID = mUser!!.uid
                mUser!!.displayName?.let {
                    mUserName = it

                }
                FirebaseFirestore.getInstance().collection("user")
                        .document(mUserID)
                        .get()
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                try {
                                    task.result?.let { doc ->
                                        followerList = doc.toObject(UserObject::class.java)!!.followerID
                                    }
                                } catch (e: Exception){

                                }
                            }


                        }
            } else {
                isSignIn = false
                mUser = null
                mUserID = ""
                mUserName = ""
                followerList = ArrayList()
            }
        }

        /** Sign Out */
        fun signOut() {
            FirebaseAuth.getInstance().signOut()
            isSignIn = false
            mUser = null
            mUserID = ""
            mUserName = ""
            followerList = ArrayList()
        }
    }

}