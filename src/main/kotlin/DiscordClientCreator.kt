import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordClientCreator : ClientCreator {
    override fun withToken(token: String): IDiscordClient {
        return ClientBuilder().withToken(token).build()
    }
}
