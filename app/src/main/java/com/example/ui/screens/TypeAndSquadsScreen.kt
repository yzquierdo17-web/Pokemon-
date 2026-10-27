package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.SavedSquadEntity
import com.example.engine.TypeEffectivenessEngine
import com.example.model.PokemonType
import com.example.ui.components.PokemonTypeChip
import com.example.ui.components.TacticalHeroBanner
import com.example.ui.theme.*
import com.example.viewmodel.RaidPulseUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeAndSquadsScreen(
    uiState: RaidPulseUiState,
    savedSquads: List<SavedSquadEntity>,
    onSelectPrimaryType: (PokemonType) -> Unit,
    onSelectSecondaryType: (PokemonType?) -> Unit,
    onDeleteSquad: (Int) -> Unit,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val defensiveProfile = TypeEffectivenessEngine.getDefensiveProfile(
        uiState.matrixPrimaryType,
        uiState.matrixSecondaryType
    )
    val doubleWeak = defensiveProfile[2.56].orEmpty()
    val singleWeak = defensiveProfile[1.6].orEmpty()
    val singleResist = defensiveProfile[0.625].orEmpty()
    val doubleResist = (defensiveProfile[0.391].orEmpty() + defensiveProfile[0.244].orEmpty())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("type_and_squads_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TacticalHeroBanner(
                title = stringResource(R.string.type_chart_title),
                subtitle = stringResource(R.string.type_chart_subtitle),
                isDarkTheme = uiState.isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // Interactive Dual-Type Effectiveness Calculator Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dual_type_calculator_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, uiState.matrixPrimaryType.color.copy(alpha = 0.65f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = null,
                            tint = CyberCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "DUAL-TYPE DEFENSIVE CALCULATOR",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "1. Primary Defending Type:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PokemonType.entries.forEach { t ->
                            FilterChip(
                                selected = uiState.matrixPrimaryType == t,
                                onClick = { onSelectPrimaryType(t) },
                                label = { Text(t.displayName, style = MaterialTheme.typography.labelSmall) },
                                modifier = Modifier.testTag("matrix_primary_${t.name.lowercase()}")
                            )
                        }
                    }

                    Text(
                        text = "2. Secondary Defending Type (Optional):",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = uiState.matrixSecondaryType == null,
                            onClick = { onSelectSecondaryType(null) },
                            label = { Text("None (Mono-Type)", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.testTag("matrix_secondary_none")
                        )
                        PokemonType.entries
                            .filter { it != uiState.matrixPrimaryType }
                            .forEach { t ->
                                FilterChip(
                                    selected = uiState.matrixSecondaryType == t,
                                    onClick = { onSelectSecondaryType(t) },
                                    label = { Text(t.displayName, style = MaterialTheme.typography.labelSmall) },
                                    modifier = Modifier.testTag("matrix_secondary_${t.name.lowercase()}")
                                )
                            }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    // Combined Typing Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Active Typing:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        PokemonTypeChip(type = uiState.matrixPrimaryType)
                        uiState.matrixSecondaryType?.let { PokemonTypeChip(type = it) }
                    }

                    if (doubleWeak.isNotEmpty()) {
                        MultiplierSection(
                            label = "2.56x DOUBLE SUPER EFFECTIVE WEAKNESS",
                            types = doubleWeak,
                            multBadge = "2.56x",
                            accent = ElectricCrimson
                        )
                    }
                    if (singleWeak.isNotEmpty()) {
                        MultiplierSection(
                            label = "1.6x SUPER EFFECTIVE WEAKNESS",
                            types = singleWeak,
                            multBadge = "1.6x",
                            accent = RaidGold
                        )
                    }
                    if (singleResist.isNotEmpty()) {
                        MultiplierSection(
                            label = "0.625x RESISTANCES (TAKES REDUCED DAMAGE)",
                            types = singleResist,
                            multBadge = "0.63x",
                            accent = CyberCyan
                        )
                    }
                    if (doubleResist.isNotEmpty()) {
                        MultiplierSection(
                            label = "0.391x / 0.244x DOUBLE & TRIPLE RESISTANCES",
                            types = doubleResist,
                            multBadge = "0.39x",
                            accent = EmeraldBoost
                        )
                    }
                }
            }
        }

        // Saved Battle & Raid Squads Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmarks,
                    contentDescription = null,
                    tint = RaidGold
                )
                Text(
                    text = stringResource(R.string.saved_squads_header),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (savedSquads.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = stringResource(R.string.empty_saved_squads),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        } else {
            items(savedSquads, key = { it.id }) { squad ->
                SavedSquadCard(
                    squad = squad,
                    onDelete = { onDeleteSquad(squad.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MultiplierSection(
    label: String,
    types: List<PokemonType>,
    multBadge: String,
    accent: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = accent
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            types.forEach { t ->
                PokemonTypeChip(type = t, multiplierText = multBadge)
            }
        }
    }
}

@Composable
private fun SavedSquadCard(
    squad: SavedSquadEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("saved_squad_card_${squad.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = squad.modeLabel.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan
                    )
                    Text(
                        text = squad.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    color = EmeraldBoost.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "SCORE ${squad.avgTacticalScore}",
                        style = MaterialTheme.typography.labelMedium,
                        color = EmeraldBoost,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_squad_${squad.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete Saved Squad",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = "Targets: ${squad.targetNamesCsv}",
                style = MaterialTheme.typography.labelMedium,
                color = RaidGold
            )
            Text(
                text = squad.notes,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
