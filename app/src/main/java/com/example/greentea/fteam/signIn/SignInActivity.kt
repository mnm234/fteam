package com.example.greentea.fteam.signIn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.greentea.fteam.LOGIN_MODE
import com.example.greentea.fteam.R
import com.example.greentea.fteam.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class SignInActivity : AppCompatActivity() {
    var modeId: Int = 0//0:Login 1:SignUp
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        modeId = intent.getIntExtra(LOGIN_MODE, 0)
        if (modeId == 1) {
            forgotPass.visibility = View.GONE
            modeTitle.setImageResource(R.mipmap.signup)
            orBorder.visibility = View.GONE
            modeId = 1
            submitButton.text = "SIGN UP"
            typeChange.text = "    SIGN IN YOUR ACCOUNT    "
        } else {
            forgotPass.visibility = View.VISIBLE
            modeTitle.setImageResource(R.mipmap.login)
            orBorder.visibility = View.VISIBLE
            modeId = 0
            submitButton.text = "SIGN IN"
            typeChange.text = "    CREATE AN ACCOUNT    "
        }

//        typeChange.paintFlags = typeChange.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        forgotPass.setOnClickListener {
            //forgot Passwordをクリックしたとき
            // https://firebase.google.com/docs/auth/android/manage-users?authuser=2#set_a_users_password
        }

        /** GoogleSignInSetup */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google_signin_button.setOnClickListener {
            googleSignIn()
        }

        submitButton.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()
            if (modeId == 0) {
                //ログイン
                signIn(email, password)
            } else {
                //登録
                signUp(email, password)
            }
        }

        typeChange.setOnClickListener {
            if (modeId == 0) {
                forgotPass.visibility = View.GONE
                modeTitle.setImageResource(R.mipmap.signup)
                google_signin_button.visibility = View.GONE
                orBorder.visibility = View.GONE
                modeId = 1
                submitButton.text = "SIGN UP"
                typeChange.text = "SIGN IN YOUR ACCOUNT"

            } else {
                forgotPass.visibility = View.VISIBLE
                modeTitle.setImageResource(R.mipmap.login)
                google_signin_button.visibility = View.VISIBLE
                orBorder.visibility = View.VISIBLE
                modeId = 0
                submitButton.text = "SIGN IN"
                typeChange.text = "CREATE AN ACCOUNT"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 既にログインしているかどうか LoginActivityには恐らく不要
        val user = mAuth.currentUser
        if (user != null){
            isSignIn(true, user)
        } else {
            isSignIn(false)
        }
    }

    private fun isSignIn(flg: Boolean, user: FirebaseUser? = null){
        if(flg){
            SignInStatus().isSignIn = true
            SignInStatus().mUser = user
        } else {
            SignInStatus().isSignIn = false
            SignInStatus().mUser = null
        }

    }

    /** SignIn */
    private fun signIn(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("unchi", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        if (user != null){
                            isSignIn(true, user)
                        } else {
                            isSignIn(false)
                        }
                    } else {
                        Log.w("unchi", "signInWithEmail:failure", it.exception)
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }
    }

    /** SignUp */
    private fun signUp(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("unchi", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        if (user != null){
                            isSignIn(true, user)
                        } else {
                            isSignIn(false)
                        }
                    } else {
                        Log.w("unchi", "createUserWithEmail:failure", it.exception)
                        Toast.makeText(this, "createUserWithEmail Failed.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    /** GoogleSignIn */
    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("unchi", "signInWithCredential:success")
                        val user = mAuth.currentUser
                        if (user != null){
                            isSignIn(true, user)
                        } else {
                            isSignIn(false)
                        }
                    } else {
                        Log.d("unchi", "signInWithCredential:failure", it.exception)
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.d("unchi", "Google sign in failed", e)
                Toast.makeText(this, "signInResult:failed code = " + e.statusCode, Toast.LENGTH_SHORT).show()
            }
        }
    }

//    /** ログアウト */
//    private fun signOut() {
//        AuthUI.getInstance().signOut(context!!).addOnCompleteListener {
//            username_textView.text = "ログインしてません"
//        }
//    }
//
    /** ユーザー情報取得の例 */
//    private fun getUserInfo() {
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            // ログイン中
//            val username = user.displayName
//            val userID = user.uid
//            val userIcon = user.photoUrl
//            val userEmail = user.email
//            // プロバイダ(GoogleとかTwitterとか)別にその元となるところからユーザー情報を引っ張る
//            for (profile in user.providerData) {
//                // Id of the provider (ex: google.com)
//                val providerID = profile.providerId
//                val pUserID = profile.uid
//                val pUsername = profile.displayName
//                val pUserEmail = profile.email
//                val pUserIcon = profile.photoUrl
//            }
//        } else {
//            // ログインしていない時
//        }
//    }

    /** ユーザーのプロフィールを更新、削除、再ログイン */
//    private fun updateUserInfo() {
//        val auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        // ユーザ名とアイコン
//        val profileUpdates = UserProfileChangeRequest.Builder()
//                .setDisplayName("名前")
//                .setPhotoUri(Uri.parse("アイコンのURI"))
//                .build()
//        user!!.updateProfile(profileUpdates).addOnCompleteListener {
//            if (it.isSuccessful) {
//                // 変更成功 以下の例もisSuccessfulは一緒なので省略
//            }
//        }
//        // メールアドレス変更
//        user.updateEmail("新しいメールアドレス").addOnCompleteListener {}
//        // 確認メール送信 内容はFireBaseのAuthentication -> メールテンプレートから弄る
//        user.sendEmailVerification().addOnCompleteListener {}
//        // パスワード変更
//        user.updatePassword("新しいパスワード").addOnCompleteListener {}
//        // パスワード再設定メール送信 内容は上記のと一緒
//        auth.sendPasswordResetEmail("ユーザのメールアドレス").addOnCompleteListener {}
//        // ユーザ削除
//        user.delete().addOnCompleteListener {}
//        // 再ログイン(セキュリティレベルが高いものにアクセスさせる際に推奨)
//        val credential = EmailAuthProvider.getCredential("ユーザのメールアドレス", "ユーザのパスワード")
//        user.reauthenticate(credential).addOnCompleteListener {}
//    }

}
