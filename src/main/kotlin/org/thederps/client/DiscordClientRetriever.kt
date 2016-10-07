package org.thederps.client

import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordClientRetriever(args: Array<String>, clientCreator: ClientCreator) : ClientRetriever {
    private var client: IDiscordClient

    init {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Please enter a token as an argument")
        } else {
            val token = args[0]
            client = clientCreator.withToken(token)
        }
    }

    override fun getClient() = client
}