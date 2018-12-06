package com.example.greentea.fteam

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_new_compe.*
import android.text.TextWatcher



class NewCompeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_new_compe, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /**
         * 「新競技名」欄にテキストを入力したときのリスナー
         **/
        newCompeName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                makeCompeButton.isEnabled = !(newCompeName.text.isEmpty() || newCompeRule.text.isEmpty())
            }
        })


        /**
         * 「競技のルール」欄にテキストを入力したときのリスナー
         **/
        newCompeRule.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                makeCompeButton.isEnabled = !(newCompeName.text.isEmpty() || newCompeRule.text.isEmpty())
            }
        })


        /**
         * 「新しく競技を作成」ボタンを押したときの処理
         * */
        makeCompeButton.setOnClickListener {
            submitMessage.text = "競技が作成されました。\n 早速撮影しますか？"
            camStartButton.visibility = View.VISIBLE
            newCompeName.isEnabled = false
            newCompeRule.isEnabled = false
        }


        /**
         *　カメラボタンをタップしたときの処理
         **/
        camStartButton.setOnClickListener {
//            val intent = Intent(this@NewCompeFragment.context,CameraActivity::class.java)
            val intent = Intent(this@NewCompeFragment.context,VideoActivity::class.java)
            startActivity(intent)
        }
    }
}
