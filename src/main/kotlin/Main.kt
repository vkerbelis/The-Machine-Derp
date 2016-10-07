import client.DiscordClientCreator
import client.DiscordClientRetriever

/**
 * @author Vidmantas K. on 2016-10-07.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val clientCreator = DiscordClientCreator()
        val clientRetriever = DiscordClientRetriever(args, clientCreator)
        val authenticator = DiscordAuthenticator(clientRetriever)
        BotController(authenticator)
    }
}
