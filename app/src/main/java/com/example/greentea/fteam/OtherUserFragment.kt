package com.example.greentea.fteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_other_user.*


class OtherUserFragment : Fragment() {

    companion object {
        fun newInstance(id:String, name:String): OtherUserFragment {
            val fragment = OtherUserFragment()
            val bundle = Bundle()
            bundle.putString(OTHER_USER_ID_KEY, id)
            bundle.putString(OTHER_USER_NAME_KEY, name)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var parent: MainActivity
    private var mOtherID: String = ""
    private var mOtherName: String = ""

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = activity as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mOtherID = it.getString(OTHER_USER_ID_KEY)!!
            mOtherName = it.getString(OTHER_USER_NAME_KEY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        other_username_textView.text = mOtherName

        /** フォロー済みかどうか判定 */
        if(SignInStatus.followerList.contains(mOtherID)){
            other_user_follow_button.isEnabled = false
            other_user_follow_button.text = "フォロー済み"
        }

        other_user_follow_button.setOnClickListener {
            followUser()
        }
    }

    /** フォロー処理 */
    private fun followUser(){
        val mFS = FirebaseFirestore.getInstance()
        SignInStatus.followerList.add(mOtherID)
        mFS.collection("user")
                .document(SignInStatus.mUserID)
                .update("followerID", SignInStatus.followerList)
                .addOnCompleteListener {
                    Toast.makeText(context, "フォローしました", Toast.LENGTH_SHORT).show()
                    other_user_follow_button.isEnabled = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "フォロー失敗", Toast.LENGTH_SHORT).show()
                }
    }
}
