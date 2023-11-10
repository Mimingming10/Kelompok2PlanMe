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
}
