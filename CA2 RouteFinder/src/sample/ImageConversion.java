package sample;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

//this class converts the image into a usable form on import!
public class ImageConversion
{
    public Image convertBlackWhite(Image img)
    {
        PixelReader pr = img.getPixelReader();
        WritableImage wImg = new WritableImage(pr, (int)img.getWidth(), (int)img.getHeight());
        PixelWriter pw = wImg.getPixelWriter();

        for(int x = 0; x<(int)img.getWidth(); x++)
        {
            for(int y = 0; y < (int)img.getHeight(); y++)
            {
                int a =(pr.getArgb(x,y)>>24) & 0xFF;
                int r =(pr.getArgb(x,y)>>16) & 0xFF;
                int g = (pr.getArgb(x,y)>>8) & 0xFF;
                int b = (pr.getArgb(x,y)) & 0xFF;

                if(r > 220 && g > 220 && b > 220)
                {
                    pw.setArgb(x,y,a<<24|255<<16|255<<8|255);
                }
                else
                {
                    pw.setArgb(x,y,a<<24|0<<16|0<<8|0);
                }
            }
        }
        return wImg;
    }
}
