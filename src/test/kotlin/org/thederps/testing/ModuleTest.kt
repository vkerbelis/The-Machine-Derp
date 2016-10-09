package org.thederps.testing

import org.junit.Assert
import org.junit.Test
import org.thederps.module.Module

/**
 * @author Vidmantas on 2016-10-09.
 */
abstract class ModuleTest<out M : Module> {
    abstract val module: M

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