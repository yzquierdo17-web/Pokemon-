package com.example.data.local

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "saved_squads")
data class SavedSquadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val modeLabel: String, // e.g. "Great League Counter Trio" or "Mega Rayquaza Raid Squad"
    val targetNamesCsv: String,
    val counterIdsCsv: String,
    val avgTacticalScore: Double,
    val notes: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface SavedSquadDao {
    @Query("SELECT * FROM saved_squads ORDER BY createdAt DESC")
    fun observeAllSquads(): Flow<List<SavedSquadEntity>>

    @Query("SELECT COUNT(*) FROM saved_squads")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: SavedSquadEntity)

    @Query("DELETE FROM saved_squads WHERE id = :id")
    suspend fun deleteSquadById(id: Int)
}

@Database(entities = [SavedSquadEntity::class], version = 1, exportSchema = false)
abstract class RaidPulseDatabase : RoomDatabase() {
    abstract fun savedSquadDao(): SavedSquadDao

    companion object {
        @Volatile
        private var INSTANCE: RaidPulseDatabase? = null

        fun getInstance(context: Context): RaidPulseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RaidPulseDatabase::class.java,
                    "raid_pulse_go.db"
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
