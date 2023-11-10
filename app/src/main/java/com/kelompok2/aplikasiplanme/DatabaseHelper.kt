package com.kelompok2.aplikasiplanme
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {


    private val CREATE_TABLE_USER="CREATE TABLE $TABLE_USER(" +
            "$COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_USER_NAME TEXT," +
            "$COL_USER_EMAIL TEXT," +
            "$COL_USER_PASSWORD TEXT)"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
    }
    private val DROP_TABLE_USER = "DROP TABLE IF EXISTS $TABLE_USER"
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DROP_TABLE_USER)
    }

    fun registerUser() {}
    companion object{
        private const val DATABASE_VERSION =1
        private const val DATABASE_NAME = "user.db"
        private const val TABLE_USER = "tbl_user"

        private const val COL_USER_ID ="user_id"
        private const val COL_USER_NAME ="user_name"
        private const val COL_USER_EMAIL ="user_email"
        private const val COL_USER_PASSWORD ="user_password"
    }
}