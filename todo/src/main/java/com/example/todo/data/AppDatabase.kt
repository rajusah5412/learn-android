package com.example.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.dao.TodoDao
import com.example.todo.data.entity.DateConverter
import com.example.todo.data.entity.Todo

@TypeConverters(DateConverter::class)
@Database(entities = [Todo::class], version = 2, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
           return if(INSTANCE == null){
               INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "todo.db"
                )
                   .fallbackToDestructiveMigration(true)
                    .build()
               INSTANCE!!;
            }else{
                INSTANCE!!
            }
        }
    }
}




//class Account(val accountHolderName: String) {
//    var accountNo : Long  = 0L
//    fun withDraw(){
//
//    }
//
//    companion object{
//
//
//        @JvmStatic
//        fun deposit(amount: Double){
//
//        }
//    }
//}