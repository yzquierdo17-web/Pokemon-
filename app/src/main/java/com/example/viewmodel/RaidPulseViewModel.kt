package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.RaidPulseRepository
import com.example.data.local.SavedSquadEntity
import com.example.engine.TypeEffectivenessEngine
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppTab(val route: String) {
    ANALYZER("counter_lab"),
    RAID_HUB("raid_hub"),
    META_TIERS("meta_tiers"),
    TYPE_AND_SQUADS("type_and_squads")
}

data class RaidPulseUiState(
    val currentTab: AppTab = AppTab.ANALYZER,
    val isDarkTheme: Boolean = true,
    // Opposing Team Counter Lab State
    val selectedLeague: BattleLeague = BattleLeague.GREAT_LEAGUE,
    val opposingTeam: List<PokemonMetaEntry> = emptyList(),
    val teamWeaknesses: List<TeamWeaknessSummary> = emptyList(),
    val teamCounters: List<CounterRecommendation> = emptyList(),
    val activeSlotIndexToPick: Int? = null,
    // Raid Hub State
    val selectedRaidBoss: RaidBossEntry,
    val activeWeather: WeatherCondition = WeatherCondition.EXTREME,
    val includeShadows: Boolean = true,
    val includeMegas: Boolean = true,
    val raidCounters: List<CounterRecommendation> = emptyList(),
    // Meta Rankings & Live PokéAPI State
    val metaLeagueFilter: BattleLeague = BattleLeague.GREAT_LEAGUE,
    val metaRoleFilter: MetaRole? = null,
    val metaTypeFilter: PokemonType? = null,
    val searchQuery: String = "",
    val isFetchingPokeApi: Boolean = false,
    val statusBannerMessage: String? = null,
    // Dual-Type Matrix State
    val matrixPrimaryType: PokemonType = PokemonType.DRAGON,
    val matrixSecondaryType: PokemonType? = PokemonType.FLYING,
    // Inspector Modal State
    val inspectedPokemon: PokemonMetaEntry? = null,
    val inspectedCounterRecommendation: CounterRecommendation? = null
)

