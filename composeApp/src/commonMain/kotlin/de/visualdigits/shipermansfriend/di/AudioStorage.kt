package de.visualdigits.shipermansfriend.di

interface AudioStorage {
    suspend fun prepareAudio(fileName: String): String?
}
