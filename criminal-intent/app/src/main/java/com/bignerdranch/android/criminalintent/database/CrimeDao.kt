// takes care of data access object

package com.bignerdranch.android.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Crime
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CrimeDao {
    // database query functions
    @Query("SELECT * FROM crime")
    fun getCrimes(): Flow<List<Crime>>
    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID): Crime
    // update functions
    @Update
    suspend fun updateCrime(crime: Crime)
}