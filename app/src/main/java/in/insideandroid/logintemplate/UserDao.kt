package `in`.insideandroid.logintemplate

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("Select * FROM User WHERE email = :email")
    fun get(email: String): User

    @Insert
    fun insertAll(user: User)

    @Update
    fun update(user: User)
}