package com.example.greentea.fteam.signIn

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.greentea.fteam.R
import com.example.greentea.fteam.`object`.UserObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

    private lateinit var parent: SignInActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as SignInActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_up_submit_button.setOnClickListener {
            val name = sign_up_edit_username.text.toString()
            if(name == "") return@setOnClickListener
            updateUserInfo(name)
        }
    }

    /** ユーザ名設定 */
    private fun updateUserInfo(name: String) {
        // ユーザ名とアイコン
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        SignInStatus.mUser!!.updateProfile(profileUpdates).addOnCompleteListener {
            if (it.isSuccessful) {
                SignInStatus.isSignIn(SignInStatus.mUser)
                Toast.makeText(context, "UserName設定完了", Toast.LENGTH_SHORT).show()
                registerUserInfo()
                parent.onFinishActivity()
            }
        }
    }

    /** ユーザー情報を他ユーザーから見られるようにFireStoreに格納 */
    private fun registerUserInfo(){
        val mFireStore = FirebaseFirestore.getInstance()
        val tempData = UserObject(SignInStatus.mUserName)
        mFireStore.collection("user")
                .document(SignInStatus.mUserID)
                .set(tempData)
                .addOnCompleteListener {

                }
                .addOnFailureListener {
                    registerUserInfo()
                    return@addOnFailureListener
                }
    }
}
