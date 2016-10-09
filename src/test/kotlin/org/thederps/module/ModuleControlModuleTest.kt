package org.thederps.module

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-09.
 */
class ModuleControlModuleTest {
    private lateinit var module: ModuleControlModule
    private lateinit var client: IDiscordClient

    @Before
    fun setUp() {
        client = mock(IDiscordClient::class.java)
        module = ModuleControlModule()
    }

    @Test
    fun testEnable_returnsTrue() {
        val enabled = module.enable(client)

        Assert.assertTrue("Disabled", enabled)
    }

    @Test
    fun testDisable() {
        module.disable()
    }

    @Test
    fun testGetCommand_empty() {
        Assert.assertTrue("Command empty", !module.command.isEmpty())
    }

    @Test
    fun testGetCommand_startsWithCommandPrefix() {
        Assert.assertTrue("Doesn't start with prefix", module.command.startsWith("!"))
    }

    @Test
    fun testGetDescription_notEmpty() {
        Assert.assertTrue("Description is empty", !module.description.isEmpty())
    }

    @Test
    fun testGetName_notEmpty() {
        Assert.assertTrue("Name is empty", !module.name.isEmpty())
    }

    @Test
    fun testGetVersion_notEmpty() {
        Assert.assertTrue("Version is empty", !module.version.isEmpty())
    }

    @Test
    fun testGetMinimumDiscord4JVersion_notEmpty() {
        Assert.assertTrue("Min version is empty", !module.minimumDiscord4JVersion.isEmpty())
    }

    @Test
    fun testGetAuthor_isMe() {
        Assert.assertTrue("Author is empty", !module.author.isEmpty())
        Assert.assertEquals("Author is not me", "Vidmantas K.", module.author)
    }
}