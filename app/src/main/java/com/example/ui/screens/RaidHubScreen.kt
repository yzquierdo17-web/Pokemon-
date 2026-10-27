package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.engine.TypeEffectivenessEngine
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.RaidPulseUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RaidHubScreen(
    uiState: RaidPulseUiState,
    raidBosses: List<RaidBossEntry>,
    onSelectRaidBoss: (RaidBossEntry) -> Unit,
    onSelectWeather: (WeatherCondition) -> Unit,
    onToggleShadows: () -> Unit,
    onToggleMegas: () -> Unit,
    onSaveRaidSquad: () -> Unit,
    onInspectRecommendation: (CounterRecommendation) -> Unit,
    onInspectPokemon: (PokemonMetaEntry) -> Unit,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeBoss = uiState.selectedRaidBoss
    val bossPokemon = activeBoss.pokemon
    val bossWeaknessMap = TypeEffectivenessEngine.getDefensiveProfile(
        bossPokemon.primaryType,
        bossPokemon.secondaryType
    )
    val bossDoubleWeak = bossWeaknessMap[2.56].orEmpty()
    val bossSingleWeak = bossWeaknessMap[1.6].orEmpty()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("raid_hub_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TacticalHeroBanner(
                title = stringResource(R.string.raid_hub_title),
                subtitle = stringResource(R.string.raid_hub_subtitle),
                isDarkTheme = uiState.isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // Raid Boss Selector Carousel
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "SELECT ACTIVE RAID BOSS (TIER 5 / MEGA / PRIMAL / SHADOW)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(raidBosses, key = { it.id }) { boss ->
                        val isSelected = boss.id == activeBoss.id
                        Card(
                            modifier = Modifier
                                .width(156.dp)
                                .clickable { onSelectRaidBoss(boss) }
                                .testTag("raid_boss_chip_${boss.id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) {
                                    boss.pokemon.primaryType.color.copy(alpha = 0.22f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) boss.pokemon.primaryType.color else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                PokemonAvatarBadge(pokemon = boss.pokemon, size = 54.dp)
                                Text(
                                    text = boss.pokemon.fullDisplayName,
                                    style = MaterialTheme.typography.labelLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = boss.tierLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // Featured Raid Boss Telemetry & Catch CP Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("active_raid_boss_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.5.dp, bossPokemon.primaryType.color.copy(alpha = 0.65f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        PokemonAvatarBadge(
                            pokemon = bossPokemon,
                            size = 72.dp,
                            modifier = Modifier.clickable { onInspectPokemon(bossPokemon) }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                color = ElectricCrimson.copy(alpha = 0.18f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${activeBoss.tierLabel.uppercase()} • ${activeBoss.bossHp} HP",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElectricCrimson,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = bossPokemon.fullDisplayName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                bossPokemon.types.forEach { t ->
                                    PokemonTypeChip(type = t)
                                }
                            }
                        }
                        FilledTonalButton(
                            onClick = onSaveRaidSquad,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("save_raid_squad_btn")
                        ) {
                            Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save", style = MaterialTheme.typography.labelMedium)
                        }
                    }

                    // 100% IV Catch CP Telemetry Row
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CatchCpColumn(
                                label = stringResource(R.string.catch_cp_lv20),
                                value = "${activeBoss.catchCpLv20Max} CP",
                                sublabel = "Range: ${activeBoss.catchCpLv20Min}-${activeBoss.catchCpLv20Max}",
                                accent = CyberCyan
                            )
                            CatchCpColumn(
                                label = stringResource(R.string.catch_cp_lv25),
                                value = "${activeBoss.catchCpLv25Max} CP",
                                sublabel = "Range: ${activeBoss.catchCpLv25Min}-${activeBoss.catchCpLv25Max}",
                                accent = RaidGold
                            )
                            val bestTrainerEst = uiState.raidCounters.firstOrNull()?.recommendedTrainers ?: 2.5
                            CatchCpColumn(
                                label = stringResource(R.string.min_trainers_label),
                                value = "${bestTrainerEst}+",
                                sublabel = "${activeBoss.raidTimerSeconds}s Raid Clock",
                                accent = EmeraldBoost
                            )
                        }
                    }

                    // Boss Weaknesses to Exploit
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "EXPLOITABLE BOSS WEAKNESSES",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            bossDoubleWeak.forEach { t ->
                                PokemonTypeChip(type = t, multiplierText = "2.56x DOUBLE", compact = false)
                            }
                            bossSingleWeak.forEach { t ->
                                PokemonTypeChip(type = t, multiplierText = "1.6x SE", compact = false)
                            }
                        }
                    }

                    Text(
                        text = activeBoss.soloOrDuoNote,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Weather & Shadow/Mega Modifiers
        item {
            WeatherSelectorBar(
                activeWeather = uiState.activeWeather,
                includeShadows = uiState.includeShadows,
                includeMegas = uiState.includeMegas,
                onSelectWeather = onSelectWeather,
                onToggleShadows = onToggleShadows,
                onToggleMegas = onToggleMegas
            )
        }

        // Top Simulated Counters Header
        item {
            Column {
                Text(
                    text = "TOP SIMULATED RAID COUNTERS VS ${bossPokemon.name.uppercase()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sorted by Time-to-Win (TTW) & Effective Raid Score in ${uiState.activeWeather.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        itemsIndexed(
            items = uiState.raidCounters,
            key = { _, rec -> rec.counter.id }
        ) { idx, rec ->
            CounterRecommendationCard(
                rank = idx + 1,
                recommendation = rec,
                isRaidMode = true,
                onClick = { onInspectRecommendation(rec) }
            )
        }
    }
}

@Composable
private fun CatchCpColumn(
    label: String,
    value: String,
    sublabel: String,
    accent: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = accent,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = sublabel,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
