package com.example.kotlindemo.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {

        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val creattableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"

        p0?.execSQL(creattableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(dropTableQuery)
        onCreate(p0)
    }

    fun insertNote(note: Note) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)

        }
        db.insert(TABLE_NAME, null, value)
        db.close()

    }

    fun getAllNotes(): List<Note> {

        val notList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT*FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {

            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Note(id, title, content)
            notList.add(note)
        }
        cursor.close()
        db.close()
        return notList

    }

    fun updateNote(note: Note) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)

        }

        val whereClause = "$COLUMN_ID = ?"
        val whereargs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, value, whereClause, whereargs)
        db.close()

    }

    fun getNoteByID(noteId: Int): Note {

        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id, title, content)

    }

    fun deleteNote(noteId: Int) {
        val db = readableDatabase

        val whereClause = "$COLUMN_ID = ?"
        val whereargs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereargs)
        db.close()


    }

}