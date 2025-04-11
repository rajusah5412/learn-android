package com.example.todo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.Date


@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)  val id: Int = 0,
    val title: String,
    val content: String? = null,
    val createDate: Instant = Clock.System.now(),
    val isCompleted : Boolean = false
)


class DateConverter{
    @TypeConverter
    fun fromLongToInstant(value: Long) : Instant {
        return Instant.fromEpochMilliseconds(value)
    }

    @TypeConverter
    fun fromInstantToLong(value: Instant) : Long {
        return value.toEpochMilliseconds()
    }
}

abstract class Fruit

interface  SweetFruit{

}

interface SourFruit

class Mango : Fruit(), SweetFruit, SourFruit
class Grape