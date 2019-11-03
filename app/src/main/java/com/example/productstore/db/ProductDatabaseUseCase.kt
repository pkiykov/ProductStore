package com.example.productstore.db

import com.example.productstore.db.models.ProductDbModel
import com.example.productstore.db.models.ProductDetailedDbModel
import com.example.productstore.products.Product
import com.example.productstore.products.ProductDetailed

class ProductDatabaseUseCase(private val productDbQueries: ProductDbQueries) {

    fun storeProductDetailed(product: ProductDetailed) {
        productDbQueries.storeDetailedProduct(convertProductDetailedToDbModel(product))
    }

    private fun convertProductToDbModel(product: Product): ProductDbModel =
        ProductDbModel(product.id, product.name, product.price.toString(), product.imageUrl)

    private fun convertProductDetailedToDbModel(product: ProductDetailed) =
        ProductDetailedDbModel(
            product.id,
            product.name,
            product.price.toString(),
            product.imageUrl,
            product.description
        )

    fun fetchProductList() = convertProductsListFromDbModel(productDbQueries.readAllProducts())

    fun fetchProductDetailed(id: String) = convertProductDetailedFromDbModel(productDbQueries.readProductDetailed(id))

    private fun convertProductsListFromDbModel(dbProductList: List<ProductDbModel>): List<Product> =
        dbProductList.map { Product(it.id, it.name, it.price.toInt(), it.imageUrl) }

    private fun convertProductDetailedFromDbModel(dbProductDetailed: ProductDetailedDbModel?): ProductDetailed? {
        if (dbProductDetailed == null) {
            return null
        }
        return ProductDetailed(
            dbProductDetailed.id,
            dbProductDetailed.name,
            dbProductDetailed.price.toInt(),
            dbProductDetailed.imageUrl,
            dbProductDetailed.description
        )
    }

    fun storeProductList(result: List<Product>) {
        productDbQueries.storeProductList(result.map { convertProductToDbModel(it) })
    }
}



