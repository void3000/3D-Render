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

import com.sun.istack.internal.Nullable;

import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;

/* Assuming Right-Hand system */
public class Matrix4f {
    private float m[][];
    private final int DIMENSION = 4;

    public Matrix4f() {
        m = new float[DIMENSION][DIMENSION];
    }

    public float[][] glGetMatrix() {
        return m;
    }

    /**
     * @param w
     * @return
     */
    public Matrix4f glMultiply(Matrix4f w) {
        Matrix4f n = new Matrix4f();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                n.m[i][j] = (m[i][0]*w.m[0][j] + m[i][1]*w.m[1][j] + m[i][2]*w.m[2][j] + m[i][3]*w.m[3][j]);
            }
        }
        return n;
    }

    public Matrix4f glGetIdentityMatrix() {
        m[0][0] = 1.0f;	m[0][1] = 0.0f;	m[0][2] = 0.0f;	m[0][3] = 0.0f;
        m[1][0] = 0.0f;	m[1][1] = 1.0f;	m[1][2] = 0.0f;	m[1][3] = 0.0f;
        m[2][0] = 0.0f;	m[2][1] = 0.0f;	m[2][2] = 1.0f;	m[2][3] = 0.0f;
        m[3][0] = 0.0f;	m[3][1] = 0.0f;	m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

    /**
     * @param xTopLeft
     * @param yTopLeft
     * @param width
     * @param height
     * @return
     */
    public Matrix4f glGetViewPortMatrix(float xTopLeft, float yTopLeft, float width, float height) {
        float halfWidth = width/2;
        float halfHeight= height/2;
        m[0][0] = halfWidth;m[0][1] = 0.0f;	        m[0][2] = 0.0f;	m[0][3] = xTopLeft + halfWidth;
        m[1][0] = 0.0f;	    m[1][1] = halfHeight;	m[1][2] = 0.0f;	m[1][3] = yTopLeft + halfHeight;
        m[2][0] = 0.0f;	    m[2][1] = 0.0f;	        m[2][2] = 0.5f;	m[2][3] = 0.5f;
        m[3][0] = 0.0f;	    m[3][1] = 0.0f;	        m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

    /**
     * @param vec
     * @return
     */
    public Vector4f glTransform(Vector4f vec) {
        return new Vector4f(
                m[0][0] * vec.getX() + m[0][1] * vec.getY() + m[0][2] * vec.getZ() + m[0][3] * vec.getW(),
                m[1][0] * vec.getX() + m[1][1] * vec.getY() + m[1][2] * vec.getZ() + m[1][3] * vec.getW(),
                m[2][0] * vec.getX() + m[2][1] * vec.getY() + m[2][2] * vec.getZ() + m[2][3] * vec.getW(),
                m[3][0] * vec.getX() + m[3][1] * vec.getY() + m[3][2] * vec.getZ() + m[3][3] * vec.getW()
        );
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f glGetCameraMatrix(float x, float y, float z) {
        m[0][0] = 1.0f;	m[0][1] = 0.0f;	m[0][2] = 0.0f;	m[0][3] = -x;
        m[1][0] = 0.0f;	m[1][1] = 1.0f;	m[1][2] = 0.0f;	m[1][3] = -y;
        m[2][0] = 0.0f;	m[2][1] = 0.0f;	m[2][2] = 1.0f;	m[2][3] = -z;
        m[3][0] = 0.0f;	m[3][1] = 0.0f;	m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

    /**
     * @param xEye
     * @param yEye
     * @param zEye
     * @param xAt
     * @param yAt
     * @param zAt
     * @param xUp
     * @param yUp
     * @param zUp
     * @return
     */
    public Matrix4f glLookAt(float xEye, float yEye, float zEye,
                             float xAt, float yAt,float zAt,
                             float xUp,float yUp,float zUp)
    {
        Vector4f eye = new Vector4f(xEye, yEye, zEye, 1.0f);
        Vector4f at = new Vector4f(xAt, yAt, zAt, 1.0f);
        Vector4f up = new Vector4f(xUp, yUp, zUp, 1.0f);

        Vector4f zc = at.glSub(eye).glNormalize();
        Vector4f xc = up.glCrossProduct(zc).glNormalize();
        Vector4f yc = zc.glCrossProduct(xc).glNormalize();

        m[0][0] = xc.getX();	m[0][1] = xc.getY();	m[0][2] = xc.getZ();	m[0][3] = -xc.glDotProduct(eye);
        m[1][0] = yc.getX();	m[1][1] = yc.getY();	m[1][2] = yc.getZ();	m[1][3] = -yc.glDotProduct(eye);
        m[2][0] = -zc.getX();	m[2][1] = -zc.getY();	m[2][2] = -zc.getZ();	m[2][3] = zc.glDotProduct(eye);
        m[3][0] = 0.0f;	m[3][1] = 0.0f;	m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f glGetModelMatrix(float x, float y, float z) {
        m[0][0] = 1.0f;	m[0][1] = 0.0f;	m[0][2] = 0.0f;	m[0][3] = x;
        m[1][0] = 0.0f;	m[1][1] = 1.0f;	m[1][2] = 0.0f;	m[1][3] = y;
        m[2][0] = 0.0f;	m[2][1] = 0.0f;	m[2][2] = 1.0f;	m[2][3] = z;
        m[3][0] = 0.0f;	m[3][1] = 0.0f;	m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

    /**
     * @param fov
     * @param aspect
     * @param zNear
     * @param zFar
     * @return
     */
    public Matrix4f glGetPerspectiveMatrix(float fov, float aspect, float zNear, float zFar) {
        float tanHalfFov = (float)Math.tan(fov/2);
        float zRange = zFar - zNear;

        m[0][0] = 1.0f /(tanHalfFov*aspect);	m[0][1] = 0.0f;				    m[0][2] = 0.0f;	m[0][3] = 0.0f;
        m[1][0] = 0.0f;						    m[1][1] = 1.0f/tanHalfFov;	    m[1][2] = 0.0f;	m[1][3] = 0.0f;
        m[2][0] = 0.0f;						    m[2][1] = 0.0f;					m[2][2] = (zNear+zFar)/zRange;	m[2][3] = 2*zFar*zNear/zRange;
        m[3][0] = 0.0f;						    m[3][1] = 0.0f;					m[3][2] = 1.0f;	m[3][3] = 0.0f;
        return this;
    }

    /**
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     * @return
     */
    public Matrix4f glGetOrthographicMatrix(float left, float right, float bottom, float top, float near, float far)
    {
        float width = right - left;
        float height= top - bottom;
        float depth = far - near;

        m[0][0] = 2/width;m[0][1] = 0;	m[0][2] = 0;	m[0][3] = -(right + left)/width;
        m[1][0] = 0;	m[1][1] = 2/height;m[1][2] = 0;	m[1][3] = -(top + bottom)/height;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = -2/depth;m[2][3] = (far + near)/depth;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
        return this;
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f glGetRotation(float x, float y, float z)
    {
        /* Multiplication Rule: RyRxRz */
        m[0][0] = (float)(Math.cos(y)*Math.cos(z) - Math.sin(x)*Math.sin(y)*Math.sin(z));
        m[0][1] = (float)(-Math.sin(z)*Math.cos(y) - Math.sin(x)*Math.sin(y)*Math.cos(z));
        m[0][2] = (float)(-Math.sin(y)*Math.cos(x));
        m[0][3] = 0.0f;

        m[1][0] = (float)(Math.sin(z)*Math.cos(x));
        m[1][1] = (float)(Math.cos(x)*Math.cos(z));
        m[1][2] = (float)(-Math.sin(x));
        m[1][3] = 0.0f;

        m[2][0] = (float)(Math.sin(y)*Math.cos(z) + Math.sin(x)*Math.sin(z)*Math.cos(y));
        m[2][1] = (float)(Math.cos(y)*Math.sin(x)*Math.cos(z)-Math.sin(y)*Math.sin(z));
        m[2][2] = (float)(Math.cos(x)*Math.cos(y));
        m[2][3] = 0.0f;

        m[3][0] = 0.0f;	m[3][1] = 0.0f;	m[3][2] = 0.0f;	m[3][3] = 1.0f;
        return this;
    }

}
