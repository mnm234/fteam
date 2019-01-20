package com.example.greentea.fteam.Login

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.greentea.fteam.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_new_comp.*
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.net.Uri
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class LoginActivity : AppCompatActivity() {
    var modeId:Int = 0//0:Login 1:SignUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        modeId = intent.getIntExtra("LoginMode", 0)
        if(modeId == 1){
            forgotPass.visibility = View.GONE
            modeTitle.setImageResource(R.mipmap.signup)
            google_signin_button.visibility = View.GONE
            orBorder.visibility = View.GONE
            modeId = 1
            submitButton.text = "SIGN UP"
            typeChange.text = "    LOGIN YOUR ACCOUNT    "
        }else{
            forgotPass.visibility = View.VISIBLE
            modeTitle.setImageResource(R.mipmap.login)
            google_signin_button.visibility = View.VISIBLE
            orBorder.visibility = View.VISIBLE
            modeId = 0
            submitButton.text = "LOG IN"
            typeChange.text = "    CREATE AN ACCOUNT    "
        }

//        typeChange.paintFlags = typeChange.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        forgotPass.setOnClickListener {
            //forgot Passwordをクリックしたとき
        }


        google_signin_button.setOnContextClickListener {
            signIn()
        }

        submitButton.setOnClickListener {
            if(modeId == 0){
                //ログイン
            }else{
                //登録
            }
        }

        typeChange.setOnClickListener {
            if(modeId == 0){
                forgotPass.visibility = View.GONE
                modeTitle.setImageResource(R.mipmap.signup)
                google_signin_button.visibility = View.GONE
                orBorder.visibility = View.GONE
                modeId = 1
                submitButton.text = "SIGN UP"
                typeChange.text = "LOGIN YOUR ACCOUNT"

            }else{
                forgotPass.visibility = View.VISIBLE
                modeTitle.setImageResource(R.mipmap.login)
                google_signin_button.visibility = View.VISIBLE
                orBorder.visibility = View.VISIBLE
                modeId = 0
                submitButton.text = "LOG IN"
                typeChange.text = "CREATE AN ACCOUNT"
            }
        }

    }


// 以下、ログインログアウトのサンプルコード。
// 処理は以下から引用する予定です。

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_hot, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        sign_in_button.setOnClickListener {
//            signIn()
//        }
//        sign_out_button.setOnClickListener {
//            signOut()
//        }
//    }
//
    /** ログイン画面呼び出し */
    private fun signIn(){
        // どの認証方式を使わせるか選択
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

//    /** ログアウト */
//    private fun signOut() {
//        AuthUI.getInstance().signOut(context!!).addOnCompleteListener {
//            username_textView.text = "ログインしてません"
//        }
//    }
//
    /** ユーザー情報取得の例 */
    private fun getUserInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // ログイン中
            val username = user.displayName
            val userID = user.uid
            val userIcon = user.photoUrl
            val userEmail = user.email
            // プロバイダ(GoogleとかTwitterとか)別にその元となるところからユーザー情報を引っ張る
            for (profile in user.providerData) {
                // Id of the provider (ex: google.com)
                val providerID = profile.providerId
                val pUserID = profile.uid
                val pUsername = profile.displayName
                val pUserEmail = profile.email
                val pUserIcon = profile.photoUrl
            }
        } else {
            // ログインしていない時
        }
    }

    /** ユーザーのプロフィールを更新、削除、再ログイン */
    private fun updateUserInfo() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        // ユーザ名とアイコン
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("名前")
                .setPhotoUri(Uri.parse("アイコンのURI"))
                .build()
        user!!.updateProfile(profileUpdates).addOnCompleteListener {
            if (it.isSuccessful) {
                // 変更成功 以下の例もisSuccessfulは一緒なので省略
            }
        }
        // メールアドレス変更
        user.updateEmail("新しいメールアドレス").addOnCompleteListener {}
        // 確認メール送信 内容はFireBaseのAuthentication -> メールテンプレートから弄る
        user.sendEmailVerification().addOnCompleteListener {}
        // パスワード変更
        user.updatePassword("新しいパスワード").addOnCompleteListener {}
        // パスワード再設定メール送信 内容は上記のと一緒
        auth.sendPasswordResetEmail("ユーザのメールアドレス").addOnCompleteListener {}
        // ユーザ削除
        user.delete().addOnCompleteListener {}
        // 再ログイン(セキュリティレベルが高いものにアクセスさせる際に推奨)
        val credential = EmailAuthProvider.getCredential("ユーザのメールアドレス", "ユーザのパスワード")
        user.reauthenticate(credential).addOnCompleteListener {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
//                username_textView.text = user!!.displayName
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
//
    companion object {
        val RC_SIGN_IN = 123
    }

}
