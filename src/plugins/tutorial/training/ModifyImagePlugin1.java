package plugins.tutorial.training;

import icy.gui.dialog.MessageDialog;
import icy.image.IcyBufferedImage;
import icy.plugin.abstract_.PluginActionable;

public class ModifyImagePlugin1 extends PluginActionable
{
    @Override
    public void run()
    {
        IcyBufferedImage image = getActiveImage();

        // check if an image is opened
        if (image == null)
        {
            MessageDialog.showDialog("This plugin needs an opened image.");
            return;
        }

        // retrieve width and height of the image
        int w = image.getSizeX();
        int h = image.getSizeY();

        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                // set pixel intensity to half of its original value
                image.setData(x, y, 0, image.getData(x, y, 0) / 2);
            }
        }
    }
}
