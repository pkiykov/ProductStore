package com.example.productstore.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

fun <T : Any> Cursor.getList(clazz: KClass<T>, list: Array<String>): List<T> {
    val result = mutableListOf<T>()

    val parameters = clazz.primaryConstructor?.parameters
    while (moveToNext()) {
        var paramCount = 0
        val args = mutableMapOf<KParameter, Any>()
        list.forEach { value ->

            val property: KProperty<*>? = clazz.declaredMemberProperties
                .find { property ->
                    property.visibility == KVisibility.PUBLIC &&
                            property.name == value
                }
            parameters?.get(paramCount)?.let { parameter ->
                when (property?.returnType?.classifier) {
                    String::class -> args[parameter] = string(value)
                    Integer::class -> args[parameter] = integer(value)
                    Bitmap::class -> args[parameter] = bitmap(value)
                    else -> {
                    }
                }
                paramCount++
            }
        }
        clazz.primaryConstructor?.callBy(args)?.let { result.add(it) }
    }
    close()
    return result
}

fun <T : Any> Cursor.getSingleObject(clazz: KClass<T>, list: Array<String>): T? {
    val parameters = clazz.primaryConstructor?.parameters
    moveToFirst()
    var paramCount = 0
    val args = mutableMapOf<KParameter, Any>()
    list.forEach { value ->

        val property: KProperty<*>? = clazz.declaredMemberProperties
            .find { property ->
                property.visibility == KVisibility.PUBLIC &&
                        property.name == value
            }
        parameters?.get(paramCount)?.let { parameter ->
            when (property?.returnType?.classifier) {
                String::class -> {
                    args[parameter] = string(value)
                }
                Integer::class -> args[parameter] = integer(value)
                Bitmap::class -> args[parameter] = bitmap(value)
                else -> {
                }
            }
            paramCount++
        }
    }
    val result = clazz.primaryConstructor?.callBy(args)
    close()
    return result
}

fun Cursor.integer(column: String) = getInt(getColumnIndex(column))
fun Cursor.string(column: String) = getString(getColumnIndex(column)) ?: ""
fun Cursor.bytes(column: String) = getBlob(getColumnIndex(column))
fun Cursor.bitmap(column: String) =
    with(bytes(column)) { BitmapFactory.decodeByteArray(this, 0, this.size) }

fun SQLiteDatabase.doQuery(
    table: String,
    columns: Array<String>,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    groupBy: String? = null,
    having: String? = null,
    orderBy: String? = null
) = query(table, columns, selection, selectionArgs, groupBy, having, orderBy)

inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    val id = try {
        val returnedValue = this.function()
        setTransactionSuccessful()
        returnedValue
    } finally {
        endTransaction()
    }

    close()
    return id
}