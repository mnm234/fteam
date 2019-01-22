package com.example.greentea.fteam.contribution.record

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.example.greentea.fteam.*
import com.example.greentea.fteam.`object`.CompetitionDetailObject
import com.example.greentea.fteam.signIn.SignInStatus
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.googleapis.media.MediaHttpUploader
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener
import com.google.api.client.http.InputStreamContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoSnippet
import com.google.api.services.youtube.model.VideoStatus
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_video_upload.*
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class VideoUploadFragment : Fragment() {

    private lateinit var filepath: String
    private lateinit var filename: String
    private lateinit var mCompetitionID: String
    private lateinit var mCompetitionName: String
    private var mDuration: Int = 0

    private lateinit var parent: VideoActivity

    private lateinit var mFirebaseFirestore: FirebaseFirestore
//    private lateinit var mFirebaseStorage: FirebaseStorage
//    private lateinit var mFirebaseStorageRef: StorageReference
//    private var mUploadTask: UploadTask? = null

    companion object {
        fun newInstance(path: String, name: String, mCompID: String, mCompName: String): VideoUploadFragment {
            val videoUploadFragment = VideoUploadFragment()
            val bundle = Bundle()
            bundle.putString(FILE_PATH_KEY, path)
            bundle.putString(NAME_PATH_KEY, name)
            bundle.putString(COMP_ID_KEY, mCompID)
            bundle.putString(COMP_NAME_KEY, mCompName)
            videoUploadFragment.arguments = bundle
            return videoUploadFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filepath = it.getString(FILE_PATH_KEY, "")
            filename = it.getString(NAME_PATH_KEY, "")
            mCompetitionID = it.getString(COMP_ID_KEY, "")
            mCompetitionName = it.getString(COMP_NAME_KEY, "")
        }
        mFirebaseFirestore = FirebaseFirestore.getInstance()
//        mFirebaseStorage = FirebaseStorage.getInstance()
//        mFirebaseStorageRef = mFirebaseStorage.reference

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

        /** ユーザー名を決めるところが現在存在しないので仮でEmailを表示 */
        if (SignInStatus.isSignIn) {
            uploadUserName.text = SignInStatus.mUserName
        }

        /** 動画の時間を取得するためにuploadVideoPreviewViewの準備が完了するまで待たせる */
        uploadVideoPreviewView.setOnPreparedListener { mp ->
            mDuration = mp.duration
            // 時間表示
            uploadVideoDuration.text = convertTime(mDuration)
            uploadButton.setOnClickListener {
                uploadYoutube()
                uploadButton.isEnabled = false
            }
            /** 動画をFirebaseから引っ張る処理 機能停止中 */
            catchButton.setOnClickListener {
                //                mFirebaseFirestore.collection("competition")
//                        .document(mCompetitionID)
//                        .collection("user")
//                        .whereEqualTo("userID", uploadUserName.text.toString())
//                        .get()
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                for (doc in task.result!!) {
//                                    // 取得した分をforEachで回す
//                                    Toast.makeText(context, "動画URL取得完了 読込開始", Toast.LENGTH_LONG).show()
//                                    uploadVideoPreviewView.setVideoURI(Uri.parse(doc.toObject(CompetitionDetailObject::class.java).videoURL))
//                                    uploadVideoPreviewView.start()
//                                }
//                            }
//                        }
            }
        }
        uploadVideoPreviewView.start()
    }

    /**
     * ms -> second
     * @param duration 変換対象の値(ms)
     * @return secondに変換して返す
     */
    private fun ms2second(duration: Int): Int {
        return duration / 1000
    }

    /**
     * 時間(ms)を表示用に変換する関数
     * @param duration 変換対象の値(ms)
     * @return 00:00:00の表記でstring型で返す
     */
    private fun convertTime(duration: Int): String {
        val minute = (duration / (1000 * 60)) % 60
        val second = (duration / 1000) % 60
        val ms = duration % 1000
        return String.format("%02d:%02d:%03d", minute, second, ms)
    }

    /**
     * 動画の詳細情報をFirestoreに登録する処理
     * @param mYouTubeVideoID 動画のID(https://youtu.be/xxxxxx)
     */
    private fun setFileData(mYouTubeVideoID: String) {
        val tempData = CompetitionDetailObject("upload", SignInStatus.mUserID, uploadUserName.text.toString(), ms2second(mDuration), mYouTubeVideoID, mCompetitionName, Date())
        mFirebaseFirestore.collection("competition")
                .document(mCompetitionID)
                .collection("user")
                .add(tempData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        File(filepath).delete()
                        Toast.makeText(context, "ファイルの詳細情報をDBに登録しました ID: ${task.result!!.id}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("unchi", e.toString())
                    Toast.makeText(context, "ファイルの詳細情報登録に失敗しました", Toast.LENGTH_SHORT).show()
                }
    }

    /** VideoUploadFragmentから出るときに端末にビデオが残っていれば削除 */
    override fun onDestroy() {
        File(filepath).delete()
        super.onDestroy()
    }

    /** 以下Youtubeのinsert部分 */
    private var mCredential: GoogleAccountCredential? = null
    private var mIntent: Intent? = null
    private var mChooserFlg = false

    /** YouTubeのUploadFunctionRoot */
    private fun uploadYoutube() {
        uploadButton.isEnabled = false
        if (!mChooserFlg) {
            uploadButton.isEnabled = true
            showAccountChooser()
        } else {
            youtubeFunc()
        }
    }

    /** 投稿時に使用するGoogleAccountの選択 */
    private fun showAccountChooser() {
        if (mCredential == null) mCredential = makeCredential()
        startActivityForResult(mCredential!!.newChooseAccountIntent(), REQUEST_ACCOUNT_CHOOSER)
    }

    private fun makeCredential(): GoogleAccountCredential {
        val scopes = Arrays.asList(
                YouTubeScopes.YOUTUBE, YouTubeScopes.YOUTUBE_READONLY, YouTubeScopes.YOUTUBE_UPLOAD)
        return GoogleAccountCredential.usingOAuth2(context, scopes)
    }

    /** YouTubeにアップロードする動画の情報設定 */
    private fun youtubeFunc() {
        try {
            /** 認証部分 */
            if (mCredential == null) mCredential = makeCredential()
            val youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), mCredential)
                    .setApplicationName("Google-YouTubeAndroidSample/1.0").build()
            /** 動画情報設定部分 */
            val videoFile = File(filepath)
            val videoObjectDefiningMetadata = Video()
            val status = VideoStatus()
            // 公開レベル {public, unlisted, private}
            status.privacyStatus = "unlisted"
            videoObjectDefiningMetadata.status = status
            val snippet = VideoSnippet()
            val cal = Calendar.getInstance()
            // 動画タイトル
            snippet.title = "Engord $mCompetitionName " + cal.time
            // 動画詳細
            snippet.description = "From Engord " + cal.time
            // タグ
            val tags = Arrays.asList("Engord", mCompetitionID)
            snippet.tags = tags
            videoObjectDefiningMetadata.snippet = snippet
            /** 投稿 */
            val mediaContent = InputStreamContent("video/*", BufferedInputStream(FileInputStream(videoFile)))
            mediaContent.length = videoFile.length()
            val videoInsert = youtube.videos().insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent)
            val uploader = videoInsert.mediaHttpUploader
            uploader.isDirectUploadEnabled = false
            val progressListener = MediaHttpUploaderProgressListener {
                when (it.uploadState) {
                    MediaHttpUploader.UploadState.INITIATION_STARTED -> {
                        Log.d("unchi", "Initiation Started")
                    }
                    MediaHttpUploader.UploadState.INITIATION_COMPLETE -> {
                        Log.d("unchi", "Initiation Completed")
                    }
                    MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS -> {
                        Log.d("unchi", "Upload in progress")
                        Log.d("unchi", "Upload percentage: " + uploader.progress)
                    }
                    MediaHttpUploader.UploadState.MEDIA_COMPLETE -> {
                        Log.d("unchi", "Upload Completed")
                    }
                    MediaHttpUploader.UploadState.NOT_STARTED -> {
                        Log.d("unchi", "Upload Not Started!")
                    }
                    else -> {
                    }
                }
            }
            uploader.progressListener = progressListener
            insertVideoExecute(videoInsert)
        } catch (e: IOException) {
            Log.d("unchi", "IOException: " + e.message)
            e.printStackTrace()
        } catch (t: Throwable) {
            Log.d("unchi", "Throwable: " + t.message)
            t.printStackTrace()
        } catch (e: Exception) {
            Log.d("unchi", "error. " + e.toString())
            e.printStackTrace()
        }
    }

    private fun insertVideoExecute(videoInsert: YouTube.Videos.Insert) {
        var youtubeID = ""
        var channelFlg = true
        GlobalScope.launch {
            // 非同期準備処理
//            async(Dispatchers.Main) {}
            // 非同期処理
            withContext(Dispatchers.Default) {
                try {
                    val returnedVideo = videoInsert.execute()
                    Log.d("unchi", "================== Returned Video ==================")
                    youtubeID = returnedVideo.id
                    Log.d("unchi", "  - Id: $youtubeID")
                    Log.d("unchi", "  - Title: " + returnedVideo.snippet.title)
                    Log.d("unchi", "  - Tags: " + returnedVideo.snippet.tags)
                    Log.d("unchi", "  - Privacy Status: " + returnedVideo.status.privacyStatus)
                    Log.d("unchi", "  - Video Count: " + returnedVideo.statistics.viewCount)
                } catch (e: UserRecoverableAuthIOException) {
                    // 選択したGアカウントにYouTube関連のPermissionがない場合
                    mIntent = e.intent
                } catch (e: GoogleJsonResponseException) {
                    if (e.details.errors[0].reason == "youtubeSignupRequired") {
                        Log.d("unchi", "チャンネルがありません")
                        channelFlg = false
                    }
                    Log.d("unchi", "GoogleJsonResponseException code: " + e.details.code + " : " + e.details.message)
                    e.printStackTrace()
                } catch (e: IOException) {
                    Log.e("unchi", e.message, e)
                    e.printStackTrace()
                } catch (t: Throwable) {
                    Log.d("unchi", "Throwable: " + t.message)
                    t.printStackTrace()
                } catch (e: Exception) {
                    Log.d("unchi", "error. " + e.toString())
                    e.printStackTrace()
                }
            }
            // 非同期後の処理 UIスレッド
            async(Dispatchers.Main) {
                if (!channelFlg) {
                    // 選択したGアカウントがチャンネルを開設していない場合
                    AlertDialog.Builder(context!!)
                            .setTitle("Error")
                            .setMessage("選択されたGoogleアカウントはチャンネルを開設していません。\n" +
                                    "動画をアップロードするにはチャンネルを開設するか既にチャンネルを開設済みのアカウントを選択してください。")
                            .setPositiveButton("ok") { _, _ -> }
                            .show()
                    channelFlg = false
                    mChooserFlg = false
                    uploadButton.isEnabled = true
                    return@async
                }
                if (mIntent != null) {
                    // 選択したGアカウントにYouTube関連のPermissionがない場合
                    startActivityForResult(mIntent, REQUEST_AUTHORIZATION_YOUTUBE)
                } else {
                    // 動画情報をFireBaseStoreへ
                    setFileData(youtubeID)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Return: GoogleAccountの選択ダイアログ
            REQUEST_ACCOUNT_CHOOSER -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                    Log.d("unchi", "AccountName: $accountName")
                    if (accountName != null) {
                        mCredential!!.selectedAccountName = accountName
                        val settings = activity!!.getPreferences(Context.MODE_PRIVATE)
                        val editor = settings.edit()
                        editor.putString(PREF_ACCOUNT_NAME, accountName).apply()
                        mChooserFlg = true
                    }
                } else {
                    Toast.makeText(context, "サインインしないとアップロードできません", Toast.LENGTH_SHORT).show()
                }
                uploadYoutube()
            }
            // Return: Upload時に選択したGoogleAccountにYoutubeのPermissionが無い場合
            REQUEST_AUTHORIZATION_YOUTUBE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mIntent = null
                    uploadYoutube()
                } else {
                    Toast.makeText(context, "許可しないとアップロードできません", Toast.LENGTH_SHORT).show()
                    uploadYoutube()
                }
            }
            // Return: 端末にGooglePlayServicesのAPKがインストールされていない場合
