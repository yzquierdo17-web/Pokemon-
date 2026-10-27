package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.engine.TypeEffectivenessEngine
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.RaidPulseUiState

@Composable
fun CounterLabScreen(
    uiState: RaidPulseUiState,
    catalog: List<PokemonMetaEntry>,
    presets: List<OpposingTeamPreset>,
    onSelectLeague: (BattleLeague) -> Unit,
    onApplyPreset: (OpposingTeamPreset) -> Unit,
    onOpenSlotPicker: (Int) -> Unit,
    onCloseSlotPicker: () -> Unit,
    onSelectOpponentForSlot: (PokemonMetaEntry) -> Unit,
    onRemoveOpponentAt: (Int) -> Unit,
    onClearTeam: () -> Unit,
    onSaveCounterSquad: () -> Unit,
    onSelectWeather: (WeatherCondition) -> Unit,
    onToggleShadows: () -> Unit,
    onToggleMegas: () -> Unit,
    onInspectRecommendation: (CounterRecommendation) -> Unit,
    onInspectPokemon: (PokemonMetaEntry) -> Unit,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("counter_lab_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TacticalHeroBanner(
                title = stringResource(R.string.analyzer_title),
                subtitle = stringResource(R.string.analyzer_subtitle),
                isDarkTheme = uiState.isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // League Selector Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BattleLeague.entries.forEach { league ->
                    val selected = league == uiState.selectedLeague
                    FilterChip(
                        selected = selected,
                        onClick = { onSelectLeague(league) },
                        label = {
                            Text(
                                text = "${league.shortName} (${league.cpCapLabel})",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        modifier = Modifier.testTag("analyzer_league_${league.name.lowercase()}")
                    )
                }
            }
        }

        // Quick Opposing Team Meta Presets
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stringResource(R.string.preset_lineups_label).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presets.forEach { preset ->
                        AssistChip(
                            onClick = { onApplyPreset(preset) },
                            label = {
                                Text(
                                    text = preset.title,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.FlashOn,
                                    contentDescription = null,
                                    tint = CyberCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            modifier = Modifier.testTag("preset_chip_${preset.id}")
                        )
                    }
                }
            }
        }

        // 3-Slot Opposing Team Lineup Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = ElectricCrimson,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "OPPOSING TEAM (${uiState.opposingTeam.size}/3)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (uiState.opposingTeam.isNotEmpty()) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                TextButton(
                                    onClick = onClearTeam,
                                    modifier = Modifier.testTag("clear_opposing_team_btn")
                                ) {
                                    Text(stringResource(R.string.clear_team), style = MaterialTheme.typography.labelMedium)
                                }
                                FilledTonalButton(
                                    onClick = onSaveCounterSquad,
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.testTag("save_counter_squad_btn")
                                ) {
                                    Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Save Trio", style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (slotIndex in 0..2) {
                            val member = uiState.opposingTeam.getOrNull(slotIndex)
                            OpposingSlotCard(
                                slotIndex = slotIndex,
                                pokemon = member,
                                onSlotClick = { onOpenSlotPicker(slotIndex) },
                                onRemoveClick = { onRemoveOpponentAt(slotIndex) },
                                onInspectClick = { member?.let(onInspectPokemon) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
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

        // Team Vulnerability Breakdown Radar
        if (uiState.teamWeaknesses.isNotEmpty()) {
            item {
                TeamWeaknessRadarCard(
                    weaknesses = uiState.teamWeaknesses.take(6),
                    teamSize = uiState.opposingTeam.size
                )
            }
        }

        // Recommended Counter Pokémon Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.recommended_counters_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ranked by Type Exploit × ${uiState.selectedLeague.shortName} Meta Rating × Move Efficiency",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        if (uiState.teamCounters.isEmpty()) {
            item {
                EmptyTeamPromptCard(onPickFirstSlot = { onOpenSlotPicker(0) })
            }
        } else {
            itemsIndexed(
                items = uiState.teamCounters,
                key = { _, rec -> rec.counter.id }
            ) { index, rec ->
                CounterRecommendationCard(
                    rank = index + 1,
                    recommendation = rec,
                    isRaidMode = false,
                    onClick = { onInspectRecommendation(rec) }
                )
            }
        }
    }

    if (uiState.activeSlotIndexToPick != null) {
        OpponentPickerDialog(
            slotIndex = uiState.activeSlotIndexToPick,
            catalog = catalog,
            onDismiss = onCloseSlotPicker,
            onSelectPokemon = onSelectOpponentForSlot
        )
    }
}

