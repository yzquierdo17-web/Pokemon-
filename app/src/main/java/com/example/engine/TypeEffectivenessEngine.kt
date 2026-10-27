package com.example.engine

import com.example.model.*
import kotlin.math.max
import kotlin.math.roundToInt

object TypeEffectivenessEngine {

    private const val SE = 1.6
    private const val NVE = 0.625
    private const val IMM = 0.390625 // Pokémon GO immunity multiplier (0.625^2)

    /**
     * Returns the exact Pokémon GO single-type effectiveness multiplier
     * when an attack of [attackType] hits a defender of [defendType].
     */
    fun getSingleTypeMultiplier(attackType: PokemonType, defendType: PokemonType): Double {
        return when (attackType) {
            PokemonType.NORMAL -> when (defendType) {
                PokemonType.ROCK, PokemonType.STEEL -> NVE
                PokemonType.GHOST -> IMM
                else -> 1.0
            }
            PokemonType.FIRE -> when (defendType) {
                PokemonType.GRASS, PokemonType.ICE, PokemonType.BUG, PokemonType.STEEL -> SE
                PokemonType.FIRE, PokemonType.WATER, PokemonType.ROCK, PokemonType.DRAGON -> NVE
                else -> 1.0
            }
            PokemonType.WATER -> when (defendType) {
                PokemonType.FIRE, PokemonType.GROUND, PokemonType.ROCK -> SE
                PokemonType.WATER, PokemonType.GRASS, PokemonType.DRAGON -> NVE
                else -> 1.0
            }
            PokemonType.ELECTRIC -> when (defendType) {
                PokemonType.WATER, PokemonType.FLYING -> SE
                PokemonType.ELECTRIC, PokemonType.GRASS, PokemonType.DRAGON -> NVE
                PokemonType.GROUND -> IMM
                else -> 1.0
            }
            PokemonType.GRASS -> when (defendType) {
                PokemonType.WATER, PokemonType.GROUND, PokemonType.ROCK -> SE
                PokemonType.FIRE, PokemonType.GRASS, PokemonType.POISON,
                PokemonType.FLYING, PokemonType.BUG, PokemonType.DRAGON, PokemonType.STEEL -> NVE
                else -> 1.0
            }
            PokemonType.ICE -> when (defendType) {
                PokemonType.GRASS, PokemonType.GROUND, PokemonType.FLYING, PokemonType.DRAGON -> SE
                PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL -> NVE
                else -> 1.0
            }
            PokemonType.FIGHTING -> when (defendType) {
                PokemonType.NORMAL, PokemonType.ICE, PokemonType.ROCK,
                PokemonType.DARK, PokemonType.STEEL -> SE
                PokemonType.POISON, PokemonType.FLYING, PokemonType.PSYCHIC,
                PokemonType.BUG, PokemonType.FAIRY -> NVE
                PokemonType.GHOST -> IMM
                else -> 1.0
            }
            PokemonType.POISON -> when (defendType) {
                PokemonType.GRASS, PokemonType.FAIRY -> SE
                PokemonType.POISON, PokemonType.GROUND, PokemonType.ROCK, PokemonType.GHOST -> NVE
                PokemonType.STEEL -> IMM
                else -> 1.0
            }
            PokemonType.GROUND -> when (defendType) {
                PokemonType.FIRE, PokemonType.ELECTRIC, PokemonType.POISON,
                PokemonType.ROCK, PokemonType.STEEL -> SE
                PokemonType.GRASS, PokemonType.BUG -> NVE
                PokemonType.FLYING -> IMM
                else -> 1.0
            }
            PokemonType.FLYING -> when (defendType) {
                PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.BUG -> SE
                PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.STEEL -> NVE
                else -> 1.0
            }
            PokemonType.PSYCHIC -> when (defendType) {
                PokemonType.FIGHTING, PokemonType.POISON -> SE
                PokemonType.PSYCHIC, PokemonType.STEEL -> NVE
                PokemonType.DARK -> IMM
                else -> 1.0
            }
            PokemonType.BUG -> when (defendType) {
                PokemonType.GRASS, PokemonType.PSYCHIC, PokemonType.DARK -> SE
                PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.POISON,
                PokemonType.FLYING, PokemonType.GHOST, PokemonType.STEEL, PokemonType.FAIRY -> NVE
                else -> 1.0
            }
            PokemonType.ROCK -> when (defendType) {
                PokemonType.FIRE, PokemonType.ICE, PokemonType.FLYING, PokemonType.BUG -> SE
                PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.STEEL -> NVE
                else -> 1.0
            }
            PokemonType.GHOST -> when (defendType) {
                PokemonType.PSYCHIC, PokemonType.GHOST -> SE
                PokemonType.DARK -> NVE
                PokemonType.NORMAL -> IMM
                else -> 1.0
            }
            PokemonType.DRAGON -> when (defendType) {
                PokemonType.DRAGON -> SE
                PokemonType.STEEL -> NVE
                PokemonType.FAIRY -> IMM
                else -> 1.0
            }
            PokemonType.DARK -> when (defendType) {
                PokemonType.PSYCHIC, PokemonType.GHOST -> SE
                PokemonType.FIGHTING, PokemonType.DARK, PokemonType.FAIRY -> NVE
                else -> 1.0
            }
            PokemonType.STEEL -> when (defendType) {
                PokemonType.ICE, PokemonType.ROCK, PokemonType.FAIRY -> SE
                PokemonType.FIRE, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.STEEL -> NVE
                else -> 1.0
            }
            PokemonType.FAIRY -> when (defendType) {
                PokemonType.FIGHTING, PokemonType.DRAGON, PokemonType.DARK -> SE
                PokemonType.FIRE, PokemonType.POISON, PokemonType.STEEL -> NVE
                else -> 1.0
            }
        }
    }

