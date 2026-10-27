package com.example.data

import com.example.data.local.SavedSquadDao
import com.example.data.local.SavedSquadEntity
import com.example.model.OpposingTeamPreset
import com.example.model.PokemonMetaEntry
import com.example.model.RaidBossEntry
import com.example.network.PokeApiMapper
import com.example.network.PokeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RaidPulseRepository(
    private val savedSquadDao: SavedSquadDao,
    private val pokeApiService: PokeApiService = PokeApiService.create()
) {
    private val _pokemonCatalog = MutableStateFlow(PokemonMetaSeedData.allPokemon)
    val pokemonCatalog: StateFlow<List<PokemonMetaEntry>> = _pokemonCatalog.asStateFlow()

    val raidBosses: List<RaidBossEntry> = PokemonMetaSeedData.raidBosses
    val opposingPresets: List<OpposingTeamPreset> = PokemonMetaSeedData.opposingTeamPresets

    val savedSquadsFlow: Flow<List<SavedSquadEntity>> = savedSquadDao.observeAllSquads()

    suspend fun ensureStarterSquadsSeeded() {
        if (savedSquadDao.getCount() == 0) {
            savedSquadDao.insertSquad(
                SavedSquadEntity(
                    title = "Anti-BBML Great League Trio",
                    modeLabel = "Great League • PvP Counter Trio",
                    targetNamesCsv = "Clodsire, Feraligatr (Shadow), Mandibuzz",
                    counterIdsCsv = "gastrodon,azumarill,serperior",
                    avgTacticalScore = 95.8,
                    notes = "Gastrodon walls Clodsire & Bastiodon; Azumarill shuts down Mandibuzz; Serperior pressures Water & Ground."
                )
            )
            savedSquadDao.insertSquad(
                SavedSquadEntity(
                    title = "Mega Rayquaza Duo Ice Team",
                    modeLabel = "Mega Legendary Raid • Snow Boost",
                    targetNamesCsv = "Rayquaza (Mega)",
                    counterIdsCsv = "mamoswine_shadow,mewtwo_shadow,gardevoir_mega",
                    avgTacticalScore = 98.4,
                    notes = "Exploits 2.56x Double Ice weakness for ~145s Time-to-Win (Easy 2-Trainer Duo)."
                )
            )
        }
    }

    suspend fun saveSquad(squad: SavedSquadEntity) {
        savedSquadDao.insertSquad(squad)
    }

    suspend fun deleteSquad(id: Int) {
        savedSquadDao.deleteSquadById(id)
    }

    /**
     * Fetches a Pokémon live from PokéAPI by name or National Dex number,
     * converts its stats to Pokémon GO mechanics, and adds it to the active catalog.
     */
    suspend fun fetchLivePokemonFromPokeApi(rawQuery: String): Result<PokemonMetaEntry> {
        val sanitized = rawQuery.trim().lowercase().replace(" ", "-")
        if (sanitized.isBlank()) {
            return Result.failure(IllegalArgumentException("Enter a Pokémon name or Pokédex #"))
        }
        return try {
            val response = pokeApiService.getPokemon(sanitized)
            val entry = PokeApiMapper.toPokemonMetaEntry(response)
            val current = _pokemonCatalog.value
            if (current.none { it.dexNumber == entry.dexNumber && it.formLabel == entry.formLabel }) {
                _pokemonCatalog.value = listOf(entry) + current
            }
            Result.success(entry)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