@Composable
private fun OpposingSlotCard(
    slotIndex: Int,
    pokemon: PokemonMetaEntry?,
    onSlotClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onInspectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(144.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onSlotClick)
            .testTag("opponent_slot_$slotIndex"),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = pokemon?.primaryType?.color?.copy(alpha = 0.65f)
                ?: MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
    ) {
        if (pokemon == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = CyberCyan.copy(alpha = 0.14f),
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Opponent Slot ${slotIndex + 1}",
                            tint = CyberCyan
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Slot #${slotIndex + 1}\nTap to Pick",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = onRemoveClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(28.dp)
                        .testTag("remove_opponent_$slotIndex")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove ${pokemon.name}",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = onInspectClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Inspect ${pokemon.name}",
                        modifier = Modifier.size(15.dp),
                        tint = CyberCyan
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 6.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PokemonAvatarBadge(pokemon = pokemon, size = 56.dp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        pokemon.types.forEach { t ->
                            PokemonTypeChip(type = t, compact = true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TeamWeaknessRadarCard(
    weaknesses: List<TeamWeaknessSummary>,
    teamSize: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("team_weakness_radar_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Radar,
                    contentDescription = null,
                    tint = CyberCyan,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.team_weakness_radar),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            weaknesses.forEach { summary ->
                val hitCount = summary.weakTeamMembers.size
                val barColor = when {
                    hitCount >= 2 || summary.maxMultiplier >= 2.5 -> EmeraldBoost
                    hitCount == 1 -> CyberCyan
                    else -> RaidGold
                }
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PokemonTypeChip(
                                type = summary.type,
                                multiplierText = "Max ${TypeEffectivenessEngine.formatMult(summary.maxMultiplier)}",
                                compact = true
                            )
                            Text(
                                text = if (hitCount > 0) {
                                    "Hits $hitCount/$teamSize SE (${summary.weakTeamMembers.joinToString { it.name }})"
                                } else {
                                    "Neutral coverage (${summary.averageMultiplier}x avg)"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    LinearProgressIndicator(
                        progress = { summary.threatSeverity },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = barColor,
                        trackColor = barColor.copy(alpha = 0.15f)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyTeamPromptCard(onPickFirstSlot: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "Select Opposing Pokémon to Begin Analysis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tap any slot above or choose a Quick Meta Preset to calculate shared type weaknesses and top GBL/Raid counters.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onPickFirstSlot) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add First Opponent")
            }
        }
    }
}

@Composable
private fun OpponentPickerDialog(
    slotIndex: Int,
    catalog: List<PokemonMetaEntry>,
    onDismiss: () -> Unit,
    onSelectPokemon: (PokemonMetaEntry) -> Unit
) {
    BackHandler(onBack = onDismiss)
    var filterText by remember { mutableStateOf("") }

    val filtered = remember(catalog, filterText) {
        if (filterText.isBlank()) catalog
        else catalog.filter {
            it.fullDisplayName.contains(filterText, ignoreCase = true) ||
                it.primaryType.displayName.contains(filterText, ignoreCase = true) ||
                it.secondaryType?.displayName?.contains(filterText, ignoreCase = true) == true
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.82f)
                .testTag("opponent_picker_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Opponent for Slot #${slotIndex + 1}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close Picker")
                    }
                }

                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    placeholder = { Text("Search Pokémon or type…") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("picker_search_input")
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filtered, key = { it.id }) { pokemon ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { onSelectPokemon(pokemon) }
                                .testTag("picker_item_${pokemon.id}"),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PokemonAvatarBadge(pokemon = pokemon, size = 48.dp)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = pokemon.fullDisplayName,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        pokemon.types.forEach { t ->
                                            PokemonTypeChip(type = t, compact = true)
                                        }
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.AddCircleOutline,
                                    contentDescription = "Select ${pokemon.name}",
                                    tint = CyberCyan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
