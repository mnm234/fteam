package com.example.greentea.fteam

import java.sql.Timestamp
import java.util.*

data class TimeLineObject(
        val name:String = "",
        val rule:String = "",
        val timestamp: Date? = null,
        val tag:ArrayList<String> = ArrayList()
)