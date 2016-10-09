package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import org.thederps.module.Module
import org.thederps.module.ModuleRetriever
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException
import java.util.*

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) : ModuleRetriever {
    private val log = LoggerFactory.getLogger(BotController::class.java)
    private lateinit var client: IDiscordClient

    fun setUp(authenticator: Authenticator): Boolean {
        try {
            client = clientRetriever.getClient()
            authenticator.login(client)
            return true
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
        return false
    }

    fun launchModule(module: Module) {
        client.moduleLoader.loadModule(module)
    }

    override fun getModules(): List<Module> {
        val modules = ArrayList<Module>()
        client.moduleLoader.loadedModules.forEach { module ->
            modules.add(module as Module)
        }
        return modules
    }
}