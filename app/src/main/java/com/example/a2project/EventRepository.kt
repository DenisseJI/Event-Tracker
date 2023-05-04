package com.example.a2project

import android.content.Context
import androidx.room.Room
import com.example.a2project.database.EventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*


private const val DATABASE_NAME = "event-database"

class EventRepository private constructor(context: Context, private val coroutineScope: CoroutineScope = GlobalScope){
    private val database: EventDatabase = Room
        .databaseBuilder(
        context.applicationContext,
            EventDatabase::class.java,
            DATABASE_NAME
    ) .build()


    fun getEvents(): Flow<List<Event>> = database.eventDao().getEvents()
    suspend fun getEvent(id: UUID): Event = database.eventDao().getEvent(id)
    fun updateEvent(event: Event){
        coroutineScope.launch { database.eventDao().updateEvent(event) }
    }

    companion object {
        private var INSTANCE: EventRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = EventRepository(context)
            }
        }

        fun get(): EventRepository {
            return INSTANCE ?:
            throw IllegalStateException("EventRepository must be initialized")
        }



    }
    suspend fun addEvent(event: Event) {
        database.eventDao().addEvent(event) }
}