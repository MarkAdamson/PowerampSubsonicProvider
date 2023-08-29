package com.markadamson83.powerampsubsonicprovider.addserver

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.markadamson83.powerampsubsonicprovider.AddServerActivity
import com.markadamson83.powerampsubsonicprovider.R

fun launchAddServerScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<AddServerActivity>, AddServerActivity>,
    block: AddServerRobot.() -> Unit
): AddServerRobot {
        return AddServerRobot(rule).apply(block)
}

class AddServerRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<AddServerActivity>, AddServerActivity>
) {
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
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<AddServerActivity>, AddServerActivity>
) {
    fun mainScreenIsPresent() {
        val servers = rule.activity.getString(R.string.servers)
        rule.onNodeWithText(servers)
            .assertIsDisplayed()
    }
}
