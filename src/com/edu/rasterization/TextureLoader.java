package com.edu.rasterization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureLoader {

    public TextureLoader(){}

    public static Texture glLoadTexture(final String res) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(res));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Texture(image);
    }
}
