package com.example.a2project

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event (
    @PrimaryKey val id: UUID,
    val type: String,
    val title: String,
    val location: String,
    val date: String,
    val attending: String,
    val attended: Boolean,
)