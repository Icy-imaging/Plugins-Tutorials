package plugins.tutorial.basics;

import icy.gui.frame.IcyFrame;
import icy.gui.util.GuiUtil;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginImageAnalysis;

import java.awt.Dimension;

import javax.swing.JPanel;

public class TitledPluginInterface extends Plugin implements PluginImageAnalysis {

	@Override
	public void compute() {

		JPanel mainPanel = new JPanel();
		IcyFrame icyFrame = GuiUtil.generateTitleFrame("Tutorial", mainPanel , new Dimension(128,128), true, true ,true, true );
		
		icyFrame.addToMainDesktopPane();
		icyFrame.setVisible( true );
		icyFrame.center();
		
	}
	
}
