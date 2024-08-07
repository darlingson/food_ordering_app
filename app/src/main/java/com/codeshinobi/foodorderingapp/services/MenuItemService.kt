package com.codeshinobi.foodorderingapp.services

import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.models.Document
import io.appwrite.services.Databases

class MenuItemService(client : Client) {
    companion object {
        private const val FoodOrderingDBId = "food_ordering_db"
        private const val MenuItemCollectionId = "menu_item"
    }

    private val databases = Databases(client)

    suspend fun fetch(): List<Document<Map<String, Any>>> {
        return databases.listDocuments(
            FoodOrderingDBId,
            MenuItemCollectionId,
            listOf(Query.orderDesc("\$createdAt"), Query.limit(10))
        ).documents
    }

    suspend fun fetchById(id: String): Document<Map<String, Any>> {
        return databases.getDocument(
            FoodOrderingDBId,
            MenuItemCollectionId,
            id
        )
    }
    suspend fun add(
        userId: String,
        title: String,
        description: String
    ): Document<Map<String, Any>> {
        return databases.createDocument(
            FoodOrderingDBId,
            MenuItemCollectionId,
            ID.unique(),
            mapOf(
                "userId" to userId,
                "title" to title,
                "description" to description
            )
        )
    }

    suspend fun remove(id: String) {
        databases.deleteDocument(
            FoodOrderingDBId,
            MenuItemCollectionId,
            id
        )
    }
}