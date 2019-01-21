package com.example.greentea.fteam.signIn

import com.example.greentea.fteam.`object`.UserObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


open class SignInStatus {
    companion object {
        /** SignInしているかどうか */
        var isSignIn: Boolean = false
        /** どのユーザーでSignInしているか */
        var mUser: FirebaseUser? = null
        /** ユーザー情報 */
        var mUserID:String = ""
        var mUserName:String = ""
        /** フォロワーリスト */
        var followerList:ArrayList<String> = ArrayList()

        /** SignIn Status Check */
        fun isSignIn(user: FirebaseUser?) {
            if (user != null) {
                isSignIn = true
                mUser = user
                mUserID = mUser!!.uid
                mUserName = mUser!!.displayName!!
                FirebaseFirestore.getInstance().collection("user")
                        .document(mUserID)
                        .get()
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                followerList = it.result!!.toObject(UserObject::class.java)!!.followerID
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
        fun signOut(){
            FirebaseAuth.getInstance().signOut()
            isSignIn = false
            mUser = null
            mUserID = ""
            mUserName = ""
            followerList = ArrayList()
        }
    }

}