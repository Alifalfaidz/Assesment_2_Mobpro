package org.d3if0145.assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0145.assesment2.model.Parfum

@Database(entities = [Parfum::class], version = 1, exportSchema = false)
abstract class ParfumDb : RoomDatabase(){
    abstract val dao: ParfumDao

    companion object {

        @Volatile
        private var INSTANCE: ParfumDb? = null

        fun getInstance(context: Context): ParfumDb {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParfumDb::class.java,
                        "mahasiswa.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
