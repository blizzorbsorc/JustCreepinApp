package com.example.justcreepinapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Location class

class LocationDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "expenses.db", null, 1) {
    // called when database is created for first time
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
                CREATE TABLE locations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    holiday TEXT,
                    type TEXT,
                    latitude TEXT,
                    longitude TEXT
                )
            """.trimIndent())
    }
    // called when database needs to be upgraded
    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS locations")
        onCreate(db)
    }
    // CRUD operations
    fun insertLocation(holiday: String, type: String, latitude:String, longitude: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("holiday", holiday)
            put("type", type)
            put("latitude", latitude)
            put("longitude", longitude)
        }
        db.insert("locations", null, values)
        db.close()
    }
    // returns a list of all locations
    fun getAllLocations(): List<Location> {
        val locations = mutableListOf<Location>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM locations", null)
        while (cursor.moveToNext()) {
            locations.add(
                Location(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                )
            )
        }
        cursor.close()
        db.close()
        return locations
    }
    // returns a single location by id

    fun deleteLocation(id: Int) {
        val db = writableDatabase
        db.delete("locations", "id=?", arrayOf(id.toString()))
        db.close()
    }
    // returns a single location by id

    fun updateLocation(id: Int, holiday: String, type: String, latitude:String, longitude: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("holiday", holiday)
            put("type", type)
            put("latitude", latitude)
            put("longitude", longitude)
        }

        // returns number of rows updated (optional to use)
        db.update(
            "locations",
            values,
            "id = ?",
            arrayOf(id.toString())
        )

        db.close()
    }
}