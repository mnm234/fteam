package com.example.greentea.fteam.signIn

import com.google.firebase.auth.FirebaseUser


class SignInStatus{
    /** SignInしているかどうか */
    var isSignIn: Boolean = false
    /** どのユーザーでSignInしているか */
    var mUser:FirebaseUser? = null
}