package com.example.greentea.fteam

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_new_compe.*
import kotlinx.android.synthetic.main.fragment_video_preview.*

class VideoPreviewFragment : Fragment() {

    lateinit var filepath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args:Bundle = arguments
        filepath = args.getString("KEY_PATH")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_video_preview, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoPreviewView.setVideoPath(filepath)
    }

    companion object {
        fun newInstance(path: String): VideoPreviewFragment {
            val VideoPreviewFragment = VideoPreviewFragment()
            val bundle = Bundle()
            bundle.putString("KEY_PATH", path)
            VideoPreviewFragment.arguments = bundle
            return VideoPreviewFragment
        }
    }
}
