package plugins.tutorial.basics;

import icy.gui.frame.ActionFrame;
import icy.plugin.abstract_.PluginActionable;

import javax.swing.JPanel;

public class TitledPluginInterface extends PluginActionable
{
    @Override
    public void run()
    {
        ActionFrame frame = new ActionFrame("Tutorial", true, true);

        // get main panel to add component if desired...
        JPanel mainPanel = frame.getMainPanel();

        frame.addToMainDesktopPane();
        frame.setVisible(true);
        frame.center();
    }
}
