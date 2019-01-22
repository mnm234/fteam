package com.example.greentea.fteam.`object`

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class TimeLineObject(
        val type:String = "",
        val compID:String = "",
        val compName:String = "",
        val userID:String = "",
        val username:String = "",
        val time:Int = 0,
        val videoID:String = "",
        @ServerTimestamp val timestamp: Date? = null
)