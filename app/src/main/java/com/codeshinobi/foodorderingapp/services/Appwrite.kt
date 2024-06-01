package com.codeshinobi.foodorderingapp.services

import android.content.Context
import io.appwrite.Client

object Appwrite {
    private const val ENDPOINT = "https://cloud.appwrite.io/v1"
    private const val PROJECT_ID = "<YOUR_PROJECT_ID>"

    private lateinit var client: Client

    fun init(context: Context) {
        client = Client(context)
            .setEndpoint(ENDPOINT)
            .setProject(PROJECT_ID)
    }
}