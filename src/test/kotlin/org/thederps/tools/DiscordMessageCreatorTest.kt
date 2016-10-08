package org.thederps.tools

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-08.
 */
class DiscordMessageCreatorTest {
    @Test
    fun testWith_returnsMessageBuilderInstance() {
        val messageBuilder = DiscordMessageCreator().with(Mockito.mock(IDiscordClient::class.java))

        Assert.assertNotNull("MessageBuilder is null", messageBuilder)
        Assert.assertTrue("Not MessageBuilder instance", messageBuilder is MessageBuilder)
    }
}