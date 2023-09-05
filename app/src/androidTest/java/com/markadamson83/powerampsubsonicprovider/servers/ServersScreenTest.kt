package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import org.junit.Rule
import org.junit.Test

typealias MainActivityTestRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class ServersScreenTest {

    @get:Rule
    val serversTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun newServer() {
        launchServersScreen(serversTestRule) {
            clickNewServer()
        } verify {
            addServerScreenIsDisplayed()
        }
    }

    @Test
    fun displayNoServersMessage() {
        launchServersScreen(serversTestRule) {
            // no operation
        } verify {
            noServersMessageIsDisplayed()
        }
    }
}