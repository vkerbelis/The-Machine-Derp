package org.thederps.tools

import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-08.
 */
class DiscordMessageCreator : MessageCreator {
    override fun with(client: IDiscordClient): MessageBuilder {
        return MessageBuilder(client)
    }
}

