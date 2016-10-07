/**
 * @author Vidmantas K. on 2016-10-07.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val clientRetriever = DiscordClientRetriever(args)
        val authenticator = DiscordAuthenticator(clientRetriever)
        BotController(authenticator)
    }
}
