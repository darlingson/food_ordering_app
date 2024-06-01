package com.codeshinobi.foodorderingapp.services
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.models.Document
import io.appwrite.services.Databases

class IdeaService(client: Client) {
    companion object {
        private const val ideaDatabaseId = "food_ordering_db"
        private const val ideaCollectionId = "ideasTracker"
    }

    private val databases = Databases(client)

    suspend fun fetch(): List<Document<Map<String, Any>>> {
        return databases.listDocuments(
            ideaDatabaseId,
            ideaCollectionId,
            listOf(Query.orderDesc("\$createdAt"), Query.limit(10))
        ).documents
    }

    suspend fun add(
        userId: String,
        title: String,
        description: String
    ): Document<Map<String, Any>> {
        return databases.createDocument(
            ideaDatabaseId,
            ideaCollectionId,
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
            ideaDatabaseId,
            ideaCollectionId,
            id
        )
    }
}