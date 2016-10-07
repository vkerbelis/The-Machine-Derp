package org.thederps

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val authenticator: Authenticator) {
    fun launch() {
        authenticator.login()
    }
}