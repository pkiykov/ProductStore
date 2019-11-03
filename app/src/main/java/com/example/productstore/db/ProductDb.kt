package com.example.productstore.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDb(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val createProducts = "CREATE TABLE ${ProductEntry.TABLE_NAME} (" +
            "${ProductEntry._ID} TEXT PRIMARY KEY," +
            "${ProductEntry.NAME_COLUMN} TEXT," +
            "${ProductEntry.PRICE_COLUMN} TEXT," +
            "${ProductEntry.IMAGE_URL_COLUMN} TEXT" +
            ")"

    private val createDetailedProducts = "CREATE TABLE ${ProductDetailedEntry.TABLE_NAME} (" +
            "${ProductDetailedEntry._ID} TEXT PRIMARY KEY," +
            "${ProductDetailedEntry.NAME_COLUMN} TEXT," +
            "${ProductDetailedEntry.PRICE_COLUMN} TEXT," +
            "${ProductDetailedEntry.DESCRIPTION_COLUMN} TEXT," +
            "${ProductDetailedEntry.IMAGE_URL_COLUMN} TEXT" +
            ")"

    private val dropDatabase = "DROP TABLE IF EXISTS ${ProductEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(createProducts)
       db.execSQL(createDetailedProducts)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(dropDatabase)
        onCreate(db)
    }

}