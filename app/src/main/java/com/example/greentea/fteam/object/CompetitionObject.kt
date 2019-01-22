package com.example.greentea.fteam.`object`

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class CompetitionObject(
        val name: String = "",
        val rule: String = "",
        val ownerID: String = "",
        val ownerName: String = "",
        @ServerTimestamp val timestamp: Date? = null,
        val viewCount: Int = 0,
        val tag: ArrayList<String> = ArrayList()
)