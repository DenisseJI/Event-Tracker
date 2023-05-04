package com.example.a2project

import android.app.Application

class EventIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EventRepository.initialize(this)
    }
}