/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordClientRetriever(args: Array<String>) : ClientRetriever {
    init {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Please enter email and password OR a token as arguments")
        }
    }
}