    /**
     * Calculates combined Pokémon GO damage multiplier against a single or dual-type Pokémon.
     */
    fun getDualTypeMultiplier(
        attackType: PokemonType,
        primaryDefend: PokemonType,
        secondaryDefend: PokemonType?
    ): Double {
        val m1 = getSingleTypeMultiplier(attackType, primaryDefend)
        val m2 = if (secondaryDefend != null && secondaryDefend != primaryDefend) {
            getSingleTypeMultiplier(attackType, secondaryDefend)
        } else {
            1.0
        }
        val raw = m1 * m2
        return when {
            raw > 2.4 -> 2.56
            raw > 1.4 -> 1.6
            raw < 0.30 -> 0.244
            raw < 0.45 -> 0.391
            raw < 0.80 -> 0.625
            else -> 1.0
        }
    }

    fun getMultiplierAgainstPokemon(attackType: PokemonType, defender: PokemonMetaEntry): Double {
        return getDualTypeMultiplier(attackType, defender.primaryType, defender.secondaryType)
    }

    /**
     * Evaluates all 18 attacking types against a dual-type combination and groups them by multiplier.
     */
    fun getDefensiveProfile(
        primaryType: PokemonType,
        secondaryType: PokemonType?
    ): Map<Double, List<PokemonType>> {
        return PokemonType.entries
            .groupBy { attackType ->
                getDualTypeMultiplier(attackType, primaryType, secondaryType)
            }
    }

    /**
     * Analyzes an opposing team (1 to 6 Pokémon) and ranks all 18 offensive types
     * by how severely they exploit the opposing lineup.
     */
    fun analyzeTeamWeaknesses(opposingTeam: List<PokemonMetaEntry>): List<TeamWeaknessSummary> {
        if (opposingTeam.isEmpty()) return emptyList()

        return PokemonType.entries.map { attackType ->
            val weakMembers = mutableListOf<PokemonMetaEntry>()
            val resistMembers = mutableListOf<PokemonMetaEntry>()
            var maxMult = 0.0
            var sumMult = 0.0

            opposingTeam.forEach { member ->
                val mult = getMultiplierAgainstPokemon(attackType, member)
                if (mult > 1.1) weakMembers.add(member)
                if (mult < 0.9) resistMembers.add(member)
                if (mult > maxMult) maxMult = mult
                sumMult += mult
            }

            val avgMult = sumMult / opposingTeam.size
            // Normalize severity between 0f and 1f
            val weaknessRatio = weakMembers.size.toFloat() / opposingTeam.size.toFloat()
            val severity = ((avgMult - 0.5) / 1.6 * 0.65 + weaknessRatio * 0.35)
                .coerceIn(0.05, 1.0)
                .toFloat()

            TeamWeaknessSummary(
                type = attackType,
                weakTeamMembers = weakMembers,
                resistantTeamMembers = resistMembers,
                maxMultiplier = maxMult,
                averageMultiplier = (avgMult * 100.0).roundToInt() / 100.0,
                threatSeverity = severity
            )
        }.sortedWith(
            compareByDescending<TeamWeaknessSummary> { it.weakTeamMembers.size }
                .thenByDescending { it.averageMultiplier }
                .thenBy { it.resistantTeamMembers.size }
        )
    }