//            REQUEST_GOOGLE_PLAY_SERVICES -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    haveGooglePlayServices()
//                } else {
//                    checkGooglePlayServicesAvailable()
//                }
//            }
        }
    }

    /**
     * 以下必要ないがいずれ使えるかもしれないコードを残しておく部分
     */

    /**
     * アップロード中にアプリを落としたりしたらキャンセルさせる
     * キャンセル無しでも問題ない(アプリを落とした場合でもきちんとアップロードが完了する)
     */
//    override fun onPause() {
//        if (mUploadTask != null && mUploadTask!!.isInProgress) {
//            mUploadTask!!.cancel()
//        }
//        super.onPause()
//    }

    /**
     * 動画をFirebaseStorageにアップロードする処理
     * アップロードが完了次第setFileDataへ
     */
//    private fun uploadVideoFile(duration: Int) {
//        if (uploadUserName.text.toString() == "") {
//            Toast.makeText(context, "動画タイトルを入力してください", Toast.LENGTH_LONG).show()
//            return
//        }
//        val targetRef = mFirebaseStorageRef.child("videos").child(filename)
//        mUploadTask = targetRef.putFile(Uri.fromFile(File(filepath)))
//        mUploadTask!!
//                .addOnSuccessListener {
//                    Toast.makeText(context, "ファイルのアップロードに成功しました", Toast.LENGTH_LONG).show()
//                    setFileData(targetRef, duration)
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
//                }
//    }

    /**
     * 動画の詳細情報をFirestoreに登録する処理
     * 現在はユーザIDと動画のDLURLとTimeのみ
     */
