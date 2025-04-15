package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.todo.data.entity.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<Todo>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM todo WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Todo

    @Query("SELECT * FROM todo WHERE id = :id")
    fun findById(id: Int): Todo

    @Upsert
    suspend fun upsert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}



