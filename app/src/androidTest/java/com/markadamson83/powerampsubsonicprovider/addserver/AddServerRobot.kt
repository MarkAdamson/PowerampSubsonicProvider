package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import com.markadamson83.powerampsubsonicprovider.R

fun launchAddServerScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: AddServerRobot.() -> Unit
): AddServerRobot {
        return AddServerRobot(rule).apply(block)
}

class AddServerRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun typeServerName(serverName: String) {
        val serverNameHint = rule.activity.getString(R.string.server_name_hint)
        rule.onNodeWithText(serverNameHint).performTextInput(serverName)
    }

    fun typeBaseURL(baseURL: String) {
        val baseURLHint = rule.activity.getString(R.string.base_url_hint)
        rule.onNodeWithText(baseURLHint).performTextInput(baseURL)
    }

    fun typeUsername(username: String) {
        val usernameHint = rule.activity.getString(R.string.username_hint)
        rule.onNodeWithText(usernameHint).performTextInput(username)
    }

    fun typePassword(password: String) {
        val passwordHint = rule.activity.getString(R.string.password_hint)
        rule.onNodeWithText(passwordHint).performTextInput(password)
    }

    fun submit() {
        val addServer = rule.activity.getString(R.string.add_server)
        rule.onNodeWithText(addServer).performClick()
    }

    infix fun verify(
        block: AddServerVerification.() -> Unit
    ): AddServerVerification {
        return AddServerVerification(rule).apply(block)
    }
}

class AddServerVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun mainScreenIsPresent() {
        val servers = rule.activity.getString(R.string.servers)
        rule.onNodeWithText(servers)
            .assertIsDisplayed()
    }

    fun badServerNameErrorIsDisplayed() {
        val badServerNameError = rule.activity.getString(R.string.bad_server_name_error)
        rule.onNodeWithText(badServerNameError)
            .assertIsDisplayed()
    }

    fun badURLErrorIsDisplayed() {
        val badURLError = rule.activity.getString(R.string.bad_url_error)
        rule.onNodeWithText(badURLError)
            .assertIsDisplayed()
    }

    fun unresponsiveServerErrorIsDisplayed() {
        val unresponsiveServerError = rule.activity.getString(R.string.unresponsive_server_error)
        rule.onNodeWithText(unresponsiveServerError)
            .assertIsDisplayed()
    }

    fun backendErrorIsDisplayed() {
        val backendError = rule.activity.getString(R.string.backend_error)
        rule.onNodeWithText(backendError)
            .assertIsDisplayed()
    }

    fun offlineErrorIsDisplayed() {
        val offlineError = rule.activity.getString(R.string.offline_error)
        rule.onNodeWithText(offlineError)
            .assertIsDisplayed()
    }
}
