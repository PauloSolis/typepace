package `in`.insideandroid.logintemplate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 3)
abstract class MyDatabase: RoomDatabase() {

    abstract fun users(): UserDao

    companion object {
        @Volatile
        private  var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase? {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "app_database1.db3"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}