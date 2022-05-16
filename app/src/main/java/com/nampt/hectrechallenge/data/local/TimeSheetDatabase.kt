package com.nampt.hectrechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nampt.hectrechallenge.data.local.entity.DetailRowEntity
import com.nampt.hectrechallenge.data.local.entity.RateVolumeEntity

@Database(
    entities = [DetailRowEntity::class, RateVolumeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TimeSheetDatabase : RoomDatabase() {
    abstract val timeSheetDao: TimeSheetDao
}