package com.nampt.hectrechallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nampt.hectrechallenge.data.local.entity.DetailRowEntity
import com.nampt.hectrechallenge.data.local.entity.RateVolumeEntity
import com.nampt.hectrechallenge.data.util.Constants

@Dao
interface TimeSheetDao {
    @Query("SELECT * FROM ${Constants.RateVolumeTable}")
    suspend fun getAllJob(): List<RateVolumeEntity>

    @Query("SELECT * FROM ${Constants.DetailRowTable}")
    suspend fun getAllDetailRow(): List<DetailRowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(albums: List<RateVolumeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albums: List<DetailRowEntity>)

    @Query("DELETE FROM ${Constants.RateVolumeTable}")
    fun clearRateVolumeTable()

    @Query("DELETE FROM ${Constants.DetailRowTable}")
    fun clearDetailRowTable()

}
