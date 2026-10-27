package com.example.data

import com.example.model.*

object PokemonMetaSeedData {

    val allPokemon: List<PokemonMetaEntry> = listOf(
        // --- MASTER LEAGUE & SUPREME RAID META ---
        PokemonMetaEntry(
            id = "rayquaza_mega",
            dexNumber = 384,
            name = "Rayquaza",
            formLabel = "Mega",
            primaryType = PokemonType.DRAGON,
            secondaryType = PokemonType.FLYING,
            baseAtk = 377,
            baseDef = 210,
            baseSta = 233,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Dragon Tail", PokemonType.DRAGON, 13, 9, 3),
            chargedMove1 = MoveInfo("Dragon Ascent", PokemonType.FLYING, 140, 70, isEliteTm = true),
            chargedMove2 = MoveInfo("Breaking Swipe", PokemonType.DRAGON, 50, 35, isEliteTm = true),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 98.4,
                BattleLeague.PVE_RAIDS to 99.9
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 29.8,
            raidTdo = 945.0,
            keyWins = listOf("Groudon", "Palkia", "Virizion", "Buzzwole", "Giratina"),
            keyLosses = listOf("Mamoswine", "Togekiss", "Dialga")
        ),
        PokemonMetaEntry(
            id = "groudon_primal",
            dexNumber = 383,
            name = "Groudon",
            formLabel = "Primal",
            primaryType = PokemonType.GROUND,
            secondaryType = PokemonType.FIRE,
            baseAtk = 353,
            baseDef = 268,
            baseSta = 218,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Mud Shot", PokemonType.GROUND, 3, 9, 2),
            chargedMove1 = MoveInfo("Precipice Blades", PokemonType.GROUND, 130, 60, isEliteTm = true),
            chargedMove2 = MoveInfo("Fire Punch", PokemonType.FIRE, 55, 40, isEliteTm = true),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 97.6,
                BattleLeague.PVE_RAIDS to 99.6
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 27.9,
            raidTdo = 980.0,
            keyWins = listOf("Dialga (Origin)", "Dusk Mane Necrozma", "Zacian", "Heatran", "Melmetal"),
            keyLosses = listOf("Kyogre", "Palkia (Origin)", "Gyarados")
        ),
        PokemonMetaEntry(
            id = "kyogre_primal",
            dexNumber = 382,
            name = "Kyogre",
            formLabel = "Primal",
            primaryType = PokemonType.WATER,
            secondaryType = PokemonType.BUG,
            baseAtk = 353,
            baseDef = 268,
            baseSta = 218,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Waterfall", PokemonType.WATER, 12, 8, 3),
            chargedMove1 = MoveInfo("Origin Pulse", PokemonType.WATER, 130, 60, isEliteTm = true),
            chargedMove2 = MoveInfo("Blizzard", PokemonType.ICE, 140, 75),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 96.2,
                BattleLeague.PVE_RAIDS to 99.2
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 27.4,
            raidTdo = 955.0,
            keyWins = listOf("Primal Groudon", "Ho-Oh", "Rhyperior", "Landorus", "Heatran"),
            keyLosses = listOf("Kartana", "Zekrom", "Palkia (Origin)")
        ),
        PokemonMetaEntry(
            id = "necrozma_dusk_mane",
            dexNumber = 800,
            name = "Necrozma",
            formLabel = "Dusk Mane",
            primaryType = PokemonType.PSYCHIC,
            secondaryType = PokemonType.STEEL,
            baseAtk = 277,
            baseDef = 220,
            baseSta = 200,
            fastMove = MoveInfo("Shadow Claw", PokemonType.GHOST, 6, 8, 2),
            chargedMove1 = MoveInfo("Sunsteel Strike", PokemonType.STEEL, 135, 65, isEliteTm = true),
            chargedMove2 = MoveInfo("Dark Pulse", PokemonType.DARK, 80, 50),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 97.9,
                BattleLeague.PVE_RAIDS to 98.7
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 26.8,
            raidTdo = 890.0,
            keyWins = listOf("Zacian", "Togekiss", "Dialga", "Mewtwo", "Xerneas"),
            keyLosses = listOf("Ho-Oh", "Groudon", "Yveltal", "Zygarde")
        ),
        PokemonMetaEntry(
            id = "necrozma_dawn_wings",
            dexNumber = 800,
            name = "Necrozma",
            formLabel = "Dawn Wings",
            primaryType = PokemonType.PSYCHIC,
            secondaryType = PokemonType.GHOST,
            baseAtk = 277,
            baseDef = 220,
            baseSta = 200,
            fastMove = MoveInfo("Shadow Claw", PokemonType.GHOST, 6, 8, 2),
            chargedMove1 = MoveInfo("Moongeist Beam", PokemonType.GHOST, 135, 65, isEliteTm = true),
            chargedMove2 = MoveInfo("Dark Pulse", PokemonType.DARK, 80, 50),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 96.4,
                BattleLeague.PVE_RAIDS to 98.9
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 27.1,
            raidTdo = 875.0,
            keyWins = listOf("Mewtwo", "Lugia", "Dusk Mane Necrozma", "Palkia", "Solgaleo"),
            keyLosses = listOf("Yveltal", "Tyranitar", "Zygarde")
        ),
        PokemonMetaEntry(
            id = "palkia_origin",
            dexNumber = 484,
            name = "Palkia",
            formLabel = "Origin",
            primaryType = PokemonType.WATER,
            secondaryType = PokemonType.DRAGON,
            baseAtk = 286,
            baseDef = 223,
            baseSta = 189,
            fastMove = MoveInfo("Dragon Breath", PokemonType.DRAGON, 4, 3, 1),
            chargedMove1 = MoveInfo("Spacial Rend", PokemonType.DRAGON, 95, 50, isEliteTm = true),
            chargedMove2 = MoveInfo("Aqua Tail", PokemonType.WATER, 55, 35),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 98.8,
                BattleLeague.PVE_RAIDS to 96.8
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.5,
            raidTdo = 840.0,
            keyWins = listOf("Groudon", "Kyogre", "Ho-Oh", "Landorus (Therian)", "Rhyperior"),
            keyLosses = listOf("Dialga (Origin)", "Togekiss", "Xerneas", "Zacian")
        ),
        PokemonMetaEntry(
            id = "dialga_origin",
            dexNumber = 483,
            name = "Dialga",
            formLabel = "Origin",
            primaryType = PokemonType.STEEL,
            secondaryType = PokemonType.DRAGON,
            baseAtk = 270,
            baseDef = 225,
            baseSta = 205,
            fastMove = MoveInfo("Dragon Breath", PokemonType.DRAGON, 4, 3, 1),
            chargedMove1 = MoveInfo("Roar of Time", PokemonType.DRAGON, 150, 65, isEliteTm = true),
            chargedMove2 = MoveInfo("Iron Head", PokemonType.STEEL, 70, 50),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 97.4,
                BattleLeague.PVE_RAIDS to 95.8
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.1,
            raidTdo = 885.0,
            keyWins = listOf("Palkia (Origin)", "Giratina", "Kyogre", "Mewtwo", "Dragonite"),
            keyLosses = listOf("Groudon", "Landorus (Therian)", "Rhyperior", "Annihilape")
        ),
        PokemonMetaEntry(
            id = "mewtwo_shadow",
            dexNumber = 150,
            name = "Mewtwo",
            formLabel = "Shadow",
            primaryType = PokemonType.PSYCHIC,
            baseAtk = 300,
            baseDef = 182,
            baseSta = 214,
            isShadow = true,
            fastMove = MoveInfo("Psycho Cut", PokemonType.PSYCHIC, 3, 9, 2),
            chargedMove1 = MoveInfo("Psystrike", PokemonType.PSYCHIC, 90, 45, isEliteTm = true),
            chargedMove2 = MoveInfo("Ice Beam", PokemonType.ICE, 90, 55),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 94.1,
                BattleLeague.PVE_RAIDS to 99.1
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 28.4,
            raidTdo = 765.0,
            keyWins = listOf("Terrakion", "Machamp", "Clodsire", "Dragonite", "Landorus"),
            keyLosses = listOf("Yveltal", "Dawn Wings Necrozma", "Tyranitar")
        ),
        PokemonMetaEntry(
            id = "lucario_mega",
            dexNumber = 448,
            name = "Lucario",
            formLabel = "Mega",
            primaryType = PokemonType.FIGHTING,
            secondaryType = PokemonType.STEEL,
            baseAtk = 310,
            baseDef = 175,
            baseSta = 172,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Force Palm", PokemonType.FIGHTING, 13, 10, 3, isEliteTm = true),
            chargedMove1 = MoveInfo("Aura Sphere", PokemonType.FIGHTING, 100, 55),
            chargedMove2 = MoveInfo("Shadow Ball", PokemonType.GHOST, 100, 55),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 92.5,
                BattleLeague.PVE_RAIDS to 99.5
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 28.9,
            raidTdo = 820.0,
            keyWins = listOf("Dialga", "Tyranitar", "Rhyperior", "Blissey", "Regigigas"),
            keyLosses = listOf("Ho-Oh", "Groudon", "Mewtwo")
        ),
        PokemonMetaEntry(
            id = "terrakion",
            dexNumber = 639,
            name = "Terrakion",
            primaryType = PokemonType.ROCK,
            secondaryType = PokemonType.FIGHTING,
            baseAtk = 260,
            baseDef = 192,
            baseSta = 209,
            fastMove = MoveInfo("Double Kick", PokemonType.FIGHTING, 8, 12, 3),
            chargedMove1 = MoveInfo("Sacred Sword", PokemonType.FIGHTING, 60, 35, isEliteTm = true),
            chargedMove2 = MoveInfo("Rock Slide", PokemonType.ROCK, 75, 45),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 90.2,
                BattleLeague.PVE_RAIDS to 96.4
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.4,
            raidTdo = 760.0,
            keyWins = listOf("Dialga", "Ho-Oh", "Yveltal", "Tyranitar", "Snorlax"),
            keyLosses = listOf("Mewtwo", "Kyogre", "Zacian")
        ),
        PokemonMetaEntry(
            id = "kartana",
            dexNumber = 798,
            name = "Kartana",
            primaryType = PokemonType.GRASS,
            secondaryType = PokemonType.STEEL,
            baseAtk = 323,
            baseDef = 182,
            baseSta = 139,
            fastMove = MoveInfo("Razor Leaf", PokemonType.GRASS, 10, 4, 2),
            chargedMove1 = MoveInfo("Leaf Blade", PokemonType.GRASS, 70, 35),
            chargedMove2 = MoveInfo("Night Slash", PokemonType.DARK, 50, 35),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 86.5,
                BattleLeague.PVE_RAIDS to 97.8
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 25.9,
            raidTdo = 690.0,
            keyWins = listOf("Kyogre", "Groudon", "Rhyperior", "Swampert", "Gastrodon"),
            keyLosses = listOf("Ho-Oh", "Reshiram", "Heatran")
        ),
        PokemonMetaEntry(
            id = "rhyperior_shadow",
            dexNumber = 464,
            name = "Rhyperior",
            formLabel = "Shadow",
            primaryType = PokemonType.GROUND,
            secondaryType = PokemonType.ROCK,
            baseAtk = 241,
            baseDef = 190,
            baseSta = 251,
            isShadow = true,
            fastMove = MoveInfo("Mud-Slap", PokemonType.GROUND, 12, 10, 3),
            chargedMove1 = MoveInfo("Rock Wrecker", PokemonType.ROCK, 110, 50, isEliteTm = true),
            chargedMove2 = MoveInfo("Breaking Swipe", PokemonType.DRAGON, 50, 35),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 96.8,
                BattleLeague.PVE_RAIDS to 97.5
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 25.2,
            raidTdo = 830.0,
            keyWins = listOf("Ho-Oh", "Dialga", "Yveltal", "Dusk Mane Necrozma", "Zapdos"),
            keyLosses = listOf("Kyogre", "Kartana", "Palkia (Origin)", "Zarude")
        ),
        PokemonMetaEntry(
            id = "mamoswine_shadow",
            dexNumber = 473,
            name = "Mamoswine",
            formLabel = "Shadow",
            primaryType = PokemonType.ICE,
            secondaryType = PokemonType.GROUND,
            baseAtk = 247,
            baseDef = 146,
            baseSta = 242,
            isShadow = true,
            fastMove = MoveInfo("Powder Snow", PokemonType.ICE, 6, 8, 2),
            chargedMove1 = MoveInfo("Avalanche", PokemonType.ICE, 90, 45),
            chargedMove2 = MoveInfo("High Horsepower", PokemonType.GROUND, 100, 60),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 91.5,
                BattleLeague.PVE_RAIDS to 98.2
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 26.3,
            raidTdo = 710.0,
            keyWins = listOf("Rayquaza", "Zygarde", "Dragonite", "Garchomp", "Landorus"),
            keyLosses = listOf("Kyogre", "Dusk Mane Necrozma", "Lucario")
        ),
        PokemonMetaEntry(
            id = "tyranitar_mega",
            dexNumber = 248,
            name = "Tyranitar",
            formLabel = "Mega",
            primaryType = PokemonType.ROCK,
            secondaryType = PokemonType.DARK,
            baseAtk = 309,
            baseDef = 276,
            baseSta = 225,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Bite", PokemonType.DARK, 4, 2, 1),
            chargedMove1 = MoveInfo("Brutal Swing", PokemonType.DARK, 65, 40),
            chargedMove2 = MoveInfo("Stone Edge", PokemonType.ROCK, 100, 55),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 91.8,
                BattleLeague.PVE_RAIDS to 97.9
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.8,
            raidTdo = 960.0,
            keyWins = listOf("Mewtwo", "Dawn Wings Necrozma", "Ho-Oh", "Lugia", "Giratina"),
            keyLosses = listOf("Lucario", "Terrakion", "Machamp", "Conkeldurr")
        ),
        PokemonMetaEntry(
            id = "gardevoir_mega",
            dexNumber = 282,
            name = "Gardevoir",
            formLabel = "Mega",
            primaryType = PokemonType.PSYCHIC,
            secondaryType = PokemonType.FAIRY,
            baseAtk = 326,
            baseDef = 229,
            baseSta = 169,
            isMegaOrPrimal = true,
            fastMove = MoveInfo("Charm", PokemonType.FAIRY, 15, 6, 3),
            chargedMove1 = MoveInfo("Dazzling Gleam", PokemonType.FAIRY, 100, 70),
            chargedMove2 = MoveInfo("Synchronoise", PokemonType.PSYCHIC, 80, 50, isEliteTm = true),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 90.4,
                BattleLeague.PVE_RAIDS to 97.1
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.9,
            raidTdo = 785.0,
            keyWins = listOf("Palkia", "Guzzlord", "Rayquaza", "Zekrom", "Yveltal"),
            keyLosses = listOf("Dusk Mane Necrozma", "Metagross", "Dialga")
        ),
        PokemonMetaEntry(
            id = "xurkitree",
            dexNumber = 796,
            name = "Xurkitree",
            primaryType = PokemonType.ELECTRIC,
            baseAtk = 330,
            baseDef = 144,
            baseSta = 195,
            fastMove = MoveInfo("Thunder Shock", PokemonType.ELECTRIC, 3, 9, 2),
            chargedMove1 = MoveInfo("Discharge", PokemonType.ELECTRIC, 65, 45),
            chargedMove2 = MoveInfo("Dazzling Gleam", PokemonType.FAIRY, 100, 70),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 85.0,
                BattleLeague.PVE_RAIDS to 97.3
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 25.7,
            raidTdo = 695.0,
            keyWins = listOf("Kyogre", "Ho-Oh", "Lugia", "Yveltal", "Gyarados"),
            keyLosses = listOf("Groudon", "Rhyperior", "Landorus")
        ),
        PokemonMetaEntry(
            id = "reshiram",
            dexNumber = 643,
            name = "Reshiram",
            primaryType = PokemonType.DRAGON,
            secondaryType = PokemonType.FIRE,
            baseAtk = 275,
            baseDef = 211,
            baseSta = 205,
            fastMove = MoveInfo("Fire Fang", PokemonType.FIRE, 8, 5, 2),
            chargedMove1 = MoveInfo("Fusion Flare", PokemonType.FIRE, 140, 45, isEliteTm = true),
            chargedMove2 = MoveInfo("Draco Meteor", PokemonType.DRAGON, 150, 65),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 92.8,
                BattleLeague.PVE_RAIDS to 96.9
            ),
            role = MetaRole.RAID_ATTACKER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 24.6,
            raidTdo = 835.0,
            keyWins = listOf("Kartana", "Dusk Mane Necrozma", "Zacian", "Genesect", "Metagross"),
            keyLosses = listOf("Palkia (Origin)", "Rhyperior", "Groudon")
        ),
        PokemonMetaEntry(
            id = "ho_oh",
            dexNumber = 250,
            name = "Ho-Oh",
            primaryType = PokemonType.FIRE,
            secondaryType = PokemonType.FLYING,
            baseAtk = 239,
            baseDef = 244,
            baseSta = 214,
            fastMove = MoveInfo("Incinerate", PokemonType.FIRE, 20, 20, 5),
            chargedMove1 = MoveInfo("Sacred Fire", PokemonType.FIRE, 130, 65, isEliteTm = true),
            chargedMove2 = MoveInfo("Brave Bird", PokemonType.FLYING, 130, 55),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 96.9,
                BattleLeague.PVE_RAIDS to 90.5
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 21.2,
            raidTdo = 810.0,
            keyWins = listOf("Dusk Mane Necrozma", "Zacian", "Xerneas", "Dialga", "Zarude"),
            keyLosses = listOf("Rhyperior", "Palkia (Origin)", "Kyogre")
        ),
        PokemonMetaEntry(
            id = "landorus_therian",
            dexNumber = 645,
            name = "Landorus",
            formLabel = "Therian",
            primaryType = PokemonType.GROUND,
            secondaryType = PokemonType.FLYING,
            baseAtk = 289,
            baseDef = 179,
            baseSta = 205,
            fastMove = MoveInfo("Mud Shot", PokemonType.GROUND, 3, 9, 2),
            chargedMove1 = MoveInfo("Sandsear Storm", PokemonType.GROUND, 60, 45, isEliteTm = true),
            chargedMove2 = MoveInfo("Stone Edge", PokemonType.ROCK, 100, 55),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 96.5,
                BattleLeague.PVE_RAIDS to 95.1
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 23.6,
            raidTdo = 740.0,
            keyWins = listOf("Dialga (Origin)", "Dusk Mane Necrozma", "Rhyperior", "Ho-Oh"),
            keyLosses = listOf("Mamoswine", "Kyogre", "Palkia (Origin)")
        ),
        PokemonMetaEntry(
            id = "zygarde_complete",
            dexNumber = 718,
            name = "Zygarde",
            formLabel = "Complete",
            primaryType = PokemonType.DRAGON,
            secondaryType = PokemonType.GROUND,
            baseAtk = 184,
            baseDef = 207,
            baseSta = 389,
            fastMove = MoveInfo("Dragon Tail", PokemonType.DRAGON, 13, 9, 3),
            chargedMove1 = MoveInfo("Crunch", PokemonType.DARK, 70, 45),
            chargedMove2 = MoveInfo("Earthquake", PokemonType.GROUND, 110, 65),
            leagueScores = mapOf(
                BattleLeague.ULTRA_LEAGUE to 96.9,
                BattleLeague.MASTER_LEAGUE to 97.5
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(
                BattleLeague.ULTRA_LEAGUE to "1/15/15 (Lv 27)",
                BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"
            ),
            raidDps = 17.5,
            raidTdo = 940.0,
            keyWins = listOf("Dusk Mane Necrozma", "Groudon", "Rhyperior", "Giratina", "Ho-Oh"),
            keyLosses = listOf("Togekiss", "Xerneas", "Mamoswine")
        ),
        PokemonMetaEntry(
            id = "zacian_hero",
            dexNumber = 888,
            name = "Zacian",
            formLabel = "Hero",
            primaryType = PokemonType.FAIRY,
            baseAtk = 254,
            baseDef = 236,
            baseSta = 192,
            fastMove = MoveInfo("Snarl", PokemonType.DARK, 5, 13, 3),
            chargedMove1 = MoveInfo("Play Rough", PokemonType.FAIRY, 90, 60),
            chargedMove2 = MoveInfo("Close Combat", PokemonType.FIGHTING, 100, 45),
            leagueScores = mapOf(
                BattleLeague.MASTER_LEAGUE to 95.8,
                BattleLeague.PVE_RAIDS to 91.2
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(BattleLeague.MASTER_LEAGUE to "15/15/15 (Lv 50)"),
            raidDps = 21.8,
            raidTdo = 760.0,
            keyWins = listOf("Palkia (Origin)", "Dialga (Origin)", "Yveltal", "Zygarde"),
            keyLosses = listOf("Dusk Mane Necrozma", "Ho-Oh", "Metagross")
        ),

        // --- GREAT LEAGUE & ULTRA LEAGUE PVP SPECIALISTS ---
        PokemonMetaEntry(
            id = "clodsire",
            dexNumber = 980,
            name = "Clodsire",
            primaryType = PokemonType.POISON,
            secondaryType = PokemonType.GROUND,
            baseAtk = 126,
            baseDef = 158,
            baseSta = 277,
            fastMove = MoveInfo("Poison Sting", PokemonType.POISON, 4, 9, 2),
            chargedMove1 = MoveInfo("Earthquake", PokemonType.GROUND, 110, 65),
            chargedMove2 = MoveInfo("Stone Edge", PokemonType.ROCK, 100, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 98.1
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "0/14/13 (Lv 30.5)"),
            raidDps = 11.4,
            raidTdo = 450.0,
            keyWins = listOf("Azumarill", "Bastiodon", "Registeel", "Talonflame", "Wigglytuff"),
            keyLosses = listOf("Feraligatr", "Gastrodon", "Skarmory", "Mandibuzz")
        ),
        PokemonMetaEntry(
            id = "feraligatr_shadow",
            dexNumber = 160,
            name = "Feraligatr",
            formLabel = "Shadow",
            primaryType = PokemonType.WATER,
            baseAtk = 205,
            baseDef = 188,
            baseSta = 198,
            isShadow = true,
            fastMove = MoveInfo("Shadow Claw", PokemonType.GHOST, 6, 8, 2),
            chargedMove1 = MoveInfo("Hydro Cannon", PokemonType.WATER, 80, 40, isEliteTm = true),
            chargedMove2 = MoveInfo("Ice Beam", PokemonType.ICE, 90, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 97.6,
                BattleLeague.ULTRA_LEAGUE to 98.2,
                BattleLeague.PVE_RAIDS to 91.5
            ),
            role = MetaRole.CHARGER,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "0/11/13 (Lv 19.5)",
                BattleLeague.ULTRA_LEAGUE to "0/12/15 (Lv 33.5)"
            ),
            raidDps = 22.4,
            raidTdo = 670.0,
            keyWins = listOf("Clodsire", "Mandibuzz", "Talonflame", "Giratina", "Gligar"),
            keyLosses = listOf("Lickilicky", "Serperior", "Lanturn", "Tapu Fini")
        ),
        PokemonMetaEntry(
            id = "azumarill",
            dexNumber = 184,
            name = "Azumarill",
            primaryType = PokemonType.WATER,
            secondaryType = PokemonType.FAIRY,
            baseAtk = 112,
            baseDef = 152,
            baseSta = 225,
            fastMove = MoveInfo("Bubble", PokemonType.WATER, 7, 11, 3),
            chargedMove1 = MoveInfo("Play Rough", PokemonType.FAIRY, 90, 60),
            chargedMove2 = MoveInfo("Ice Beam", PokemonType.ICE, 90, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 96.9
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "0/15/15 (Lv 45.5)"),
            raidDps = 10.5,
            raidTdo = 410.0,
            keyWins = listOf("Mandibuzz", "Annihilape", "Sableye", "Umbreon", "Feraligatr"),
            keyLosses = listOf("Clodsire", "Toxapex", "Venusaur", "Serperior")
        ),
        PokemonMetaEntry(
            id = "mandibuzz",
            dexNumber = 630,
            name = "Mandibuzz",
            primaryType = PokemonType.DARK,
            secondaryType = PokemonType.FLYING,
            baseAtk = 129,
            baseDef = 205,
            baseSta = 242,
            fastMove = MoveInfo("Snarl", PokemonType.DARK, 5, 13, 3),
            chargedMove1 = MoveInfo("Dark Pulse", PokemonType.DARK, 80, 50),
            chargedMove2 = MoveInfo("Aerial Ace", PokemonType.FLYING, 55, 40),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 96.4,
                BattleLeague.ULTRA_LEAGUE to 94.8
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "0/13/15 (Lv 27.5)",
                BattleLeague.ULTRA_LEAGUE to "15/15/15 (Lv 50)"
            ),
            raidDps = 11.8,
            raidTdo = 520.0,
            keyWins = listOf("Gastrodon", "Serperior", "Trevenant", "Cresselia", "Giratina"),
            keyLosses = listOf("Azumarill", "Bastiodon", "Carbink", "Wigglytuff")
        ),
        PokemonMetaEntry(
            id = "gastrodon",
            dexNumber = 423,
            name = "Gastrodon",
            primaryType = PokemonType.WATER,
            secondaryType = PokemonType.GROUND,
            baseAtk = 169,
            baseDef = 143,
            baseSta = 244,
            fastMove = MoveInfo("Mud-Slap", PokemonType.GROUND, 12, 10, 3),
            chargedMove1 = MoveInfo("Body Slam", PokemonType.NORMAL, 50, 35),
            chargedMove2 = MoveInfo("Earth Power", PokemonType.GROUND, 90, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 96.2,
                BattleLeague.ULTRA_LEAGUE to 91.4
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "1/15/14 (Lv 24.5)"),
            raidDps = 13.8,
            raidTdo = 480.0,
            keyWins = listOf("Clodsire", "Bastiodon", "Registeel", "Dunsparce", "Talonflame"),
            keyLosses = listOf("Serperior", "Jumpluff", "Abomasnow", "Mandibuzz")
        ),
        PokemonMetaEntry(
            id = "annihilape",
            dexNumber = 979,
            name = "Annihilape",
            primaryType = PokemonType.FIGHTING,
            secondaryType = PokemonType.GHOST,
            baseAtk = 220,
            baseDef = 178,
            baseSta = 242,
            fastMove = MoveInfo("Counter", PokemonType.FIGHTING, 8, 6, 2),
            chargedMove1 = MoveInfo("Rage Fist", PokemonType.GHOST, 50, 35, isEliteTm = true),
            chargedMove2 = MoveInfo("Close Combat", PokemonType.FIGHTING, 100, 45),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 94.6,
                BattleLeague.ULTRA_LEAGUE to 95.8,
                BattleLeague.MASTER_LEAGUE to 91.2
            ),
            role = MetaRole.CHARGER,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "2/15/15 (Lv 17)",
                BattleLeague.ULTRA_LEAGUE to "0/15/14 (Lv 29.5)"
            ),
            raidDps = 18.9,
            raidTdo = 640.0,
            keyWins = listOf("Bastiodon", "Lickilicky", "Registeel", "Dialga", "Cobalion"),
            keyLosses = listOf("Azumarill", "Wigglytuff", "Talonflame", "Mandibuzz")
        ),
        PokemonMetaEntry(
            id = "talonflame",
            dexNumber = 663,
            name = "Talonflame",
            primaryType = PokemonType.FIRE,
            secondaryType = PokemonType.FLYING,
            baseAtk = 176,
            baseDef = 155,
            baseSta = 186,
            fastMove = MoveInfo("Incinerate", PokemonType.FIRE, 20, 20, 5, isEliteTm = true),
            chargedMove1 = MoveInfo("Flame Charge", PokemonType.FIRE, 65, 50),
            chargedMove2 = MoveInfo("Fly", PokemonType.FLYING, 80, 45),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 94.2,
                BattleLeague.ULTRA_LEAGUE to 96.5
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "0/13/15 (Lv 26)",
                BattleLeague.ULTRA_LEAGUE to "15/15/15 (Lv 50)"
            ),
            raidDps = 15.6,
            raidTdo = 470.0,
            keyWins = listOf("Annihilape", "Serperior", "Skarmory", "Registeel", "Cobalion"),
            keyLosses = listOf("Feraligatr", "Bastiodon", "Gastrodon", "Tapu Fini")
        ),
        PokemonMetaEntry(
            id = "skarmory",
            dexNumber = 227,
            name = "Skarmory",
            primaryType = PokemonType.STEEL,
            secondaryType = PokemonType.FLYING,
            baseAtk = 148,
            baseDef = 226,
            baseSta = 163,
            fastMove = MoveInfo("Steel Wing", PokemonType.STEEL, 7, 6, 2),
            chargedMove1 = MoveInfo("Sky Attack", PokemonType.FLYING, 85, 55),
            chargedMove2 = MoveInfo("Brave Bird", PokemonType.FLYING, 130, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 91.8,
                BattleLeague.ULTRA_LEAGUE to 88.4
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "0/15/14 (Lv 27.5)"),
            raidDps = 12.9,
            raidTdo = 490.0,
            keyWins = listOf("Serperior", "Jumpluff", "Clodsire", "Wigglytuff", "Venusaur"),
            keyLosses = listOf("Talonflame", "Bastiodon", "Lanturn", "Magnezone")
        ),
        PokemonMetaEntry(
            id = "bastiodon",
            dexNumber = 411,
            name = "Bastiodon",
            primaryType = PokemonType.ROCK,
            secondaryType = PokemonType.STEEL,
            baseAtk = 94,
            baseDef = 286,
            baseSta = 155,
            fastMove = MoveInfo("Smack Down", PokemonType.ROCK, 12, 8, 3),
            chargedMove1 = MoveInfo("Stone Edge", PokemonType.ROCK, 100, 55),
            chargedMove2 = MoveInfo("Flamethrower", PokemonType.FIRE, 90, 55),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 92.6
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "0/15/14 (Lv 50)"),
            raidDps = 9.2,
            raidTdo = 460.0,
            keyWins = listOf("Talonflame", "Mandibuzz", "Skarmory", "Jumpluff", "Cresselia"),
            keyLosses = listOf("Gastrodon", "Clodsire", "Annihilape", "Poliwrath")
        ),
        PokemonMetaEntry(
            id = "cresselia",
            dexNumber = 488,
            name = "Cresselia",
            primaryType = PokemonType.PSYCHIC,
            baseAtk = 152,
            baseDef = 258,
            baseSta = 260,
            fastMove = MoveInfo("Psycho Cut", PokemonType.PSYCHIC, 3, 9, 2),
            chargedMove1 = MoveInfo("Grass Knot", PokemonType.GRASS, 90, 50, isEliteTm = true),
            chargedMove2 = MoveInfo("Moonblast", PokemonType.FAIRY, 110, 60),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 91.5,
                BattleLeague.ULTRA_LEAGUE to 95.9
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "2/15/13 (Lv 19.5)",
                BattleLeague.ULTRA_LEAGUE to "1/15/13 (Lv 35.5)"
            ),
            raidDps = 12.1,
            raidTdo = 620.0,
            keyWins = listOf("Poliwrath", "Gastrodon", "Annihilape", "Swampert", "Virizion"),
            keyLosses = listOf("Mandibuzz", "Registeel", "Giratina (Altered)", "Skeledirge")
        ),
        PokemonMetaEntry(
            id = "giratina_altered",
            dexNumber = 487,
            name = "Giratina",
            formLabel = "Altered",
            primaryType = PokemonType.GHOST,
            secondaryType = PokemonType.DRAGON,
            baseAtk = 187,
            baseDef = 225,
            baseSta = 284,
            fastMove = MoveInfo("Shadow Claw", PokemonType.GHOST, 6, 8, 2),
            chargedMove1 = MoveInfo("Dragon Claw", PokemonType.DRAGON, 50, 35),
            chargedMove2 = MoveInfo("Shadow Sneak", PokemonType.GHOST, 50, 45),
            leagueScores = mapOf(
                BattleLeague.ULTRA_LEAGUE to 96.7,
                BattleLeague.MASTER_LEAGUE to 91.0
            ),
            role = MetaRole.SAFE_SWAP,
            optimalIvSpread = mapOf(BattleLeague.ULTRA_LEAGUE to "1/12/15 (Lv 28)"),
            raidDps = 15.8,
            raidTdo = 690.0,
            keyWins = listOf("Cresselia", "Poliwrath", "Talonflame", "Cobalion", "Virizion"),
            keyLosses = listOf("Mandibuzz", "Clefable", "Tapu Fini", "Feraligatr")
        ),
        PokemonMetaEntry(
            id = "registeel",
            dexNumber = 379,
            name = "Registeel",
            primaryType = PokemonType.STEEL,
            baseAtk = 143,
            baseDef = 285,
            baseSta = 190,
            fastMove = MoveInfo("Lock-On", PokemonType.NORMAL, 1, 5, 1),
            chargedMove1 = MoveInfo("Zap Cannon", PokemonType.ELECTRIC, 150, 80, isEliteTm = true),
            chargedMove2 = MoveInfo("Focus Blast", PokemonType.FIGHTING, 150, 75),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 92.4,
                BattleLeague.ULTRA_LEAGUE to 96.1
            ),
            role = MetaRole.CLOSER,
            optimalIvSpread = mapOf(
                BattleLeague.GREAT_LEAGUE to "2/13/14 (Lv 23.5)",
                BattleLeague.ULTRA_LEAGUE to "2/15/15 (Lv 46.5)"
            ),
            raidDps = 11.2,
            raidTdo = 540.0,
            keyWins = listOf("Cresselia", "Mandibuzz", "Tapu Fini", "Skarmory", "Pidgeot"),
            keyLosses = listOf("Annihilape", "Talonflame", "Gastrodon", "Poliwrath")
        ),
        PokemonMetaEntry(
            id = "tapu_fini",
            dexNumber = 788,
            name = "Tapu Fini",
            primaryType = PokemonType.WATER,
            secondaryType = PokemonType.FAIRY,
            baseAtk = 189,
            baseDef = 244,
            baseSta = 172,
            fastMove = MoveInfo("Water Gun", PokemonType.WATER, 3, 3, 1),
            chargedMove1 = MoveInfo("Surf", PokemonType.WATER, 65, 40),
            chargedMove2 = MoveInfo("Moonblast", PokemonType.FAIRY, 110, 60),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 90.8,
                BattleLeague.ULTRA_LEAGUE to 96.3
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.ULTRA_LEAGUE to "1/15/14 (Lv 31)"),
            raidDps = 14.9,
            raidTdo = 560.0,
            keyWins = listOf("Giratina", "Poliwrath", "Mandibuzz", "Talonflame", "Guzzlord"),
            keyLosses = listOf("Registeel", "Venusaur", "Tentacruel", "Cresselia")
        ),
        PokemonMetaEntry(
            id = "serperior",
            dexNumber = 497,
            name = "Serperior",
            primaryType = PokemonType.GRASS,
            baseAtk = 161,
            baseDef = 204,
            baseSta = 181,
            fastMove = MoveInfo("Vine Whip", PokemonType.GRASS, 5, 8, 2),
            chargedMove1 = MoveInfo("Frenzy Plant", PokemonType.GRASS, 100, 45, isEliteTm = true),
            chargedMove2 = MoveInfo("Aerial Ace", PokemonType.FLYING, 55, 40),
            leagueScores = mapOf(
                BattleLeague.GREAT_LEAGUE to 95.8,
                BattleLeague.ULTRA_LEAGUE to 91.2
            ),
            role = MetaRole.LEAD,
            optimalIvSpread = mapOf(BattleLeague.GREAT_LEAGUE to "0/11/15 (Lv 25)"),
            raidDps = 14.5,
            raidTdo = 480.0,
            keyWins = listOf("Gastrodon", "Feraligatr", "Azumarill", "Lanturn", "Whiscash"),
            keyLosses = listOf("Talonflame", "Skarmory", "Mandibuzz", "Clodsire")
        )
    )

    val raidBosses: List<RaidBossEntry> = listOf(
        RaidBossEntry(
            id = "boss_rayquaza_mega",
            pokemon = allPokemon.first { it.id == "rayquaza_mega" },
            tierLabel = "Mega Legendary Raid",
            bossHp = 22500,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2102,
            catchCpLv20Max = 2191,
            catchCpLv25Min = 2627,
            catchCpLv25Max = 2739,
            bossMovesets = listOf("Dragon Tail + Outrage", "Air Slash + Dragon Ascent*"),
            soloOrDuoNote = "2.56x Double Weak to Ice! Easy Duo with Lv40+ Shadow Mamoswine in Snow."
        ),
        RaidBossEntry(
            id = "boss_groudon_primal",
            pokemon = allPokemon.first { it.id == "groudon_primal" },
            tierLabel = "Primal Raid (Tier 6)",
            bossHp = 22500,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2260,
            catchCpLv20Max = 2351,
            catchCpLv25Min = 2825,
            catchCpLv25Max = 2939,
            bossMovesets = listOf("Mud Shot + Precipice Blades", "Dragon Tail + Solar Beam"),
            soloOrDuoNote = "2.56x Double Weak to Water! Primal Kyogre & Shadow Feraligatr shred it in Rain."
        ),
        RaidBossEntry(
            id = "boss_palkia_origin",
            pokemon = allPokemon.first { it.id == "palkia_origin" },
            tierLabel = "Tier 5 Legendary",
            bossHp = 15000,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2269,
            catchCpLv20Max = 2367,
            catchCpLv25Min = 2836,
            catchCpLv25Max = 2958,
            bossMovesets = listOf("Dragon Tail + Spacial Rend*", "Dragon Breath + Hydro Pump"),
            soloOrDuoNote = "Weak ONLY to Dragon & Fairy (1.6x). Bring Mega Rayquaza, Origin Dialga, or Mega Gardevoir."
        ),
        RaidBossEntry(
            id = "boss_necrozma_dusk_mane",
            pokemon = allPokemon.first { it.id == "necrozma_dusk_mane" },
            tierLabel = "Tier 5 Fusion Raid",
            bossHp = 15000,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2018,
            catchCpLv20Max = 2104,
            catchCpLv25Min = 2522,
            catchCpLv25Max = 2630,
            bossMovesets = listOf("Metal Claw + Sunsteel Strike*", "Psycho Cut + Dark Pulse"),
            soloOrDuoNote = "1.6x Weak to Ground, Fire, Ghost, and Dark. Primal Groudon & Dawn Wings Necrozma dominate."
        ),
        RaidBossEntry(
            id = "boss_mewtwo_shadow",
            pokemon = allPokemon.first { it.id == "mewtwo_shadow" },
            tierLabel = "Tier 5 Shadow Raid",
            bossHp = 15000,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2275,
            catchCpLv20Max = 2387,
            catchCpLv25Min = 2844,
            catchCpLv25Max = 2984,
            bossMovesets = listOf("Confusion + Psystrike*", "Psycho Cut + Focus Blast"),
            soloOrDuoNote = "Enrages at 60% HP! Use Purified Gems + Dawn Wings Necrozma or Mega Tyranitar."
        ),
        RaidBossEntry(
            id = "boss_ho_oh",
            pokemon = allPokemon.first { it.id == "ho_oh" },
            tierLabel = "Tier 5 Legendary",
            bossHp = 15000,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2119,
            catchCpLv20Max = 2207,
            catchCpLv25Min = 2649,
            catchCpLv25Max = 2759,
            bossMovesets = listOf("Incinerate + Sacred Fire*", "Extrasensory + Brave Bird"),
            soloOrDuoNote = "2.56x Double Weak to Rock! Shadow Rhyperior & Mega Tyranitar can solo/duo in Partly Cloudy."
        ),
        RaidBossEntry(
            id = "boss_dialga_origin",
            pokemon = allPokemon.first { it.id == "dialga_origin" },
            tierLabel = "Tier 5 Legendary",
            bossHp = 15000,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2246,
            catchCpLv20Max = 2337,
            catchCpLv25Min = 2807,
            catchCpLv25Max = 2921,
            bossMovesets = listOf("Metal Claw + Roar of Time*", "Dragon Breath + Iron Head"),
            soloOrDuoNote = "Steel/Dragon typing leaves only Fighting & Ground (1.6x) as weaknesses! Use Primal Groudon & Mega Lucario."
        ),
        RaidBossEntry(
            id = "boss_kyogre_primal",
            pokemon = allPokemon.first { it.id == "kyogre_primal" },
            tierLabel = "Primal Raid (Tier 6)",
            bossHp = 22500,
            raidTimerSeconds = 300,
            catchCpLv20Min = 2260,
            catchCpLv20Max = 2351,
            catchCpLv25Min = 2825,
            catchCpLv25Max = 2939,
            bossMovesets = listOf("Waterfall + Origin Pulse*", "Waterfall + Blizzard"),
            soloOrDuoNote = "Weak to Grass & Electric (1.6x). Bring Kartana & Xurkitree, watch out for Blizzard!"
        )
    )

    val opposingTeamPresets: List<OpposingTeamPreset> = listOf(
        OpposingTeamPreset(
            id = "preset_gl_bbml",
            title = "Great League Core",
            league = BattleLeague.GREAT_LEAGUE,
            description = "Clodsire + Feraligatr + Mandibuzz top GBL trio",
            memberIds = listOf("clodsire", "feraligatr_shadow", "mandibuzz")
        ),
        OpposingTeamPreset(
            id = "preset_gl_bastiodon",
            title = "GL Grasshole / Tank Core",
            league = BattleLeague.GREAT_LEAGUE,
            description = "Bastiodon + Azumarill + Serperior bulk lineup",
            memberIds = listOf("bastiodon", "azumarill", "serperior")
        ),
        OpposingTeamPreset(
            id = "preset_ul_trio",
            title = "Ultra League Wall",
            league = BattleLeague.ULTRA_LEAGUE,
            description = "Giratina (Altered) + Cresselia + Registeel",
            memberIds = listOf("giratina_altered", "cresselia", "registeel")
        ),
        OpposingTeamPreset(
            id = "preset_ml_origin",
            title = "Master League Meta",
            league = BattleLeague.MASTER_LEAGUE,
            description = "Origin Palkia + Dusk Mane Necrozma + Ho-Oh",
            memberIds = listOf("palkia_origin", "necrozma_dusk_mane", "ho_oh")
        ),
        OpposingTeamPreset(
            id = "preset_ml_dragons",
            title = "Master Double Dragon",
            league = BattleLeague.MASTER_LEAGUE,
            description = "Zygarde Complete + Origin Dialga + Landorus-T",
            memberIds = listOf("zygarde_complete", "dialga_origin", "landorus_therian")
        )
    )
}
