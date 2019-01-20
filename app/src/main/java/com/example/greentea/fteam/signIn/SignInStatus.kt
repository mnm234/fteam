package com.example.greentea.fteam.signIn

import android.util.Log
import com.google.firebase.auth.FirebaseUser


open class SignInStatus {
    companion object {
        /** SignInしているかどうか */
        var isSignIn: Boolean = false
        /** どのユーザーでSignInしているか */
        var mUser: FirebaseUser? = null

        fun isSignIn(flg: Boolean, user: FirebaseUser? = null) {
            Log.d("unchi", "isSignIn")
            if (flg) {
                Log.d("unchi", "isSignIn True")
                isSignIn = true
                mUser = user
            } else {
                Log.d("unchi", "isSignIn False")
                isSignIn = false
                mUser = null
            }
        }
    }

}