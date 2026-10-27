package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun raidPulseApp_rendersCounterLabAndNavigatesToRaidHub() {
        composeTestRule.onNodeWithTag("counter_lab_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tactical_hero_banner").assertIsDisplayed()

        // Navigate to Raid Hub tab
        composeTestRule.onNodeWithTag("nav_tab_raids").performClick()
        composeTestRule.onNodeWithTag("raid_hub_screen").assertIsDisplayed()
    }
}