    /**
     * Computes the best offensive move multiplier & chosen move for [attacker] against [defender].
     */
    private fun evaluateBestOffensiveMove(
        attacker: PokemonMetaEntry,
        defender: PokemonMetaEntry,
        weather: WeatherCondition
    ): Triple<Double, MoveInfo, Boolean> {
        val candidateChargedMoves = listOfNotNull(attacker.chargedMove1, attacker.chargedMove2)
        var bestMove = attacker.chargedMove1
        var bestEffectivePower = -1.0
        var bestTypeMult = 1.0
        var isWeatherBoosted = false

        for (charged in candidateChargedMoves) {
            val chargedMult = getMultiplierAgainstPokemon(charged.type, defender)
            val fastMult = getMultiplierAgainstPokemon(attacker.fastMove.type, defender)
            val stabCharged = if (attacker.types.contains(charged.type)) 1.2 else 1.0
            val stabFast = if (attacker.types.contains(attacker.fastMove.type)) 1.2 else 1.0
            // Weighted 70% charged move effectiveness + 30% fast move effectiveness with STAB
            val combinedMult = (chargedMult * stabCharged * 0.70) + (fastMult * stabFast * 0.30)
            val weatherBonus = if (weather.boostedTypes.contains(charged.type) ||
                weather.boostedTypes.contains(attacker.fastMove.type)
            ) 1.2 else 1.0
            val score = combinedMult * weatherBonus
            if (score > bestEffectivePower) {
                bestEffectivePower = score
                bestMove = charged
                bestTypeMult = max(chargedMult, fastMult)
                isWeatherBoosted = weatherBonus > 1.0
            }
        }
        return Triple(bestTypeMult, bestMove, isWeatherBoosted)
    }

