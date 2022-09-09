package com.cham.mytest.database

import androidx.room.*

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/10/20 17:32
 * @UpdateUser:
 * @UpdateDate:     2020/10/20 17:32
 * @UpdateRemark:
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user ")
     fun getsAll(): List<UserEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(vararg stu: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUser(users: List<UserEntity>)






    @Query("select * from user limit 100")
     fun queryTop100(): List<UserEntity>

//    //删除最前的100条数据
//    @Query("DELETE FROM user  where id  in    (select * from user  limit 100) ")
//    suspend fun deleteTop1002()


    @Query("DELETE  FROM user ")
     fun deleteAll()

    @Delete
     fun delete( users: List<UserEntity>)
}