package com.markadamson83.powerampsubsonicprovider.servers

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.markadamson83.powerampsubsonicprovider.R

fun launchServersScreen(
    rule: MainActivityTestRule,
    block: ServersRobot.() -> Unit
): ServersRobot {
    return ServersRobot(rule).apply(block)
}

class ServersRobot(
    private val rule: MainActivityTestRule
) {
    infix fun verify(
        block: ServersVerification.() -> Unit
    ): ServersVerification {
        return ServersVerification(rule).apply(block)
    }

    fun clickNewServer() {
        val newServer = rule.activity.getString(R.string.new_server)
        rule.onNodeWithText(newServer)
            .assertHasClickAction()
            .performClick()
    }

    fun deleteServer(serverId: String) {
        val deleteServer = rule.activity.getString(R.string.delete_server) + "::" + serverId
        rule.onNodeWithTag(deleteServer)
            .assertHasClickAction()
            .performClick()
    }
}

class ServersVerification constructor(
    private val rule: MainActivityTestRule
) {
    fun addServerScreenIsDisplayed() {
        val addServer = rule.activity.getString(R.string.add_server)
        rule.onNodeWithText(addServer)
            .assertIsDisplayed()
    }

    fun noServersMessageIsDisplayed() {
        val noServersMessage = rule.activity.getString(R.string.no_servers)
        rule.onNodeWithText(noServersMessage)
            .assertIsDisplayed()
    }

    fun noServersMessageIsNotDisplayed() {
        val noServersMessage = rule.activity.getString(R.string.no_servers)
        rule.onAllNodesWithText(noServersMessage)
            .assertCountEquals(0)
    }

    fun serverIsDisplayed(serverName: String) {
        val server = rule.activity.getString(R.string.server)
        rule.onNodeWithTag(server)
            .assertIsDisplayed()
            .assert(hasText(serverName))
    }

    fun serverIsNotDisplayed(serverName: String) {
        val server = rule.activity.getString(R.string.server)
        rule.onAllNodesWithTag(server)
            .filter(hasText(serverName))
            .assertCountEquals(0)
    }

}
