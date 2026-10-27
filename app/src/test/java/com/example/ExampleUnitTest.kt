package com.example

import com.example.data.PokemonMetaSeedData
import com.example.engine.TypeEffectivenessEngine
import com.example.model.BattleLeague
import com.example.model.PokemonType
import com.example.model.WeatherCondition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun doubleSuperEffectiveMultiplier_isAccurateForPokemonGo() {
        // Rayquaza (Dragon / Flying) is 2.56x double weak to Ice in Pokémon GO
        val mult = TypeEffectivenessEngine.getDualTypeMultiplier(
            attackType = PokemonType.ICE,
            primaryDefend = PokemonType.DRAGON,
            secondaryDefend = PokemonType.FLYING
        )
        assertEquals(2.56, mult, 0.01)
    }

    @Test
    fun immunityInMainSeries_is0391MultiplierInPokemonGo() {
        // Ground vs Flying is 0.391x in Pokémon GO
        val mult = TypeEffectivenessEngine.getDualTypeMultiplier(
            attackType = PokemonType.GROUND,
            primaryDefend = PokemonType.FLYING,
            secondaryDefend = null
        )
        assertEquals(0.391, mult, 0.01)
    }

    @Test
    fun raidCounterSimulator_ranksIceCountersFirstAgainstMegaRayquaza() {
        val rayquazaBoss = PokemonMetaSeedData.raidBosses.first { it.id == "boss_rayquaza_mega" }
        val counters = TypeEffectivenessEngine.simulateRaidCounters(
            boss = rayquazaBoss,
            candidatePool = PokemonMetaSeedData.allPokemon,
            weather = WeatherCondition.SNOW,
            includeShadows = true,
            includeMegas = true
        )
        assertTrue(counters.isNotEmpty())
        val topCounter = counters.first()
        assertEquals("mamoswine_shadow", topCounter.counter.id)
        assertEquals(2.56, topCounter.damageMultiplierDealt, 0.01)
    }

    @Test
    fun teamWeaknessAnalyzer_identifiesSharedVulnerabilities() {
        val team = PokemonMetaSeedData.allPokemon.filter {
            it.id in listOf("clodsire", "feraligatr_shadow", "mandibuzz")
        }
        val weaknesses = TypeEffectivenessEngine.analyzeTeamWeaknesses(team)
        assertTrue(weaknesses.isNotEmpty())
        val counters = TypeEffectivenessEngine.recommendCountersForTeam(
            opposingTeam = team,
            candidatePool = PokemonMetaSeedData.allPokemon,
            league = BattleLeague.GREAT_LEAGUE,
            weather = WeatherCondition.EXTREME,
            includeShadows = true,
            includeMegas = false
        )
        assertTrue(counters.isNotEmpty())
    }
}