class RaidPulseViewModel(
    private val repository: RaidPulseRepository
) : ViewModel() {

    private val initialPreset = repository.opposingPresets.first()
    private val initialTeam = initialPreset.memberIds.mapNotNull { id ->
        repository.pokemonCatalog.value.find { it.id == id }
    }
    private val defaultBoss = repository.raidBosses.first()

    private val _uiState = MutableStateFlow(
        RaidPulseUiState(
            selectedLeague = initialPreset.league,
            opposingTeam = initialTeam,
            selectedRaidBoss = defaultBoss
        )
    )
    val uiState: StateFlow<RaidPulseUiState> = _uiState.asStateFlow()

    val pokemonCatalog: StateFlow<List<PokemonMetaEntry>> = repository.pokemonCatalog
    val raidBosses: List<RaidBossEntry> = repository.raidBosses
    val opposingPresets: List<OpposingTeamPreset> = repository.opposingPresets

    val savedSquads: StateFlow<List<SavedSquadEntity>> = repository.savedSquadsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.ensureStarterSquadsSeeded()
        }
        recomputeAllSimulations()
    }

    fun selectTab(tab: AppTab) {
        _uiState.update { it.copy(currentTab = tab, statusBannerMessage = null) }
    }

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun dismissBanner() {
        _uiState.update { it.copy(statusBannerMessage = null) }
    }

    // --- Opposing Team Counter Lab Actions ---

    fun selectAnalyzerLeague(league: BattleLeague) {
        _uiState.update { it.copy(selectedLeague = league) }
        recomputeTeamAnalysis()
    }

    fun applyOpposingPreset(preset: OpposingTeamPreset) {
        val catalog = repository.pokemonCatalog.value
        val members = preset.memberIds.mapNotNull { id -> catalog.find { it.id == id } }
        _uiState.update {
            it.copy(
                selectedLeague = preset.league,
                opposingTeam = members,
                statusBannerMessage = "Loaded ${preset.title} (${members.joinToString { m -> m.name }})"
            )
        }
        recomputeTeamAnalysis()
    }

    fun openOpponentPicker(slotIndex: Int) {
        _uiState.update { it.copy(activeSlotIndexToPick = slotIndex) }
    }

    fun closeOpponentPicker() {
        _uiState.update { it.copy(activeSlotIndexToPick = null) }
    }

    fun addOrReplaceOpponent(pokemon: PokemonMetaEntry, slotIndex: Int? = null) {
        val current = _uiState.value.opposingTeam.toMutableList()
        val targetSlot = slotIndex ?: _uiState.value.activeSlotIndexToPick
        if (targetSlot != null && targetSlot in 0 until current.size) {
            current[targetSlot] = pokemon
        } else if (current.size < 3 && current.none { it.id == pokemon.id }) {
            current.add(pokemon)
        } else if (current.isNotEmpty() && current.none { it.id == pokemon.id }) {
            current[current.lastIndex] = pokemon
        }
        _uiState.update {
            it.copy(
                opposingTeam = current,
                activeSlotIndexToPick = null,
                inspectedPokemon = null
            )
        }
        recomputeTeamAnalysis()
    }

    fun removeOpponentAt(index: Int) {
        val current = _uiState.value.opposingTeam.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            _uiState.update { it.copy(opposingTeam = current) }
            recomputeTeamAnalysis()
        }
    }

    fun clearOpposingTeam() {
        _uiState.update {
            it.copy(
                opposingTeam = emptyList(),
                teamWeaknesses = emptyList(),
                teamCounters = emptyList()
            )
        }
    }

    fun analyzeSinglePokemonAsOpponent(pokemon: PokemonMetaEntry) {
        _uiState.update {
            it.copy(
                currentTab = AppTab.ANALYZER,
                opposingTeam = listOf(pokemon),
                inspectedPokemon = null,
                inspectedCounterRecommendation = null,
                statusBannerMessage = "Analyzing counters & weaknesses for ${pokemon.fullDisplayName}"
            )
        }
        recomputeTeamAnalysis()
    }

    // --- Raid Hub Actions ---

    fun selectRaidBoss(boss: RaidBossEntry) {
        _uiState.update { it.copy(selectedRaidBoss = boss) }
        recomputeRaidSimulation()
    }

    fun setWeatherCondition(weather: WeatherCondition) {
        _uiState.update { it.copy(activeWeather = weather) }
        recomputeAllSimulations()
    }

    fun toggleIncludeShadows() {
        _uiState.update { it.copy(includeShadows = !it.includeShadows) }
        recomputeAllSimulations()
    }

    fun toggleIncludeMegas() {
        _uiState.update { it.copy(includeMegas = !it.includeMegas) }
        recomputeAllSimulations()
    }

    // --- Meta Rankings & Live PokéAPI Actions ---

    fun setMetaLeagueFilter(league: BattleLeague) {
        _uiState.update { it.copy(metaLeagueFilter = league) }
    }

    fun setMetaRoleFilter(role: MetaRole?) {
        _uiState.update {
            it.copy(metaRoleFilter = if (it.metaRoleFilter == role) null else role)
        }
    }

    fun setMetaTypeFilter(type: PokemonType?) {
        _uiState.update {
            it.copy(metaTypeFilter = if (it.metaTypeFilter == type) null else type)
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun fetchPokemonFromPokeApi(queryOverride: String? = null) {
        val q = (queryOverride ?: _uiState.value.searchQuery).trim()
        if (q.isBlank()) return
        _uiState.update { it.copy(isFetchingPokeApi = true, statusBannerMessage = null) }
        viewModelScope.launch {
            val result = repository.fetchLivePokemonFromPokeApi(q)
            result.fold(
                onSuccess = { entry ->
                    _uiState.update {
                        it.copy(
                            isFetchingPokeApi = false,
                            inspectedPokemon = entry,
                            statusBannerMessage = "Imported ${entry.name} (#${entry.dexNumber}) from PokéAPI!"
                        )
                    }
                    recomputeAllSimulations()
                },
                onFailure = { err ->
                    _uiState.update {
                        it.copy(
                            isFetchingPokeApi = false,
                            statusBannerMessage = "PokéAPI lookup failed for \"$q\". Try an exact name (e.g. 'garchomp', 'lucario') or Dex #."
                        )
                    }
                }
            )
        }
    }

    // --- Dual-Type Matrix & Saved Squads Actions ---

    fun setMatrixPrimaryType(type: PokemonType) {
        _uiState.update { state ->
            val sec = if (state.matrixSecondaryType == type) null else state.matrixSecondaryType
            state.copy(matrixPrimaryType = type, matrixSecondaryType = sec)
        }
    }

    fun setMatrixSecondaryType(type: PokemonType?) {
        _uiState.update { state ->
            val cleanSec = if (type == state.matrixPrimaryType) null else type
            state.copy(matrixSecondaryType = cleanSec)
        }
    }

    fun saveCurrentTeamCounterSquad() {
        val state = _uiState.value
        if (state.opposingTeam.isEmpty() || state.teamCounters.isEmpty()) return
        val topThree = state.teamCounters.take(3)
        val oppNames = state.opposingTeam.joinToString(", ") { it.name }
        val counterIds = topThree.joinToString(",") { it.counter.id }
        val avgScore = (topThree.map { it.overallScore }.average() * 10.0).toInt() / 10.0
        val topCounterNames = topThree.joinToString(" / ") { it.counter.fullDisplayName }

        viewModelScope.launch {
            repository.saveSquad(
                SavedSquadEntity(
                    title = "Counter vs ${state.opposingTeam.first().name} Core",
                    modeLabel = "${state.selectedLeague.displayName} • Counter Trio",
                    targetNamesCsv = oppNames,
                    counterIdsCsv = counterIds,
                    avgTacticalScore = avgScore,
                    notes = "Recommended Trio: $topCounterNames (${state.activeWeather.displayName})"
                )
            )
            _uiState.update {
                it.copy(statusBannerMessage = "Saved Counter Squad ($topCounterNames) to Type & Squads!")
            }
        }
    }

    fun saveCurrentRaidCounterSquad() {
        val state = _uiState.value
        val boss = state.selectedRaidBoss
        val topThree = state.raidCounters.take(3)
        if (topThree.isEmpty()) return
        val counterIds = topThree.joinToString(",") { it.counter.id }
        val avgScore = (topThree.map { it.overallScore }.average() * 10.0).toInt() / 10.0
        val topNames = topThree.joinToString(" / ") { it.counter.fullDisplayName }
        val bestTtw = topThree.first().timeToWinSeconds

        viewModelScope.launch {
            repository.saveSquad(
                SavedSquadEntity(
                    title = "Anti-${boss.pokemon.fullDisplayName} Raid Team",
                    modeLabel = "${boss.tierLabel} • ${state.activeWeather.displayName}",
                    targetNamesCsv = boss.pokemon.fullDisplayName,
                    counterIdsCsv = counterIds,
                    avgTacticalScore = avgScore,
                    notes = "Top Raid Lineup: $topNames (Best TTW: ${bestTtw}s, ~${topThree.first().recommendedTrainers} Trainers)"
                )
            )
            _uiState.update {
                it.copy(statusBannerMessage = "Saved Anti-${boss.pokemon.name} Raid Squad!")
            }
        }
    }

    fun deleteSavedSquad(id: Int) {
        viewModelScope.launch {
            repository.deleteSquad(id)
        }
    }

    fun inspectPokemon(pokemon: PokemonMetaEntry?) {
        _uiState.update { it.copy(inspectedPokemon = pokemon, inspectedCounterRecommendation = null) }
    }

    fun inspectCounterRecommendation(rec: CounterRecommendation?) {
        _uiState.update {
            it.copy(
                inspectedCounterRecommendation = rec,
                inspectedPokemon = rec?.counter
            )
        }
    }

    private fun recomputeAllSimulations() {
        recomputeTeamAnalysis()
        recomputeRaidSimulation()
    }

    private fun recomputeTeamAnalysis() {
        val state = _uiState.value
        val catalog = repository.pokemonCatalog.value
        val weaknesses = TypeEffectivenessEngine.analyzeTeamWeaknesses(state.opposingTeam)
        val counters = TypeEffectivenessEngine.recommendCountersForTeam(
            opposingTeam = state.opposingTeam,
            candidatePool = catalog,
            league = state.selectedLeague,
            weather = state.activeWeather,
            includeShadows = state.includeShadows,
            includeMegas = state.includeMegas
        )
        _uiState.update {
            it.copy(
                teamWeaknesses = weaknesses,
                teamCounters = counters
            )
        }
    }

    private fun recomputeRaidSimulation() {
        val state = _uiState.value
        val catalog = repository.pokemonCatalog.value
        val raidCounters = TypeEffectivenessEngine.simulateRaidCounters(
            boss = state.selectedRaidBoss,
            candidatePool = catalog,
            weather = state.activeWeather,
            includeShadows = state.includeShadows,
            includeMegas = state.includeMegas
        )
        _uiState.update { it.copy(raidCounters = raidCounters) }
    }

    companion object {
        fun provideFactory(repository: RaidPulseRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RaidPulseViewModel(repository) as T
                }
            }
    }
}
