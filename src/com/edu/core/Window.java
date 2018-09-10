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

import com.edu.rasterization.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Window extends JFrame {

    private final Canvas    canvas;
    private final byte[]    pixels;
    private final Graphics  graphics;
    private final Context context;
    private final BufferedImage   bufferedImage;
    private final BufferStrategy  bufferStrategy;

    public Window(String title, final int width, final int height) {
        super(title);
        canvas = new Canvas();
        // Initialize the canvas
        Dimension size = new Dimension(width, height);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);
        canvas.setPreferredSize(size);

        add(canvas);

        // Resize the frame before packing it, this
        // will insure that both the width and height
        // won't be padded by 10 pixels.
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a context to draw on
        context = new Context(width, height);

        // initialize bufferedImage object
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        // This will act a buffer that we can draw directly to it
        pixels = ((DataBufferByte)(bufferedImage.getRaster().getDataBuffer())).getData();

        // Initialize the buffer strategy
        canvas.createBufferStrategy(1);
        bufferStrategy = canvas.getBufferStrategy();

        // Initialize graphics
        graphics = bufferStrategy.getDrawGraphics();
        bufferStrategy.show();
    }

    public void updateTitle(String text) {
        setTitle(text);
    }

    /**
     * It makes sure that pixels are drawn to the screen.
     * If we don't flush the window, no updates will be
     * done on the screen.
     */
    public void flush() {
        context.copy(pixels);
        graphics.drawImage( bufferedImage,
                            0,
                            0,
                            context.getWidth(),
                            context.getHeight(),
                            null);
        bufferStrategy.show();
    }

    /**
     * A function that returns the context.
     *
     * @return  Context type object
     */
    public Context getContext() { return context; }
}
