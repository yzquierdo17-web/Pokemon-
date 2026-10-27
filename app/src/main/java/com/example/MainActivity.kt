package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.RaidPulseRepository
import com.example.data.local.RaidPulseDatabase
import com.example.ui.components.PokemonInspectorModal
import com.example.ui.screens.CounterLabScreen
import com.example.ui.screens.MetaTiersScreen
import com.example.ui.screens.RaidHubScreen
import com.example.ui.screens.TypeAndSquadsScreen
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppTab
import com.example.viewmodel.RaidPulseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaidPulseApp()
        }
    }
}

private data class NavDestination(
    val tab: AppTab,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun RaidPulseApp() {
    val context = LocalContext.current
    val repository = remember(context) {
        val db = RaidPulseDatabase.getInstance(context)
        RaidPulseRepository(db.savedSquadDao())
    }
    val viewModel: RaidPulseViewModel = viewModel(
        factory = RaidPulseViewModel.provideFactory(repository)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val catalog by viewModel.pokemonCatalog.collectAsStateWithLifecycle()
    val savedSquads by viewModel.savedSquads.collectAsStateWithLifecycle()

    val destinations = remember {
        listOf(
            NavDestination(
                tab = AppTab.ANALYZER,
                labelRes = R.string.nav_analyzer,
                selectedIcon = Icons.Filled.Radar,
                unselectedIcon = Icons.Outlined.Radar,
                testTag = "nav_tab_analyzer"
            ),
            NavDestination(
                tab = AppTab.RAID_HUB,
                labelRes = R.string.nav_raids,
                selectedIcon = Icons.Filled.LocalFireDepartment,
                unselectedIcon = Icons.Outlined.LocalFireDepartment,
                testTag = "nav_tab_raids"
            ),
            NavDestination(
                tab = AppTab.META_TIERS,
                labelRes = R.string.nav_meta,
                selectedIcon = Icons.Filled.EmojiEvents,
                unselectedIcon = Icons.Outlined.EmojiEvents,
                testTag = "nav_tab_meta"
            ),
            NavDestination(
                tab = AppTab.TYPE_AND_SQUADS,
                labelRes = R.string.nav_dex,
                selectedIcon = Icons.Filled.Shield,
                unselectedIcon = Icons.Outlined.Shield,
                testTag = "nav_tab_dex"
            )
        )
    }

    if (uiState.currentTab != AppTab.ANALYZER) {
        BackHandler {
            viewModel.selectTab(AppTab.ANALYZER)
        }
    }

    MyApplicationTheme(darkTheme = uiState.isDarkTheme) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isWideScreen = maxWidth >= 680.dp

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets.safeDrawing,
                bottomBar = {
                    if (!isWideScreen) {
                        NavigationBar(
                            modifier = Modifier.testTag("bottom_navigation_bar")
                        ) {
                            destinations.forEach { dest ->
                                val selected = uiState.currentTab == dest.tab
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { viewModel.selectTab(dest.tab) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                            contentDescription = stringResource(dest.labelRes)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(dest.labelRes),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    },
                                    modifier = Modifier.testTag(dest.testTag)
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (isWideScreen) {
                        NavigationRail(
                            modifier = Modifier.testTag("side_navigation_rail")
                        ) {
                            Spacer(modifier = Modifier.height(12.dp))
                            destinations.forEach { dest ->
                                val selected = uiState.currentTab == dest.tab
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = { viewModel.selectTab(dest.tab) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                            contentDescription = stringResource(dest.labelRes)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(dest.labelRes),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    },
                                    modifier = Modifier.testTag(dest.testTag)
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .widthIn(max = 860.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            AnimatedVisibility(
                                visible = uiState.statusBannerMessage != null,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                uiState.statusBannerMessage?.let { msg ->
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp)
                                            .testTag("status_notification_banner")
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = CyberCyan,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Text(
                                                    text = msg,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }
                                            IconButton(
                                                onClick = { viewModel.dismissBanner() },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Dismiss notification",
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            when (uiState.currentTab) {
                                AppTab.ANALYZER -> CounterLabScreen(
                                    uiState = uiState,
                                    catalog = catalog,
                                    presets = viewModel.opposingPresets,
                                    onSelectLeague = viewModel::selectAnalyzerLeague,
                                    onApplyPreset = viewModel::applyOpposingPreset,
                                    onOpenSlotPicker = viewModel::openOpponentPicker,
                                    onCloseSlotPicker = viewModel::closeOpponentPicker,
                                    onSelectOpponentForSlot = { viewModel.addOrReplaceOpponent(it) },
                                    onRemoveOpponentAt = viewModel::removeOpponentAt,
                                    onClearTeam = viewModel::clearOpposingTeam,
                                    onSaveCounterSquad = viewModel::saveCurrentTeamCounterSquad,
                                    onSelectWeather = viewModel::setWeatherCondition,
                                    onToggleShadows = viewModel::toggleIncludeShadows,
                                    onToggleMegas = viewModel::toggleIncludeMegas,
                                    onInspectRecommendation = viewModel::inspectCounterRecommendation,
                                    onInspectPokemon = viewModel::inspectPokemon,
                                    onToggleTheme = viewModel::toggleTheme,
                                    modifier = Modifier.weight(1f)
                                )
                                AppTab.RAID_HUB -> RaidHubScreen(
                                    uiState = uiState,
                                    raidBosses = viewModel.raidBosses,
                                    onSelectRaidBoss = viewModel::selectRaidBoss,
                                    onSelectWeather = viewModel::setWeatherCondition,
                                    onToggleShadows = viewModel::toggleIncludeShadows,
                                    onToggleMegas = viewModel::toggleIncludeMegas,
                                    onSaveRaidSquad = viewModel::saveCurrentRaidCounterSquad,
                                    onInspectRecommendation = viewModel::inspectCounterRecommendation,
                                    onInspectPokemon = viewModel::inspectPokemon,
                                    onToggleTheme = viewModel::toggleTheme,
                                    modifier = Modifier.weight(1f)
                                )
                                AppTab.META_TIERS -> MetaTiersScreen(
                                    uiState = uiState,
                                    catalog = catalog,
                                    onSelectLeague = viewModel::setMetaLeagueFilter,
                                    onSelectRole = viewModel::setMetaRoleFilter,
                                    onSelectType = viewModel::setMetaTypeFilter,
                                    onSearchQueryChange = viewModel::updateSearchQuery,
                                    onFetchFromPokeApi = { viewModel.fetchPokemonFromPokeApi() },
                                    onInspectPokemon = viewModel::inspectPokemon,
                                    onAddOpponent = { pokemon ->
                                        viewModel.addOrReplaceOpponent(pokemon)
                                        viewModel.selectTab(AppTab.ANALYZER)
                                    },
                                    onFindCountersAgainst = viewModel::analyzeSinglePokemonAsOpponent,
                                    onToggleTheme = viewModel::toggleTheme,
                                    modifier = Modifier.weight(1f)
                                )
                                AppTab.TYPE_AND_SQUADS -> TypeAndSquadsScreen(
                                    uiState = uiState,
                                    savedSquads = savedSquads,
                                    onSelectPrimaryType = viewModel::setMatrixPrimaryType,
                                    onSelectSecondaryType = viewModel::setMatrixSecondaryType,
                                    onDeleteSquad = viewModel::deleteSavedSquad,
                                    onToggleTheme = viewModel::toggleTheme,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        uiState.inspectedPokemon?.let { pokemon ->
                            PokemonInspectorModal(
                                pokemon = pokemon,
                                recommendation = uiState.inspectedCounterRecommendation,
                                onDismiss = { viewModel.inspectPokemon(null) },
                                onAddToOpposingTeam = {
                                    viewModel.addOrReplaceOpponent(it)
                                    viewModel.selectTab(AppTab.ANALYZER)
                                },
                                onAnalyzeCountersAgainst = {
                                    viewModel.analyzeSinglePokemonAsOpponent(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
