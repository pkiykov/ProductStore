package com.example.productstore.db

import android.provider.BaseColumns

const val DATABASE_NAME = "product_store.db"
const val DATABASE_VERSION = 10

object ProductEntry: BaseColumns {

    const val TABLE_NAME = "product"
    const val _ID = "id"
    const val NAME_COLUMN = "name"
    const val PRICE_COLUMN = "price"
    const val IMAGE_URL_COLUMN = "imageUrl"
}

/*
* Some values such as 'name' or 'price' can be retrieved by 'join' opereation,
* so we may not need in duplicating them in another table. Depends on the requirements
* */
object ProductDetailedEntry: BaseColumns {

    const val TABLE_NAME = "product_detailed"
    const val _ID = "id"
    const val NAME_COLUMN = "name"
    const val PRICE_COLUMN = "price"
    const val IMAGE_URL_COLUMN = "imageUrl"
    const val DESCRIPTION_COLUMN = "description"
}