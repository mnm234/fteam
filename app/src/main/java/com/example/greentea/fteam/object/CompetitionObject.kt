package com.example.greentea.fteam.`object`

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class CompetitionObject(
        val name: String = "",
        val rule: String = "",
        @ServerTimestamp val timestamp: Date? = null,
        val tag: ArrayList<String> = ArrayList()
)