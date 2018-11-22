package com.example.greentea.fteam

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.fragment_video_preview.*

class VideoPreviewFragment : Fragment() {

    private lateinit var filepath: String
    private lateinit var parent: VideoActivity
    private lateinit var mMediaPlayer:MediaPlayer
    private lateinit var mSurfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args:Bundle? = arguments
        filepath = args!!.getString("KEY_PATH")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_video_preview, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = activity as VideoActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSurfaceView()
//        videoPreviewView.setOnClickListener {
//            videoPreviewView.start()
//        }
        backButton.setOnClickListener {
            parent.goCamera()
        }
//        videoPreviewView.setVideoPath(filepath)
//        videoPreviewView.start()
        Log.d("unchi", filepath)
    }

    internal var mSurfaceCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            videoPreviewView.setOnClickListener {
//                playVideoSource(filepath)
                mMediaPlayer.start()
            }
            playVideoSource(filepath)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {

        }
    }

    fun prepareSurfaceView(){
        mSurfaceHolder = videoPreviewView.holder
        mSurfaceHolder.addCallback(mSurfaceCallback)
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setOnVideoSizeChangedListener { mediaPlayer, w, h ->
            val winWidth = getView()!!.width
            val winHeight = getView()!!.height
            if (w > h || w == h && winWidth < winHeight) {
                val width = winWidth
                val p = h.toFloat() / w.toFloat()
                val height = (width.toFloat() * p).toInt()
                mSurfaceHolder.setFixedSize(width, height)
            } else {
                val height = winHeight
                val p =  w.toFloat() / h.toFloat()
                val width = (height.toFloat() * p).toInt()
                mSurfaceHolder.setFixedSize(width, height)
            }
        }
        
    }

    fun playVideoSource(path:String) {
        mMediaPlayer.setDataSource(path)
        mMediaPlayer.setDisplay(mSurfaceHolder)
        mMediaPlayer.prepare()
        mMediaPlayer.start()
    }

    companion object {
        fun newInstance(path:String): VideoPreviewFragment {
            val VideoPreviewFragment = VideoPreviewFragment()
            val bundle = Bundle()
            bundle.putString("KEY_PATH", path)
            VideoPreviewFragment.arguments = bundle
            return VideoPreviewFragment
        }
    }
}