    /**
     * Generates ranked CounterRecommendations against an opposing PvP/Rocket team.
     */
    fun recommendCountersForTeam(
        opposingTeam: List<PokemonMetaEntry>,
        candidatePool: List<PokemonMetaEntry>,
        league: BattleLeague,
        weather: WeatherCondition,
        includeShadows: Boolean,
        includeMegas: Boolean,
        limit: Int = 12
    ): List<CounterRecommendation> {
        if (opposingTeam.isEmpty()) return emptyList()

        val filteredPool = candidatePool.filter { candidate ->
            (includeShadows || !candidate.isShadow) &&
                (includeMegas || !candidate.isMegaOrPrimal) &&
                (league == BattleLeague.MASTER_LEAGUE || league == BattleLeague.PVE_RAIDS || !candidate.isMegaOrPrimal)
        }

        return filteredPool.map { candidate ->
            val matchups = opposingTeam.map { opponent ->
                val (offMult, _, _) = evaluateBestOffensiveMove(candidate, opponent, weather)
                val oppFastMult = getMultiplierAgainstPokemon(opponent.fastMove.type, candidate)
                val oppChargedMult = getMultiplierAgainstPokemon(opponent.chargedMove1.type, candidate)
                val defMult = max(oppFastMult, oppChargedMult)

                val netRatio = offMult / defMult.coerceAtLeast(0.39)
                val isWin = netRatio >= 1.15 || (offMult >= 1.6 && defMult <= 1.0)
                val label = when {
                    offMult >= 2.5 && defMult <= 0.63 -> "HARD WALLS (${formatMult(offMult)})"
                    offMult >= 1.6 && defMult < 1.0 -> "DOMINANT WIN (${formatMult(offMult)})"
                    offMult >= 1.6 -> "SUPER EFFECTIVE (${formatMult(offMult)})"
                    defMult <= 0.625 -> "TANKY RESIST (${formatMult(defMult)} taken)"
                    isWin -> "FAVORABLE META EDGE"
                    else -> "NEUTRAL / RISKY"
                }
                SingleTargetMatchup(
                    opponent = opponent,
                    offensiveMultiplier = offMult,
                    defensiveMultiplier = defMult,
                    advantageLabel = label,
                    isWinningMatchup = isWin
                )
            }

            val avgOffMult = matchups.map { it.offensiveMultiplier }.average()
            val avgDefMult = matchups.map { it.defensiveMultiplier }.average()
            val winsCount = matchups.count { it.isWinningMatchup }
            val metaScore = candidate.leagueScores[league]
                ?: candidate.leagueScores[BattleLeague.MASTER_LEAGUE]
                ?: 82.0

            val (_, chosenCharged, weatherBoosted) = evaluateBestOffensiveMove(
                candidate,
                opposingTeam.first(),
                weather
            )

            // Composite Tactical Score (0-100 scale)
            val coverageBonus = (winsCount.toDouble() / opposingTeam.size.toDouble()) * 28.0
            val offenseBonus = (avgOffMult - 1.0) * 26.0
            val defenseBonus = (1.1 - avgDefMult) * 18.0
            val metaContribution = (metaScore * 0.48)
            val weatherAddition = if (weatherBoosted) 4.0 else 0.0

            val rawScore = (metaContribution + coverageBonus + offenseBonus + defenseBonus + weatherAddition)
                .coerceIn(40.0, 99.8)

            val bestMoveset = "${candidate.fastMove.formattedName} + ${chosenCharged.formattedName}"
            val winningNames = matchups.filter { it.isWinningMatchup }.joinToString(", ") { it.opponent.name }
            val summary = if (winsCount == opposingTeam.size && opposingTeam.size > 1) {
                "Corebreaker! Beats all ${opposingTeam.size} opposing Pokémon ($winningNames) with ${chosenCharged.type.displayName} pressure."
            } else if (winsCount > 0) {
                "Covers $winsCount/${opposingTeam.size} threats ($winningNames) while maintaining a ${metaScore.roundToInt()} ${league.shortName} meta rating."
            } else {
                "High-stat generalist option with ${bestMoveset}."
            }

            CounterRecommendation(
                counter = candidate,
                overallScore = (rawScore * 10.0).roundToInt() / 10.0,
                bestMovesetText = bestMoveset,
                damageMultiplierDealt = (avgOffMult * 100.0).roundToInt() / 100.0,
                damageMultiplierTaken = (avgDefMult * 100.0).roundToInt() / 100.0,
                isWeatherBoosted = weatherBoosted,
                estimatedDps = candidate.raidDps,
                estimatedTdo = candidate.raidTdo,
                timeToWinSeconds = 0,
                recommendedTrainers = 1.0,
                targetMatchups = matchups,
                tacticalSummary = summary
            )
        }.sortedByDescending { it.overallScore }
            .take(limit)
    }

