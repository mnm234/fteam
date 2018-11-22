package com.example.greentea.fteam

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import kotlinx.android.synthetic.main.activity_camera.*
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private var captureSession: CameraCaptureSession? = null
    private var cameraDevice: CameraDevice? = null
    private var previewSize: Size = Size(1080,1920)
    private lateinit var previewRequestBuilder: CaptureRequest.Builder
    private var imageReader: ImageReader? = null
    private lateinit var previewRequest: CaptureRequest
    private var backgroundThread:HandlerThread? = null
    private var backgroundHandler:Handler? = null

    companion object {
        const val CAMERA_PERMISION = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        mySurfaceView.surfaceTextureListener = surfaceTextureListener
        startBackgroundThread()
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
            imageReader = ImageReader.newInstance(width,height,ImageFormat.JPEG,2)
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
            return false
        }
    }

    fun openCamera() {
        val manager: CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            val cameraId: String = manager.cameraIdList[0]
            val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

            if(permission != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission()
                return
            }
            manager.openCamera(cameraId, stateCallback, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val stateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice?) {
            this@CameraActivity.cameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice?) {
            cameraDevice?.close()
            this@CameraActivity.cameraDevice = null
        }

        override fun onError(camera: CameraDevice?, error: Int) {
            onDisconnected(camera)
            finish()
        }
    }

    private fun createCameraPreviewSession() {
        try {
            val texture = mySurfaceView.surfaceTexture
            texture.setDefaultBufferSize(previewSize.width, previewSize.height)
            val surface = Surface(texture)
            previewRequestBuilder = cameraDevice!!.createCaptureRequest(
                    CameraDevice.TEMPLATE_PREVIEW
            )
            previewRequestBuilder.addTarget(surface)
            cameraDevice?.createCaptureSession(Arrays.asList(surface, imageReader?.surface),
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    object : CameraCaptureSession.StateCallback() {
                        override fun onConfigured(session: CameraCaptureSession?) {
                            if(cameraDevice == null) return
                            captureSession = session
                            try {
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                                previewRequest = previewRequestBuilder.build()
                                session?.setRepeatingRequest(previewRequest, null, Handler(backgroundThread?.looper))
                            } catch (e: CameraAccessException) {
                                Log.e("erfs", e.toString())
                            }
                        }

                        override fun onConfigureFailed(session: CameraCaptureSession?) {
                            // 設定失敗時の動作
                        }
                    },null)
        } catch (e: CameraAccessException) {
            Log.e("erf", e.toString())
        }
    }

    fun requestCameraPermission() {
        if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            AlertDialog.Builder(baseContext)
                    .setMessage("Permission Here")
                    .setPositiveButton(android.R.string.ok) {_,_ ->
                        requestPermissions(arrayOf(android.Manifest.permission.CAMERA),CAMERA_PERMISION)
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->
                        finish()
                    }
                    .create()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISION)
        }
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        backgroundHandler = Handler(backgroundThread?.looper)
    }

}