//    private fun setFileData(mYouTubeVideoID: String) {
//        mFirebaseStorage.getReferenceFromUrl(tar.toString()).downloadUrl.addOnCompleteListener {
//            val tempData = CompetitionDetailObject("ID予定地", uploadUserName.text.toString(), ms2second(duration), it.result!!.toString())
//            mFirebaseFirestore.collection("competition")
//                    .document(mCompetitionID)
//                    .collection("user")
//                    .add(tempData)
//                    .addOnSuccessListener { doc ->
//                        File(filepath).delete()
//                        Toast.makeText(context, "ファイルの詳細情報をDBに登録しました !D:" + doc.id, Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener { e ->
//                        Log.d("testaa", e.toString())
//                        Toast.makeText(context, "ファイルの詳細情報登録に失敗しました", Toast.LENGTH_SHORT).show()
//                    }
//        }.addOnFailureListener {
//            Toast.makeText(context, "動画DL用URLの作成に失敗しました", Toast.LENGTH_LONG).show()
//        }
//    }


    /**
     * 以下GooglePlayServicesが端末で有効化されているかどうかチェック
     * 現状これで弾かれるのがKindleとかの極一部なので不要
     */
//    private fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
//        val dialog = GooglePlayServicesUtil.getErrorDialog(
//                connectionStatusCode, activity, REQUEST_GOOGLE_PLAY_SERVICES)
//        dialog.show()
//    }
//
//    /** Check that Google Play services APK is installed and up to date. */
//    private fun checkGooglePlayServicesAvailable(): Boolean {
//        val connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context)
//        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
//            return false
//        }
//        return true
//    }
//
//    private fun haveGooglePlayServices() {
//        if (mCredential != null && mCredential!!.selectedAccountName == null) {
//            showAccountChooser()
//        } else {
//            /** ここに動画投稿関数 */
//        }
//    }

    /** 以下VideoFileの選定 恐らく不要 */
