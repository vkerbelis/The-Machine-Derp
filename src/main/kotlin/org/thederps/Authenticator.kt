package org.thederps

import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
interface Authenticator {
    @Throws(DiscordException::class)
    fun login(client: IDiscordClient)
}