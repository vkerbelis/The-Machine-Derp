import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-07.
 */
interface ClientCreator {
    fun withToken(token: String): IDiscordClient
}
