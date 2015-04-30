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
package plugins.tutorial.basics;

import icy.gui.frame.progress.AnnounceFrame;
import icy.plugin.abstract_.PluginActionable;

/**
 * This tutorial displays a simple message at the bottom of the screen, in a scrolling annonceFrame.
 * A class is an ICY plugin as it extends icy.plugin.abstract_.Plugin
 * It can then be visible in the menu if it implements icy.plugin.interface_.PluginImageAnalysis
 * 
 * @author Fabrice de Chaumont
 * @author Stephane Dallongeville
 */
public class HelloWorldPlugin extends PluginActionable
{
    // This method is called when user click on the plugin button.
    @Override
    public void run()
    {
        new AnnounceFrame("Hello World !");
    }
}
