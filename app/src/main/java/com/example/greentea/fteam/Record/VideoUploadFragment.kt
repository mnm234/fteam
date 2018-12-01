package com.example.greentea.fteam.Record

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.greentea.fteam.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_video_upload.*
import java.io.File


class VideoUploadFragment : Fragment() {

    private val FILE_PATH_KEY = "KEY_PATH"
    private val NAME_PATH_KEY = "KEY_FILE_NAME"

    private lateinit var filepath: String
    private lateinit var filename: String
    private lateinit var parent: VideoActivity
    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mSurfaceHolder: SurfaceHolder

    private lateinit var mFirebaseFirestore: FirebaseFirestore
    private lateinit var mFirebaseStorage: FirebaseStorage
    private lateinit var mFirebaseStorageRef: StorageReference
    private var mUploadTask: UploadTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args: Bundle? = arguments
        filepath = args!!.getString(FILE_PATH_KEY, "")
        filename = args.getString(NAME_PATH_KEY, "")

        mFirebaseFirestore = FirebaseFirestore.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
        mFirebaseStorageRef = mFirebaseStorage.reference

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video_upload, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        parent = activity as VideoActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadVideoPreviewView.setVideoPath(filepath)
        uploadVideoPreviewView.setOnClickListener {
            uploadVideoPreviewView.start()
        }
        uploadVideoPreviewView.setOnPreparedListener {mp ->
            uploadButton.setOnClickListener {
                uploadVideoFile(mp.duration)
            }
            /**
             * 動画をFirebaseから引っ張る処理
             */
            catchButton.setOnClickListener {
                if (uploadEditText.text.toString() != "") {
                    mFirebaseFirestore.collection("videoData")
                            .whereEqualTo("title", uploadEditText.text.toString())
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (doc in task.result!!) {
                                        // 取得した分をforEachで回す
                                        Toast.makeText(context, "動画URL取得完了 読込開始", Toast.LENGTH_LONG).show()
                                        uploadVideoPreviewView.setVideoURI(Uri.parse(doc.toObject(VideoObject::class.java).path))
                                        uploadVideoPreviewView.start()
                                    }
                                }
                            }
                }
            }
        }

        uploadVideoPreviewView.start()
    }

    private fun ms2second(duration: Int) : Int{
        return duration / 1000
    }

    /**
     * 動画をFirebaseStorageにアップロードする処理
     * アップロードが完了次第setFileDataへ
     */
    private fun uploadVideoFile(duration: Int) {
        if (uploadEditText.text.toString() == "") {
            Toast.makeText(context, "動画タイトルを入力してください", Toast.LENGTH_LONG).show()
            return
        }
        val targetRef = mFirebaseStorageRef.child("videos").child(filename)
        mUploadTask = targetRef.putFile(Uri.fromFile(File(filepath)))
        mUploadTask!!
                .addOnSuccessListener { _ ->
                    Toast.makeText(context, "ファイルのアップロードに成功しました", Toast.LENGTH_LONG).show()
                    setFileData(targetRef, duration)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                }
    }

    /**
     * 動画の詳細情報をFirestoreに登録する処理
     * 現在はタイトル名と動画のDLURLのみ
     */
    private fun setFileData(tar: StorageReference, duration: Int) {

        mFirebaseStorage.getReferenceFromUrl(tar.toString()).downloadUrl.addOnCompleteListener {
            val tempData = VideoObject(uploadEditText.text.toString(), it.result!!.toString(), ms2second(duration))
            mFirebaseFirestore.collection("videoData")
                    .add(tempData)
                    .addOnSuccessListener { doc ->
                        File(filepath).delete()
                        Log.d("testaa", "ファイルの詳細情報をDBに登録しました !D:" + doc.id)
//                        Toast.makeText(context, "ファイルの詳細情報をDBに登録しました !D:" + doc.id, Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        Log.d("testaa", e.toString())
//                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    }
        }.addOnFailureListener {
//            Toast.makeText(context, "動画DL用URLの作成に失敗しました", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * アップロード中にアプリを落としたりしたらキャンセルさせる
     * キャンセル無しでも問題ない(アプリを落とした場合でもきちんとアップロードが完了する)
     */
    override fun onPause() {
        if(mUploadTask != null && mUploadTask!!.isInProgress) {
            mUploadTask!!.cancel()
            Log.d("testaa", "アップロードをキャンセルしました")
        }
        super.onPause()
    }

    /**
     * VideoUploadFragmentから出るときに端末にビデオが残っていれば削除
     */
    override fun onDestroy() {
        File(filepath).delete()
        super.onDestroy()
    }

    companion object {

        fun newInstance(path: String, name: String): VideoUploadFragment {
            val videoUploadFragment = VideoUploadFragment()
            val bundle = Bundle()
            bundle.putString("KEY_PATH", path)
            bundle.putString("KEY_FILE_NAME", name)
            videoUploadFragment.arguments = bundle
            return videoUploadFragment
        }
    }
}
