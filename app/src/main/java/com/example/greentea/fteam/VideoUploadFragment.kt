package com.example.greentea.fteam

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

    private var tempPath = ""
    private var tempID = ""

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
        uploadButton.setOnClickListener {
            uploadVideoFile()
        }
        catchButton.setOnClickListener {
            if(tempID != ""){
//                var temp : VideoObject?
                mFirebaseFirestore.collection("videoData").document(tempID)
                        .get().addOnCompleteListener {doc ->
                            tempPath = doc.result!!.toObject(VideoObject::class.java)!!.path
                        }
                mFirebaseStorage.getReferenceFromUrl(tempPath).downloadUrl.addOnCompleteListener {
                    lastPath = it.result!!
                    uploadVideoPreviewView.setVideoURI(lastPath)
                    uploadVideoPreviewView.start()
                }
            } else if (lastPath != null) {
                uploadVideoPreviewView.setVideoURI(lastPath)
                uploadVideoPreviewView.start()

            }
        }
        uploadVideoPreviewView.start()
    }

    private fun uploadVideoFile() {
        val targetRef = mFirebaseStorageRef.child("videos").child(filename)
        targetRef
                .putFile(Uri.fromFile(File(filepath)))
                .addOnSuccessListener { uploadSnap ->
//                    Toast.makeText(context, "アップロード成功", Toast.LENGTH_LONG).show()
                    setFileData(targetRef)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                }
    }

    private fun setFileData(tar: StorageReference) {
//        val data = HashMap<String, Any>()
//        data["title"] = "test1"
//        data["path"] = tar.toString()
//        Log.d("testaa", tar.toString())
        tempPath = tar.toString()
        val tempData = VideoObject("test1", tar.toString())

        mFirebaseFirestore.collection("videoData")
                .add(tempData)
                .addOnSuccessListener {
                    tempID = it.id
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

        lateinit var lastPath: Uri

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
