package com.ubaya.projecttakeoff.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user")
    fun selectAllUser():List<User>

    @Query("SELECT id, email, username, first_name, last_name, profile_picture FROM user " +
            "WHERE username = :username " +
            "AND password = :password")
    fun login(username: String, password: String): User?

    @Query("SELECT id, email, username, first_name, last_name, profile_picture FROM user " +
            "WHERE id = :id")
    fun selectUser(id: Int): User?

    // To avoid updating the password to empty (Since the password would always be empty Client side).
    @Query("UPDATE user SET email = :email, username = :username, first_name = :firstName, " +
            "last_name = :lastName, profile_picture = :profilePicture WHERE id = :id")
    fun updateUser(id: Int, email: String, username: String, firstName: String,
                   lastName: String, profilePicture: String): Int

    @Query("UPDATE user SET password = :newPassword WHERE id = :id AND password = :oldPassword")
    fun updatePassword(id: Int, oldPassword: String, newPassword: String): Int

    @Delete
    fun deleteUser(vararg user: User)
}