/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator(args: Array<String>) : Authenticator {
    init {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Please enter email and password OR a token as arguments")
        }
    }
}