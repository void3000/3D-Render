package com.edu.rasterization;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Texture {
    private BufferedImage bufferedImage;
    private byte[] pixels;
    private final int PIXEL_LENGTH = 3;
    private int width;
    private int height;

    public Texture(BufferedImage image) {
        bufferedImage = image;
        width = bufferedImage.getWidth();
        height= bufferedImage.getHeight();
        pixels = ((DataBufferByte)(bufferedImage.getRaster().getDataBuffer())).getData();
    }

    public byte[] glGetPixels() {
        return pixels;
    }

    /**
     * @param tu
     * @param tv
     * @return
     */
    public int glGetMap(float tu, float tv) {
        float u = Math.abs((tu * width)% width);
        float v = Math.abs((tv * height)% height);

        int offset = (int)(u + v * width) * PIXEL_LENGTH;
        byte b = pixels[0         ];
        byte g = pixels[1 + offset];
        byte r = pixels[2 + offset];

        return (((r << 16) | ( g << 8) | b) & 0xffffff);
    }

    public void copy(byte[] array) {
        for (int i = 0; i < width * height; ++i) {
            array[PIXEL_LENGTH*i    ] = pixels[PIXEL_LENGTH*i    ];
            array[PIXEL_LENGTH*i + 1] = pixels[PIXEL_LENGTH*i + 1];
            array[PIXEL_LENGTH*i + 2] = pixels[PIXEL_LENGTH*i + 2];
        }
    }
}
