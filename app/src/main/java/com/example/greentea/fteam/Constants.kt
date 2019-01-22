@file:JvmName("Constants")

package com.example.greentea.fteam

import android.Manifest
/** Video */
const val REQUEST_VIDEO_PERMISSIONS = 1
val VIDEO_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
const val FILE_PATH_KEY = "KEY_PATH"
const val NAME_PATH_KEY = "KEY_FILE_NAME"
const val REQUEST_ACCOUNT_CHOOSER = 0
const val REQUEST_AUTHORIZATION_YOUTUBE = 1
const val PREF_ACCOUNT_NAME = "ACCOUNT_NAME"

/** CompDetail */
const val COMP_ID_KEY = "COMP_ID_KEY"
const val COMP_NAME_KEY = "COMP_NAME_KEY"

/** SignIn */
const val LOGIN_MODE = "LOGIN_MODE"
const val LOGIN = 0
const val CREATE_ACCOUNT = 1
const val RC_SIGN_IN = 123

/** MainActivity */
const val MAIN_BOTTOM_NAV_KEY = "BOTTOM_MENU_ID"
const val MAIN_HOME_BOTTOM_NAV_ID = 0
const val MAIN_MY_PAGE_BOTTOM_NAV_ID = 1
const val MAIN_NEW_BOTTOM_NAV_ID = 2
const val MAIN_UPLOAD_BOTTOM_NAV_ID = 3

/** HomeFragment */
const val VIEW_PAGER_KEY = "VIEW_PAGER"

/** OtherUserFragment */
const val OTHER_USER_ID_KEY = "OTHER_USER_ID_KEY"
const val OTHER_USER_NAME_KEY = "OTHER_USER_NAME_KEY"

/** YoutubeActivity */
const val YOUTUBE_ID_KEY = "YOUTUBE_ID_KEY"