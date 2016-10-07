package org.thederps.client

import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-07.
 */
interface ClientRetriever {
    fun getClient(): IDiscordClient
}
