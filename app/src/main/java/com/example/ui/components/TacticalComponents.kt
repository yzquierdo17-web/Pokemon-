package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.R
import com.example.engine.TypeEffectivenessEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun TacticalHeroBanner(
    title: String,
    subtitle: String,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("tactical_hero_banner"),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_raid_arena_banner),
                contentDescription = "Tactical Battle Arena Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xEB090E1A),
                                Color(0xCC0F172A),
                                Color(0x88151E3F)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = CyberCyan.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "POGO TACTICAL",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Text(
                            text = "2.56x / 1.6x ENGINE",
                            style = MaterialTheme.typography.labelSmall,
                            color = RaidGold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                        .testTag("theme_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Dark or Light Stadium Theme",
                        tint = RaidGold
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonAvatarBadge(
    pokemon: PokemonMetaEntry,
    size: Dp = 64.dp,
    rankNumber: Int? = null,
    modifier: Modifier = Modifier
) {
    val primaryColor = pokemon.primaryType.color
    val secondaryColor = pokemon.secondaryType?.color ?: primaryColor

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.42f),
                        secondaryColor.copy(alpha = 0.18f),
                        Color.Transparent
                    )
                )
            )
            drawCircle(
                color = primaryColor.copy(alpha = 0.6f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
            )
        }

        // Fallback monogram behind AsyncImage in case offline/loading
        Text(
            text = pokemon.name.take(2).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = primaryColor.copy(alpha = 0.55f),
            fontWeight = FontWeight.Bold
        )

        AsyncImage(
            model = pokemon.spriteUrl,
            contentDescription = pokemon.fullDisplayName,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentScale = ContentScale.Fit
        )

        if (rankNumber != null) {
            Surface(
                color = if (rankNumber == 1) RaidGold else MaterialTheme.colorScheme.surface,
                shape = CircleShape,
                border = BorderStroke(1.dp, primaryColor),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(22.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "#$rankNumber",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (rankNumber == 1) Color(0xFF1A1600) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (pokemon.isShadow || pokemon.isMegaOrPrimal) {
            val tagColor = if (pokemon.isShadow) ShadowPurple else RaidGold
            val tagText = if (pokemon.isShadow) "SHDW" else "MEGA"
            Surface(
                color = Color(0xDD090E1A),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, tagColor),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = tagText,
                    style = MaterialTheme.typography.labelSmall,
                    color = tagColor,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonTypeChip(
    type: PokemonType,
    multiplierText: String? = null,
    compact: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        color = type.color.copy(alpha = 0.22f),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, type.color.copy(alpha = 0.75f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = if (compact) 6.dp else 9.dp,
                vertical = if (compact) 2.dp else 4.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (compact) 6.dp else 8.dp)
                    .clip(CircleShape)
                    .background(type.color)
            )
            Text(
                text = if (compact) type.shortCode else type.displayName.uppercase(),
                style = if (compact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (multiplierText != null) {
                Text(
                    text = multiplierText,
                    style = MaterialTheme.typography.labelSmall,
                    color = type.color,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WeatherSelectorBar(
    activeWeather: WeatherCondition,
    includeShadows: Boolean,
    includeMegas: Boolean,
    onSelectWeather: (WeatherCondition) -> Unit,
    onToggleShadows: () -> Unit,
    onToggleMegas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = includeShadows,
                onClick = onToggleShadows,
                label = { Text("Shadow (+20% ATK)", style = MaterialTheme.typography.labelMedium) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Whatshot,
                        contentDescription = null,
                        tint = if (includeShadows) ShadowPurple else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier.testTag("toggle_shadows_chip")
            )
            FilterChip(
                selected = includeMegas,
                onClick = onToggleMegas,
                label = { Text("Mega / Primal", style = MaterialTheme.typography.labelMedium) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = if (includeMegas) RaidGold else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier.testTag("toggle_megas_chip")
            )
            WeatherCondition.entries.forEach { weather ->
                val isSelected = weather == activeWeather
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectWeather(weather) },
                    label = {
                        Text(
                            text = weather.displayName,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (weather) {
                                WeatherCondition.EXTREME -> Icons.Default.Public
                                WeatherCondition.SUNNY -> Icons.Default.WbSunny
                                WeatherCondition.RAINY -> Icons.Default.WaterDrop
                                WeatherCondition.PARTLY_CLOUDY -> Icons.Default.CloudQueue
                                WeatherCondition.CLOUDY -> Icons.Default.Cloud
                                WeatherCondition.WINDY -> Icons.Default.Air
                                WeatherCondition.SNOW -> Icons.Default.AcUnit
                                WeatherCondition.FOG -> Icons.Default.BlurOn
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    modifier = Modifier.testTag("weather_chip_${weather.name.lowercase()}")
                )
            }
        }
    }
}

@Composable
fun CounterRecommendationCard(
    rank: Int,
    recommendation: CounterRecommendation,
    isRaidMode: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pokemon = recommendation.counter
    val scoreColor = when {
        recommendation.overallScore >= 94.0 -> EmeraldBoost
        recommendation.overallScore >= 87.0 -> CyberCyan
        else -> RaidGold
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("counter_card_${pokemon.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (rank == 1) 1.5.dp else 1.dp,
            color = if (rank == 1) scoreColor.copy(alpha = 0.7f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PokemonAvatarBadge(
                    pokemon = pokemon,
                    size = 62.dp,
                    rankNumber = rank
                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = pokemon.fullDisplayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (recommendation.isWeatherBoosted) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Weather Boosted Move",
                                tint = RaidGold,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        pokemon.types.forEach { t ->
                            PokemonTypeChip(type = t, compact = true)
                        }
                        Surface(
                            color = EmeraldBoost.copy(alpha = 0.16f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${TypeEffectivenessEngine.formatMult(recommendation.damageMultiplierDealt)} DMG",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldBoost,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = CyberCyan,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = recommendation.bestMovesetText,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Tactical Score Pill
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Surface(
                        color = scoreColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, scoreColor.copy(alpha = 0.65f))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${recommendation.overallScore}",
                                style = MaterialTheme.typography.titleMedium,
                                color = scoreColor,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isRaidMode) "RAID" else "SCORE",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
            Spacer(modifier = Modifier.height(8.dp))

            if (isRaidMode) {
                // Raid Metrics Bar: TTW, DPS, TDO, Recommended Trainers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RaidMetricItem("TTW (WIN)", "${recommendation.timeToWinSeconds}s", CyberCyan)
                    RaidMetricItem("EFF. DPS", "${recommendation.estimatedDps}", EmeraldBoost)
                    RaidMetricItem("TOTAL TDO", "${recommendation.estimatedTdo.toInt()}", RaidGold)
                    RaidMetricItem("TRAINERS", "${recommendation.recommendedTrainers}+", MaterialTheme.colorScheme.onSurface)
                }
            } else {
                // PvP Opposing Team Matchup Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    recommendation.targetMatchups.forEach { matchup ->
                        val chipColor = if (matchup.isWinningMatchup) EmeraldBoost else ElectricCrimson
                        Surface(
                            color = chipColor.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, chipColor.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (matchup.isWinningMatchup) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = chipColor,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "vs ${matchup.opponent.name}: ${TypeEffectivenessEngine.formatMult(matchup.offensiveMultiplier)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = recommendation.tacticalSummary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RaidMetricItem(label: String, value: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = valueColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PokemonInspectorModal(
    pokemon: PokemonMetaEntry,
    recommendation: CounterRecommendation?,
    onDismiss: () -> Unit,
    onAddToOpposingTeam: (PokemonMetaEntry) -> Unit,
    onAnalyzeCountersAgainst: (PokemonMetaEntry) -> Unit
) {
    BackHandler(onBack = onDismiss)

    val defensiveProfile = TypeEffectivenessEngine.getDefensiveProfile(
        pokemon.primaryType,
        pokemon.secondaryType
    )
    val doubleWeak = defensiveProfile[2.56].orEmpty()
    val singleWeak = defensiveProfile[1.6].orEmpty()
    val singleResist = defensiveProfile[0.625].orEmpty()
    val doubleResist = (defensiveProfile[0.391].orEmpty() + defensiveProfile[0.244].orEmpty())

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .heightIn(max = 680.dp)
                .testTag("pokemon_inspector_modal"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, pokemon.primaryType.color.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    PokemonAvatarBadge(pokemon = pokemon, size = 76.dp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DEX #${pokemon.dexNumber} • ${pokemon.role.displayName.uppercase()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = pokemon.fullDisplayName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            pokemon.types.forEach { t ->
                                PokemonTypeChip(type = t)
                            }
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close Inspector")
                    }
                }

                // Base Stats Bars (ATK / DEF / STA)
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "POKÉMON GO BASE STATS & RAID TELEMETRY",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        StatBarRow("ATK", pokemon.baseAtk, 380, ElectricCrimson)
                        StatBarRow("DEF", pokemon.baseDef, 320, CyberCyan)
                        StatBarRow("STA", pokemon.baseSta, 400, EmeraldBoost)
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Base Raid DPS: ${pokemon.raidDps}",
                                style = MaterialTheme.typography.labelMedium,
                                color = RaidGold
                            )
                            Text(
                                text = "Base TDO: ${pokemon.raidTdo.toInt()}",
                                style = MaterialTheme.typography.labelMedium,
                                color = CyberCyan
                            )
                        }
                    }
                }

                // Optimal Moveset
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "OPTIMAL META MOVESET (* = ELITE TM)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        MoveDetailRow("FAST", pokemon.fastMove)
                        MoveDetailRow("CHARGED 1", pokemon.chargedMove1)
                        pokemon.chargedMove2?.let { MoveDetailRow("CHARGED 2", it) }
                    }
                }

                // 18-Type Vulnerability Breakdown
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "DEFENSIVE WEAKNESSES & RESISTANCES",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (doubleWeak.isNotEmpty()) {
                        WeaknessGroupRow("2.56x Double Weak:", doubleWeak, "2.56x")
                    }
                    if (singleWeak.isNotEmpty()) {
                        WeaknessGroupRow("1.6x Super Effective:", singleWeak, "1.6x")
                    }
                    if (singleResist.isNotEmpty()) {
                        WeaknessGroupRow("0.625x Resists:", singleResist, "0.63x")
                    }
                    if (doubleResist.isNotEmpty()) {
                        WeaknessGroupRow("0.391x Double Resists:", doubleResist, "0.39x")
                    }
                }

                if (recommendation != null && recommendation.targetMatchups.isNotEmpty()) {
                    HorizontalDivider()
                    Text(
                        text = "ACTIVE MATCHUP SIMULATION",
                        style = MaterialTheme.typography.labelMedium,
                        color = RaidGold
                    )
                    Text(
                        text = recommendation.tacticalSummary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onAddToOpposingTeam(pokemon) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("modal_add_opponent_btn")
                    ) {
                        Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Opponent", style = MaterialTheme.typography.labelMedium)
                    }
                    Button(
                        onClick = { onAnalyzeCountersAgainst(pokemon) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("modal_counter_this_btn")
                    ) {
                        Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Find Counters", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBarRow(label: String, value: Int, maxVal: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.width(34.dp)
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.labelMedium,
            color = color,
            modifier = Modifier.width(34.dp)
        )
        LinearProgressIndicator(
            progress = { (value.toFloat() / maxVal.toFloat()).coerceIn(0.08f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.16f)
        )
    }
}

@Composable
private fun MoveDetailRow(slotLabel: String, move: MoveInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = slotLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(68.dp)
            )
            PokemonTypeChip(type = move.type, compact = true)
            Text(
                text = move.formattedName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = "PWR ${move.power} • ENG ${move.energy}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WeaknessGroupRow(
    title: String,
    types: List<PokemonType>,
    multText: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            types.forEach { t ->
                PokemonTypeChip(type = t, multiplierText = multText, compact = true)
            }
        }
    }
}
