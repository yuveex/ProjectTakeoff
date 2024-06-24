package com.ubaya.projecttakeoff.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user")
    fun selectAllUser():List<User>

    @Query("SELECT * FROM user " +
            "WHERE id = :id")
    fun selectUser(id: Int): User

    @Update
    fun update(user: User)

    @Delete
    fun deleteTodo(vararg user: User)
}