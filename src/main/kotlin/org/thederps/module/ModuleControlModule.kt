package org.thederps.module

import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-09.
 */
class ModuleControlModule : Module {
    override val command = "!modules"

    override val description = "Shows, activates available or deactivates running modules"

    override fun getName() = "Module control"

    override fun enable(client: IDiscordClient) = true

    override fun getVersion() = "1.0"

    override fun getMinimumDiscord4JVersion() = "2.6.1"

    override fun getAuthor() = "Vidmantas K."

    override fun disable() {
        // Do nothing
    }
}
