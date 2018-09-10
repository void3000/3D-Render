/**
 * Copyright (C) 2018 Bitsqwit - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Public license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the Public license with
 * this file. If not, please write to: keorapetse.finger@yahoo.com
 */
package com.edu.core;

public class Surface {
    public int width;
    public int height;
    public int offset;

    public byte[] pixels;

    private final int PIXEL_LENGTH = 3;

    public Surface(final int width, final int height) {
        this.width = width;
        this.height= height;
        pixels = new byte[width * height * PIXEL_LENGTH];
    }

    /**
     * @param x
     * @param y
     * @param r
     * @param g
     * @param b
     */
    public void glDrawPixel(int x, int y, byte r, byte g, byte b) {
        offset = (y * width + x) * PIXEL_LENGTH;
        if (offset >= pixels.length)
            return;
        pixels[offset    ] = b;
        pixels[offset + 1] = g;
        pixels[offset + 2] = r;
    }

    public void glDrawPixel(int x, int y, int color) {
        offset = (y * width + x) * PIXEL_LENGTH;
        if (offset >= pixels.length)
            return;
        pixels[offset    ] = (byte)((color >> 0) & 0xff);
        pixels[offset + 1] = (byte)((color >> 8) & 0xff);
        pixels[offset + 2] = (byte)((color >> 16)& 0xff);
    }

    /**
     * @param color
     */
    public void clear(int color) {
        for (int i = 0; i < pixels.length; i += PIXEL_LENGTH) {
            pixels[i    ] = (byte)((color >> 0) & 0xff);
            pixels[i + 1] = (byte)((color >> 8) & 0xff);
            pixels[i + 2] = (byte)((color >> 16)& 0xff);
        }
    }

    public int getWidth() { return width;   }
    public int getHeight(){ return height;  }

    /**
     * @param array
     */
    public void copy(byte[] array) {
        for (int i = 0; i < width * height; ++i) {
            array[PIXEL_LENGTH*i    ] = pixels[PIXEL_LENGTH*i    ];
            array[PIXEL_LENGTH*i + 1] = pixels[PIXEL_LENGTH*i + 1];
            array[PIXEL_LENGTH*i + 2] = pixels[PIXEL_LENGTH*i + 2];
        }
    }
}
