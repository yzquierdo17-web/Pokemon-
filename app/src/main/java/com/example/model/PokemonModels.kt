package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class PokemonType(
    val displayName: String,
    val color: Color,
    val shortCode: String
) {
    NORMAL("Normal", TypeNormal, "NRM"),
    FIRE("Fire", TypeFire, "FIR"),
    WATER("Water", TypeWater, "WTR"),
    ELECTRIC("Electric", TypeElectric, "ELC"),
    GRASS("Grass", TypeGrass, "GRS"),
    ICE("Ice", TypeIce, "ICE"),
    FIGHTING("Fighting", TypeFighting, "FGT"),
    POISON("Poison", TypePoison, "PSN"),
    GROUND("Ground", TypeGround, "GRD"),
    FLYING("Flying", TypeFlying, "FLY"),
    PSYCHIC("Psychic", TypePsychic, "PSY"),
    BUG("Bug", TypeBug, "BUG"),
    ROCK("Rock", TypeRock, "RCK"),
    GHOST("Ghost", TypeGhost, "GHT"),
    DRAGON("Dragon", TypeDragon, "DRG"),
    DARK("Dark", TypeDark, "DRK"),
    STEEL("Steel", TypeSteel, "STL"),
    FAIRY("Fairy", TypeFairy, "FRY");

    companion object {
        fun fromString(name: String): PokemonType? {
            return entries.firstOrNull { it.name.equals(name.trim(), ignoreCase = true) }
        }
    }
}

enum class WeatherCondition(
    val displayName: String,
    val boostedTypes: Set<PokemonType>,
    val description: String
) {
    EXTREME(
        "Neutral / No Boost",
        emptySet(),
        "No weather damage modifiers active"
    ),
    SUNNY(
        "Sunny / Clear",
        setOf(PokemonType.FIRE, PokemonType.GRASS, PokemonType.GROUND),
        "+20% Fire, Grass & Ground damage"
    ),
    RAINY(
        "Rainy",
        setOf(PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.BUG),
        "+20% Water, Electric & Bug damage"
    ),
    PARTLY_CLOUDY(
        "Partly Cloudy",
        setOf(PokemonType.NORMAL, PokemonType.ROCK),
        "+20% Normal & Rock damage"
    ),
    CLOUDY(
        "Cloudy",
        setOf(PokemonType.FAIRY, PokemonType.FIGHTING, PokemonType.POISON),
        "+20% Fairy, Fighting & Poison damage"
    ),
    WINDY(
        "Windy",
        setOf(PokemonType.DRAGON, PokemonType.FLYING, PokemonType.PSYCHIC),
        "+20% Dragon, Flying & Psychic damage"
    ),
    SNOW(
        "Snow",
        setOf(PokemonType.ICE, PokemonType.STEEL),
        "+20% Ice & Steel damage"
    ),
    FOG(
        "Fog",
        setOf(PokemonType.DARK, PokemonType.GHOST),
        "+20% Dark & Ghost damage"
    )
}

enum class BattleLeague(
    val displayName: String,
    val shortName: String,
    val cpCapLabel: String
) {
    GREAT_LEAGUE("Great League", "Great", "Max 1500 CP"),
    ULTRA_LEAGUE("Ultra League", "Ultra", "Max 2500 CP"),
    MASTER_LEAGUE("Master League", "Master", "Unlimited CP"),
    PVE_RAIDS("PvE Raid Meta", "Raids", "Top DPS / TDO")
}

enum class MetaRole(val displayName: String, val badgeText: String) {
    LEAD("Lead Specialist", "LEAD"),
    CLOSER("Shield-Down Closer", "CLOSER"),
    SAFE_SWAP("Safe Switch", "SAFE SWAP"),
    CHARGER("Fast Energy Charger", "SPAMMER"),
    RAID_ATTACKER("Supreme Raid Attacker", "RAID S+")
}

data class MoveInfo(
    val name: String,
    val type: PokemonType,
    val power: Int,
    val energy: Int,
    val turns: Int = 2,
    val isEliteTm: Boolean = false
) {
    val formattedName: String
        get() = if (isEliteTm) "$name*" else name
}

data class PokemonMetaEntry(
    val id: String,
    val dexNumber: Int,
    val name: String,
    val formLabel: String? = null,
    val primaryType: PokemonType,
    val secondaryType: PokemonType? = null,
    val baseAtk: Int,
    val baseDef: Int,
    val baseSta: Int,
    val isShadow: Boolean = false,
    val isMegaOrPrimal: Boolean = false,
    val fastMove: MoveInfo,
    val chargedMove1: MoveInfo,
    val chargedMove2: MoveInfo? = null,
    val leagueScores: Map<BattleLeague, Double>,
    val role: MetaRole,
    val optimalIvSpread: Map<BattleLeague, String> = emptyMap(),
    val raidDps: Double,
    val raidTdo: Double,
    val keyWins: List<String> = emptyList(),
    val keyLosses: List<String> = emptyList()
) {
    val fullDisplayName: String
        get() = if (formLabel != null) "$name ($formLabel)" else name

    val spriteUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$dexNumber.png"

    val types: List<PokemonType>
        get() = listOfNotNull(primaryType, secondaryType)

    fun metaTierFor(league: BattleLeague): String {
        val score = leagueScores[league] ?: 75.0
        return when {
            score >= 94.5 -> "S+"
            score >= 91.0 -> "S"
            score >= 87.5 -> "A+"
            score >= 83.0 -> "A"
            else -> "B+"
        }
    }
}

data class RaidBossEntry(
    val id: String,
    val pokemon: PokemonMetaEntry,
    val tierLabel: String,
    val bossHp: Int,
    val raidTimerSeconds: Int = 300,
    val catchCpLv20Min: Int,
    val catchCpLv20Max: Int,
    val catchCpLv25Min: Int,
    val catchCpLv25Max: Int,
    val bossMovesets: List<String>,
    val soloOrDuoNote: String
)

data class SingleTargetMatchup(
    val opponent: PokemonMetaEntry,
    val offensiveMultiplier: Double,
    val defensiveMultiplier: Double,
    val advantageLabel: String,
    val isWinningMatchup: Boolean
)

data class CounterRecommendation(
    val counter: PokemonMetaEntry,
    val overallScore: Double,
    val bestMovesetText: String,
    val damageMultiplierDealt: Double,
    val damageMultiplierTaken: Double,
    val isWeatherBoosted: Boolean,
    val estimatedDps: Double,
    val estimatedTdo: Double,
    val timeToWinSeconds: Int,
    val recommendedTrainers: Double,
    val targetMatchups: List<SingleTargetMatchup>,
    val tacticalSummary: String
)

data class TeamWeaknessSummary(
    val type: PokemonType,
    val weakTeamMembers: List<PokemonMetaEntry>,
    val resistantTeamMembers: List<PokemonMetaEntry>,
    val maxMultiplier: Double,
    val averageMultiplier: Double,
    val threatSeverity: Float // 0f..1f for radar bar
)

data class OpposingTeamPreset(
    val id: String,
    val title: String,
    val league: BattleLeague,
    val description: String,
    val memberIds: List<String>
)
