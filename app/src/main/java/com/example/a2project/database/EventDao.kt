package com.example.a2project.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a2project.Event
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface EventDao{
    @Query("SELECT * FROM event")
    fun getEvents(): Flow<List<Event>>
    @Query("SELECT * FROM event WHERE id=(:id)")
    //suspend fun getEvent(id: UUID): Event
    suspend fun getEvent(id: UUID): Event
    @Update
    suspend fun updateEvent(event: Event)
    @Insert
    suspend fun addEvent(event: Event)
}