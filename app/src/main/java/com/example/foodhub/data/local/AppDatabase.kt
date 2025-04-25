package com.example.foodhub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}