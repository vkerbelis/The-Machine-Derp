package org.thederps

import org.junit.Test
import org.mockito.Mockito.*
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
class BotControllerTest {
    @Test
    fun testLaunch_callsLoginAction() {
        val authenticator = mock(Authenticator::class.java)
        val controller = BotController(authenticator)

        controller.launch()

        verify(authenticator).login()
    }

    @Test
    fun testLaunch_doesNotCrashOnAuthenticatorException() {
        val authenticator = mock(Authenticator::class.java)
        val controller = BotController(authenticator)
        doThrow(DiscordException("")).`when`(authenticator).login()

        controller.launch()

        verify(authenticator).login()
    }
}