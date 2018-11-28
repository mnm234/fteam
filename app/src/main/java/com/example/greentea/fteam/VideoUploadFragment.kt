package com.example.greentea.fteam

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        uploadButton.setOnClickListener { it ->
            uploadVideoFile()
        }
        uploadVideoPreviewView.start()
    }

    private fun uploadVideoFile() {
        mFirebaseStorageRef.child("videos").child(filename)
                .putFile(Uri.fromFile(File(filepath)))
                .addOnSuccessListener { uploadSnap ->
//                    Toast.makeText(context, "アップロード成功", Toast.LENGTH_LONG).show()
                    setFileData(uploadSnap)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                }
    }

    private fun setFileData(uploadSnap: UploadTask.TaskSnapshot) {
        val data = HashMap<String, Any>()
        data["title"] = "test1"
//        data["path"] = uploadSnap

        mFirebaseFirestore.collection("videoData")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(context, "アップロード成功", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                }
    }

    override fun onDetach() {
        super.onDetach()
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
