/**
 * @author Vidmantas K. on 2016-10-07.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val authenticator = DiscordAuthenticator(args)
        BotController(authenticator)
    }
}
