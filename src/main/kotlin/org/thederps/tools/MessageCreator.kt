package org.thederps.tools

import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-08.
 */
interface MessageCreator {
    fun with(client: IDiscordClient): MessageBuilder
}
