package com.example.greentea.fteam.signIn

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


open class SignInStatus {
    companion object {
        /** SignInしているかどうか */
        var isSignIn: Boolean = false
        /** どのユーザーでSignInしているか */
        var mUser: FirebaseUser? = null

        /** SignIn Status Check */
        fun isSignIn(user: FirebaseUser?) {
            Log.d("unchi", "isSignIn")
            if (user != null) {
                Log.d("unchi", "isSignIn True")
                isSignIn = true
                mUser = user
            } else {
                Log.d("unchi", "isSignIn False")
                isSignIn = false
                mUser = null
            }
        }

        /** Sign Out */
        fun signOut(){
            FirebaseAuth.getInstance().signOut()
            isSignIn = false
            mUser = null
        }
    }

}