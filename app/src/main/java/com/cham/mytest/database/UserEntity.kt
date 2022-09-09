package com.cham.mytest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/10/20 17:30
 * @UpdateUser:
 * @UpdateDate:     2020/10/20 17:30
 * @UpdateRemark:
 */
@Entity(tableName = "User")
data class UserEntity(

    var userName: String,
    var userP: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Long = 0
)