package com.example.greentea.fteam.`object`

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class TimeLineObject(
        val type:String = "",
        val compName:String = "",
        val userID:String = "",
        val username:String = "",
        val time:Int = 0,
        val videoURL:String = "",
        @ServerTimestamp val timestamp: Date? = null
)