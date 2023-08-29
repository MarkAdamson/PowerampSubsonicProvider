package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import org.junit.Rule
import org.junit.Test

class AddServerTest {

    @get:Rule
    val addServerTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddServer() {
        launchAddServerScreen(addServerTestRule) {
            typeBaseURL("http://demo.subsonic.org")
            typeUsername("guest1")
            typePassword("guest")
            submit()
        } verify {
            mainScreenIsPresent()
        }
    }
}