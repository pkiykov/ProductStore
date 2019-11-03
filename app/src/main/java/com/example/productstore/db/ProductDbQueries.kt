package com.example.productstore.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import com.example.productstore.db.models.ProductDbModel
import com.example.productstore.db.models.ProductDetailedDbModel

class ProductDbQueries(context: Context) {

    private val dbHelper = ProductDb(context)

    fun storeProductList(productList: List<ProductDbModel>) =
        dbHelper.writableDatabase.transaction {
            productList.forEach { product ->
                insertProduct(product)
            }
        }

    fun storeDetailedProduct(product: ProductDetailedDbModel) = dbHelper.writableDatabase
        .transaction {
            insertDetailedProduct(product)
        }

    private fun SQLiteDatabase.insertProduct(product: ProductDbModel) {
        insertWithOnConflict(ProductEntry.TABLE_NAME, null, ContentValues().apply {
            put(ProductEntry._ID, product.id)
            put(ProductEntry.NAME_COLUMN, product.name)
            put(ProductEntry.PRICE_COLUMN, product.price)
            put(ProductEntry.IMAGE_URL_COLUMN, product.imageUrl)
        }, CONFLICT_REPLACE)
    }

    private fun SQLiteDatabase.insertDetailedProduct(product: ProductDetailedDbModel) {
        insertWithOnConflict(ProductDetailedEntry.TABLE_NAME, null, ContentValues().apply {
            put(ProductDetailedEntry._ID, product.id)
            put(ProductDetailedEntry.NAME_COLUMN, product.name)
            put(ProductDetailedEntry.PRICE_COLUMN, product.price)
            put(ProductDetailedEntry.IMAGE_URL_COLUMN, product.imageUrl)
            put(ProductDetailedEntry.DESCRIPTION_COLUMN, product.description)
        }, CONFLICT_REPLACE)
    }

    fun readAllProducts(): List<ProductDbModel> = dbHelper.readableDatabase.transaction {
        val columns = arrayOf(
            ProductEntry._ID,
            ProductEntry.NAME_COLUMN,
            ProductEntry.PRICE_COLUMN,
            ProductEntry.IMAGE_URL_COLUMN
        )

        doQuery(ProductEntry.TABLE_NAME, columns, orderBy = "${ProductEntry._ID} ASC")
            .getList(ProductDbModel::class, columns)
    }

    fun readProductDetailed(id: String): ProductDetailedDbModel? =
        dbHelper.readableDatabase.transaction {
            val columns = arrayOf(
                ProductDetailedEntry._ID,
                ProductDetailedEntry.NAME_COLUMN,
                ProductDetailedEntry.PRICE_COLUMN,
                ProductDetailedEntry.IMAGE_URL_COLUMN,
                ProductDetailedEntry.DESCRIPTION_COLUMN
            )
            val where = "${ProductEntry._ID} = '$id'"

            doQuery(ProductDetailedEntry.TABLE_NAME, columns, selection = where)
                .getSingleObject(ProductDetailedDbModel::class, columns)
        }
}