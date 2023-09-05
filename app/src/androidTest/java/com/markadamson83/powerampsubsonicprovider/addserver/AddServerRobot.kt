package com.markadamson83.powerampsubsonicprovider.addserver

import android.content.pm.ActivityInfo
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.markadamson83.powerampsubsonicprovider.MainActivity
import com.markadamson83.powerampsubsonicprovider.R

fun launchAddServerScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: AddServerRobot.() -> Unit
): AddServerRobot {
        return AddServerRobot(rule).apply {
            clickNewServer()
        }.apply(block)
}

class AddServerRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun clickNewServer() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_server))
            .performClick()
    }

    fun setPortraitOrientation() {
        rule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun setLandscapeOrientation() {
        rule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    fun typeServerName(serverName: String) {
        val serverNameHint = rule.activity.getString(R.string.server_name_hint)
        rule.onNodeWithTag(serverNameHint).performTextInput(serverName)
    }

    fun clearServerName() {
        val serverNameHint = rule.activity.getString(R.string.server_name_hint)
        rule.onNodeWithTag(serverNameHint).performTextClearance()
    }

    fun typeBaseURL(baseURL: String) {
        val baseURLHint = rule.activity.getString(R.string.base_url_hint)
        rule.onNodeWithTag(baseURLHint).performTextInput(baseURL)
    }

    fun clearBaseURL() {
        val baseURLHint = rule.activity.getString(R.string.base_url_hint)
        rule.onNodeWithTag(baseURLHint).performTextClearance()
    }

    fun typeUsername(username: String) {
        val usernameHint = rule.activity.getString(R.string.username_hint)
        rule.onNodeWithTag(usernameHint).performTextInput(username)
    }

    fun clearUsername() {
        val usernameHint = rule.activity.getString(R.string.username_hint)
        rule.onNodeWithTag(usernameHint).performTextClearance()
    }

    fun typePassword(password: String) {
        val passwordHint = rule.activity.getString(R.string.password_hint)
        rule.onNodeWithTag(passwordHint).performTextInput(password)
    }

    fun clearPassword() {
        val passwordHint = rule.activity.getString(R.string.password_hint)
        rule.onNodeWithTag(passwordHint).performTextClearance()
    }

    fun togglePasswordVisibility() {
        val passwordVisibilityContentDescription = rule.activity.getString(R.string.toggle_password_visibility)
        rule.onNodeWithTag(passwordVisibilityContentDescription).performClick()
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

    fun badServerNameErrorIsNotDisplayed() {
        val badServerNameError = rule.activity.getString(R.string.bad_server_name_error)
        rule.onNodeWithText(badServerNameError)
            .assertDoesNotExist()
    }

    fun serverNameIs(text: String) {
        val serverNameHint = rule.activity.getString(R.string.server_name_hint)
        rule.onNodeWithTag(serverNameHint)
            .assert(hasText(text))
    }

    fun badURLErrorIsDisplayed() {
        val badURLError = rule.activity.getString(R.string.bad_url_error)
        rule.onNodeWithText(badURLError)
            .assertIsDisplayed()
    }

    fun badURLErrorIsNotDisplayed() {
        val badURLError = rule.activity.getString(R.string.bad_url_error)
        rule.onNodeWithText(badURLError)
            .assertDoesNotExist()
    }

    fun urlIs(text: String) {
        val baseURLHint = rule.activity.getString(R.string.base_url_hint)
        rule.onNodeWithTag(baseURLHint)
            .assert(hasText(text))
    }

    fun badUsernameErrorIsDisplayed() {
        val badUsernameError = rule.activity.getString(R.string.bad_username_error)
        rule.onNodeWithText(badUsernameError)
            .assertIsDisplayed()
    }

    fun badUsernameErrorIsNotDisplayed() {
        val badUsernameError = rule.activity.getString(R.string.bad_username_error)
        rule.onNodeWithText(badUsernameError)
            .assertDoesNotExist()
    }

    fun usernameIs(text: String) {
        val usernameHint = rule.activity.getString(R.string.username_hint)
        rule.onNodeWithTag(usernameHint)
            .assert(hasText(text))
    }

    fun badPasswordErrorIsDisplayed() {
        val badPasswordError = rule.activity.getString(R.string.bad_password_error)
        rule.onNodeWithText(badPasswordError)
            .assertIsDisplayed()
    }

    fun badPasswordErrorIsNotDisplayed() {
        val badPasswordError = rule.activity.getString(R.string.bad_password_error)
        rule.onNodeWithText(badPasswordError)
            .assertDoesNotExist()
    }

    fun passwordIs(text: String) {
        val passwordHint = rule.activity.getString(R.string.password_hint)
        rule.onNodeWithTag(passwordHint)
            .assert(hasText(text))
    }

    fun passwordIsVisible() {
        val togglePasswordVisiblity = rule.activity.getString(R.string.toggle_password_visibility)
        rule.onNodeWithTag(togglePasswordVisiblity)
            .assert(hasContentDescription(rule.activity.getString(R.string.hide_password)))
    }

    fun blockingSavingIsDisplayed() {
        val loading = rule.activity.getString(R.string.saving)
        rule.onNodeWithTag(loading)
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
