package org.thederps

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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
}