package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.RaidPulseUiState

@Composable
fun MetaTiersScreen(
    uiState: RaidPulseUiState,
    catalog: List<PokemonMetaEntry>,
    onSelectLeague: (BattleLeague) -> Unit,
    onSelectRole: (MetaRole?) -> Unit,
    onSelectType: (PokemonType?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onFetchFromPokeApi: () -> Unit,
    onInspectPokemon: (PokemonMetaEntry) -> Unit,
    onAddOpponent: (PokemonMetaEntry) -> Unit,
    onFindCountersAgainst: (PokemonMetaEntry) -> Unit,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeLeague = uiState.metaLeagueFilter

    val rankedList = remember(
        catalog,
        activeLeague,
        uiState.metaRoleFilter,
        uiState.metaTypeFilter,
        uiState.searchQuery
    ) {
        catalog
            .filter { entry ->
                val matchesLeague = entry.leagueScores.containsKey(activeLeague) ||
                    entry.id.startsWith("pokeapi_")
                val matchesRole = uiState.metaRoleFilter == null || entry.role == uiState.metaRoleFilter
                val matchesType = uiState.metaTypeFilter == null || entry.types.contains(uiState.metaTypeFilter)
                val q = uiState.searchQuery.trim()
                val matchesQuery = q.isEmpty() ||
                    entry.fullDisplayName.contains(q, ignoreCase = true) ||
                    entry.fastMove.name.contains(q, ignoreCase = true) ||
                    entry.chargedMove1.name.contains(q, ignoreCase = true) ||
                    entry.types.any { it.displayName.contains(q, ignoreCase = true) }

                matchesLeague && matchesRole && matchesType && matchesQuery
            }
            .sortedByDescending { it.leagueScores[activeLeague] ?: 78.0 }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("meta_tiers_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TacticalHeroBanner(
                title = stringResource(R.string.meta_rankings_title),
                subtitle = stringResource(R.string.meta_rankings_subtitle),
                isDarkTheme = uiState.isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // Search & Live PokéAPI Import Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.search_pokemon_hint),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear search")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("meta_search_input")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Not in local list? Import any Pokémon live from PokéAPI:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = onFetchFromPokeApi,
                            enabled = !uiState.isFetchingPokeApi && uiState.searchQuery.isNotBlank(),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("fetch_pokeapi_btn")
                        ) {
                            if (uiState.isFetchingPokeApi) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(R.string.fetch_pokeapi_button),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }

        // League Selector Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BattleLeague.entries.forEach { league ->
                    FilterChip(
                        selected = league == activeLeague,
                        onClick = { onSelectLeague(league) },
                        label = {
                            Text(
                                text = "${league.displayName} (${league.cpCapLabel})",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        modifier = Modifier.testTag("meta_league_chip_${league.name.lowercase()}")
                    )
                }
            }
        }

        // Role Filter Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.metaRoleFilter == null,
                    onClick = { onSelectRole(null) },
                    label = { Text("All Roles", style = MaterialTheme.typography.labelSmall) }
                )
                MetaRole.entries.forEach { role ->
                    FilterChip(
                        selected = uiState.metaRoleFilter == role,
                        onClick = { onSelectRole(role) },
                        label = { Text(role.badgeText, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        // Type Filter Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = uiState.metaTypeFilter == null,
                    onClick = { onSelectType(null) },
                    label = { Text("All 18 Types", style = MaterialTheme.typography.labelSmall) }
                )
                PokemonType.entries.forEach { type ->
                    FilterChip(
                        selected = uiState.metaTypeFilter == type,
                        onClick = { onSelectType(type) },
                        label = { Text(type.displayName, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        itemsIndexed(
            items = rankedList,
            key = { _, p -> p.id }
        ) { index, pokemon ->
            MetaRankingCard(
                rank = index + 1,
                pokemon = pokemon,
                league = activeLeague,
                onClick = { onInspectPokemon(pokemon) },
                onAddOpponent = { onAddOpponent(pokemon) },
                onFindCounters = { onFindCountersAgainst(pokemon) }
            )
        }
    }
}

@Composable
private fun MetaRankingCard(
    rank: Int,
    pokemon: PokemonMetaEntry,
    league: BattleLeague,
    onClick: () -> Unit,
    onAddOpponent: () -> Unit,
    onFindCounters: () -> Unit
) {
    val score = pokemon.leagueScores[league] ?: 82.0
    val tier = pokemon.metaTierFor(league)
    val tierColor = when (tier) {
        "S+" -> EmeraldBoost
        "S" -> CyberCyan
        "A+" -> RaidGold
        else -> MaterialTheme.colorScheme.primary
    }
    val ivText = pokemon.optimalIvSpread[league] ?: "15/15/15 (Rank 1)"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("meta_card_${pokemon.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PokemonAvatarBadge(
                    pokemon = pokemon,
                    size = 60.dp,
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
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = pokemon.role.badgeText,
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        pokemon.types.forEach { t ->
                            PokemonTypeChip(type = t, compact = true)
                        }
                        Text(
                            text = "IV: $ivText",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val movesText = buildString {
                        append(pokemon.fastMove.formattedName)
                        append(" + ")
                        append(pokemon.chargedMove1.formattedName)
                        pokemon.chargedMove2?.let {
                            append(" / ")
                            append(it.formattedName)
                        }
                    }
                    Text(
                        text = movesText,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Tier & Score Badge
                Surface(
                    color = tierColor.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, tierColor.copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tier,
                            style = MaterialTheme.typography.titleMedium,
                            color = tierColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (pokemon.keyWins.isNotEmpty()) {
                        "Beats: ${pokemon.keyWins.take(3).joinToString(", ")}"
                    } else {
                        "Tap for full 18-type matrix"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    TextButton(
                        onClick = onAddOpponent,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.testTag("meta_add_opponent_${pokemon.id}")
                    ) {
                        Text("+ Opponent", style = MaterialTheme.typography.labelSmall)
                    }
                    FilledTonalButton(
                        onClick = onFindCounters,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.testTag("meta_counter_${pokemon.id}")
                    ) {
                        Text("Counters", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
