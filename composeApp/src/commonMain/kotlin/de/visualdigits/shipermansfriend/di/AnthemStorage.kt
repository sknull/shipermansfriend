package de.visualdigits.shipermansfriend.di

interface AnthemStorage {
    suspend fun prepareAnthem(countryCode: String): String?
}
