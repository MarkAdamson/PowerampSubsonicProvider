package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.markadamson83.powerampsubsonicprovider.AddServerActivity
import org.junit.Rule
import org.junit.Test

class AddServerTest {

    @get:Rule
    val addServerTestRule = createAndroidComposeRule<AddServerActivity>()

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