    /**
     * Simulates Raid Boss counters with real-time Weather Boost, Shadow (+20% ATK),
     * Mega Evolution, Time-to-Win (TTW), and Trainer Estimator calculations.
     */
    fun simulateRaidCounters(
        boss: RaidBossEntry,
        candidatePool: List<PokemonMetaEntry>,
        weather: WeatherCondition,
        includeShadows: Boolean,
        includeMegas: Boolean,
        limit: Int = 14
    ): List<CounterRecommendation> {
        val bossPokemon = boss.pokemon
        val filtered = candidatePool.filter { candidate ->
            (includeShadows || !candidate.isShadow) &&
                (includeMegas || !candidate.isMegaOrPrimal)
        }

        return filtered.mapNotNull { candidate ->
            val (typeMult, bestCharged, weatherBoosted) = evaluateBestOffensiveMove(
                candidate,
                bossPokemon,
                weather
            )
            // In raids, only surface counters that deal at least Super Effective (1.6x) or extreme neutral DPS
            if (typeMult < 1.55) return@mapNotNull null

            val bossFastMult = getMultiplierAgainstPokemon(bossPokemon.fastMove.type, candidate)
            val bossChargedMult = getMultiplierAgainstPokemon(bossPokemon.chargedMove1.type, candidate)
            val damageTakenMult = (bossFastMult * 0.35 + bossChargedMult * 0.65)

            val fastTypeMult = getMultiplierAgainstPokemon(candidate.fastMove.type, bossPokemon)
            val chargedTypeMult = getMultiplierAgainstPokemon(bestCharged.type, bossPokemon)
            val stabMultiplier = (if (candidate.types.contains(bestCharged.type)) 1.12 else 0.92) *
                (if (candidate.types.contains(candidate.fastMove.type)) 1.05 else 0.95)
            val blendedTypeMult = (chargedTypeMult * 0.72 + fastTypeMult * 0.28)

            val weatherMultiplier = if (weatherBoosted) 1.20 else 1.0
            val effectiveDps = candidate.raidDps * (blendedTypeMult / 1.6) * stabMultiplier * weatherMultiplier
            val effectiveTdo = candidate.raidTdo * (blendedTypeMult / 1.6) * stabMultiplier * weatherMultiplier /
                damageTakenMult.coerceAtLeast(0.55)

            // Calculate realistic Time To Win (TTW) against boss HP including relobby overhead
            val rawTtw = (boss.bossHp / effectiveDps).roundToInt() + 14
            val ttwSeconds = rawTtw.coerceIn(95, 890)
            val trainersNeeded = ((ttwSeconds.toDouble() / (boss.raidTimerSeconds - 15).toDouble()) * 10.0)
                .roundToInt() / 10.0

            val raidScore = (effectiveDps * 1.45 + (effectiveTdo / 35.0) - (ttwSeconds * 0.08))
                .coerceIn(55.0, 99.9)

            val bestMoveset = "${candidate.fastMove.formattedName} + ${bestCharged.formattedName}"
            val matchup = SingleTargetMatchup(
                opponent = bossPokemon,
                offensiveMultiplier = typeMult,
                defensiveMultiplier = (damageTakenMult * 100.0).roundToInt() / 100.0,
                advantageLabel = if (typeMult >= 2.5) "2.56x DOUBLE WEAKNESS" else "1.6x SUPER EFFECTIVE",
                isWinningMatchup = true
            )

            val summary = buildString {
                append("${formatMult(typeMult)} ${bestCharged.type.displayName} damage")
                if (weatherBoosted) append(" • +20% ${weather.displayName} Boost")
                if (candidate.isShadow) append(" • +20% Shadow Bonus")
                if (candidate.isMegaOrPrimal) append(" • +10%/30% Ally Raid Aura")
            }

            CounterRecommendation(
                counter = candidate,
                overallScore = (raidScore * 10.0).roundToInt() / 10.0,
                bestMovesetText = bestMoveset,
                damageMultiplierDealt = typeMult,
                damageMultiplierTaken = (damageTakenMult * 100.0).roundToInt() / 100.0,
                isWeatherBoosted = weatherBoosted,
                estimatedDps = (effectiveDps * 10.0).roundToInt() / 10.0,
                estimatedTdo = effectiveTdo.roundToInt().toDouble(),
                timeToWinSeconds = ttwSeconds,
                recommendedTrainers = trainersNeeded.coerceAtLeast(0.5),
                targetMatchups = listOf(matchup),
                tacticalSummary = summary
            )
        }.sortedWith(
            compareByDescending<CounterRecommendation> { it.overallScore }
                .thenBy { it.timeToWinSeconds }
        ).take(limit)
    }

    fun formatMult(mult: Double): String {
        return when {
            mult >= 2.5 -> "2.56x"
            mult >= 1.55 -> "1.6x"
            mult <= 0.26 -> "0.24x"
            mult <= 0.42 -> "0.39x"
            mult <= 0.65 -> "0.63x"
            else -> "1.0x"
        }
    }
}
