// type conversion for database

package com.bignerdranch.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverters {
    // tell Room compiler how to convert the type to store in database
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    // tell Room how to convert from database representation to original type
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}