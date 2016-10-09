package org.thederps.client

import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * @author Vidmantas on 2016-10-09.
 */
class DiscordClientCreatorTest {
    @Test
    fun testWithToken_buildsNonNullDiscordClient() {
        val client = DiscordClientCreator().withToken("")

        assertNotNull("Client is null", client)
    }
}