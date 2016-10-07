/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val args: Array<String>) {
    init {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Please enter email and password OR a token as arguments")
        }
    }
}