//    @Throws(IOException::class)
//    private fun getVideoFromUser(): File {
//        val listOfVideoFiles = getLocalVideoFiles()
//        return getUserChoice(listOfVideoFiles)
//    }
//
//    @Throws(IOException::class)
//    private fun getLocalVideoFiles(): Array<File> {
//        val currentDirectory = File(".")
//        Log.d("unchi", "Video files from " + currentDirectory.absolutePath + ":")
//
//        // Filters out video files. This list of video extensions is not comprehensive.
//        val videoFilter = FilenameFilter { dir, name ->
//            val lowercaseName = name!!.toLowerCase()
//            (lowercaseName.endsWith(".webm") || lowercaseName.endsWith(".flv")
//                    || lowercaseName.endsWith(".f4v") || lowercaseName.endsWith(".mov")
//                    || lowercaseName.endsWith(".mp4"))
//        }
//        return currentDirectory.listFiles(videoFilter)
//    }
//
//    @Throws(IOException::class)
//    private fun getUserChoice(videoFiles: Array<File>): File {
//        if (videoFiles.isEmpty()) {
//            throw IllegalArgumentException("No video files in this directory.")
//        }
//        for (vFiles in videoFiles) {
//            Log.d("unchi", vFiles.name)
//        }
//        val bReader = BufferedReader(InputStreamReader(System.`in`))
//        var inputChoice = ""
//        do {
//            Log.d("unchi", "Choose the number of the video file you want to upload: ")
//            inputChoice = bReader.readLine()
//        } while (!isValidIntegerSelection(inputChoice, videoFiles.size))
//        return videoFiles[inputChoice.toInt()]
//    }
//
//    private fun isValidIntegerSelection(input: String, max: Int): Boolean {
//        if (input.length > 9) return false
//        var validNumber = false
//        val intsOnly = Pattern.compile("^\\\\d{1,9}\$")
//        val makeMatch = intsOnly.matcher(input)
//        if (makeMatch.find()) {
//            val number = makeMatch.group().toInt()
//            if ((number >= 0) && (number < max)) validNumber = true
//        }
//        return validNumber
//    }
}
