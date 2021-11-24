package `in`.insideandroid.logintemplate

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="User")
class User(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    val email: String,
    val password: String,
    val SD: Double,
    val average: Double
    ): Serializable
