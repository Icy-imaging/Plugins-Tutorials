/*
 * Copyright 2010, 2011 Institut Pasteur.
 * 
 * This file is part of ICY.
 * 
 * ICY is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ICY is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ICY. If not, see <http://www.gnu.org/licenses/>.
 */
package plugins.tutorial;

import icy.plugin.PluginDescriptor;
import icy.plugin.PluginLauncher;
import icy.plugin.PluginLoader;
import icy.plugin.abstract_.PluginActionable;
import icy.system.thread.ThreadUtil;
import plugins.tutorial.gui.GuiBuildExample01;

/**
 * This tutorial shows how to start a plugin from a plugin. <br>
 * We use an invoke later to create the plugin in the graphic thread. Plugin which support starting
 * as thread don't need this.
 * 
 * @author Fabrice de Chaumont
 */
public class StartAPluginFromAPlugin extends PluginActionable
{
    @Override
    public void run()
    {

        System.out.println("Plugin list:");
        System.out.println("============");

        // we get all the PluginDescriptor from the PluginManager
        for (final PluginDescriptor pluginDescriptor : PluginLoader.getPlugins())
        {
            System.out.print(pluginDescriptor.getSimpleClassName()); // output the name of the
            // class.

            // This part of the example check for a match in the name of the class
            if (pluginDescriptor.getSimpleClassName().compareToIgnoreCase("PluginWithPanel") == 0)
            {
                System.out.print(" ==> Starting by looking at the name....");

                // Create a new Runnable which contain the proper launcher
                ThreadUtil.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        PluginLauncher.start(pluginDescriptor);
                    }
                });
            }

            // This part of the example check for a match in the name of the class
            if (pluginDescriptor.isInstanceOf(GuiBuildExample01.class))
            {
                System.out.print(" ==> Starting using instance of....");

                // Create a new Runnable which contain the proper launcher
                ThreadUtil.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        PluginLauncher.start(pluginDescriptor);
                    }
                });
            }

            System.out.println();
        }
    }
}
