/**
 * Copyright (C) 2018 Bitsqwit - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Public license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the Public license with
 * this file. If not, please write to: keorapetse.finger@yahoo.com
 */
package com.edu.rasterization;

import com.edu.core.Matrix4f;
import com.edu.core.Surface;
import com.edu.core.Vector4f;
import com.edu.core.Vertex3D;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * Rendering pipeline: Modeling -> ModelView [Transformation(translation, rotation, etc.)] -> Projection ->
 * Homogenous space to Coordinate space(Normalize: divide by w) -> ViewPort(screen) Transformation -> Rendering
 */
public class Context extends Surface {
    private static float depthBuffer[];
    private Random rand;
    public Vertex3D lightSource;
    public final float Id = 0.93f;
    public final float Kd = 0.23f;
    public final float Ia = 0.80f;
    public final float Ka = 0.09f;
    public final int COLOR_R = 0xff;
    public final int COLOR_G = 0xff;
    public final int COLOR_B = 0xff;
    /**
     * @param width
     * @param height
     */
    public Context(final int width, final int height) {
        super(width, height);
        depthBuffer = new float[width * height];
        lightSource = new Vertex3D(-1.5f,3.0f, 3.5f);
    }

    /**
     * @param model
     * @param transform
     */
    public void glDrawFillMesh(Model model, Matrix4f transform, Matrix4f rotation) {
        List<Vertex3D> vertices = model.getVertices();
        List<Vertex3D> normals  = model.getNormals();
        List<Face> faces        = model.getFaces();
        int nFaces              = faces.size();

        Vector4f n = null;

        rand = new Random();
        rand.setSeed(0);

        Arrays.fill(depthBuffer, Float.MAX_VALUE);
        /* Very Important! */
        Matrix4f viewPort = new Matrix4f().glGetViewPortMatrix(0.0f, 0.0f,width, height);
        for (int j = 0; j < nFaces; j += 3) {
            Vertex3D v0 = vertices.get(faces.get(j + 0).getVertexIndex()).transform(transform).glNormalize();
            Vertex3D v1 = vertices.get(faces.get(j + 1).getVertexIndex()).transform(transform).glNormalize();
            Vertex3D v2 = vertices.get(faces.get(j + 2).getVertexIndex()).transform(transform).glNormalize();

            if (normals.size() == 0) {
                n = v1.getVector().glSub(v2.getVector()).glCrossProduct(v2.getVector().glSub(v0.getVector()));
            }else {
                Vertex3D n0 = normals.get(faces.get(j + 0).getNormalIndex()).transform(rotation).glNormalize();
                Vertex3D n1 = normals.get(faces.get(j + 1).getNormalIndex()).transform(rotation).glNormalize();
                Vertex3D n2 = normals.get(faces.get(j + 2).getNormalIndex()).transform(rotation).glNormalize();
                n = n0.getVector().glAdd(n1.getVector()).glAdd(n2.getVector()).glDiv(3);
            }

            n = n.glNormalize();
            float ambient = Ia * Ka;
            float diffuse = n.glDotProduct(lightSource.getVector())* Id * Kd;
            float Ir = Math.min(1.0f, Math.max(0.0f,ambient + diffuse));

            glTriangleRasterization(v0.transform(viewPort), v1.transform(viewPort), v2.transform(viewPort), ((int)(Ir*COLOR_R) << 16 | (int)(Ir*COLOR_G) << 8 | (int)(Ir*COLOR_B) << 0));

        }
    }

    public void glFillTriangle(Vertex3D v0, Vertex3D v1, Vertex3D v2, int color) {

    }
    /**
     * @param v0
     * @param v1
     * @param v2
     * @param color
     */
    public void glTriangleRasterization(Vertex3D v0, Vertex3D v1, Vertex3D v2, int color) {
        int minY, minX, maxY, maxX;
        /* !Note: we assume vertices are in a sorted order. */
        Vertex3D minYVert = v0;
        Vertex3D midYVert = v1;
        Vertex3D maxYVert = v2;

        minY = (int)minYVert.getY();
        maxY = (int)minYVert.getY();

        minX = (int)minYVert.getX();
        maxX = (int)minYVert.getX();

        if (minY > midYVert.getY()) {
            minY = (int)midYVert.getY();
        }
        if (minY > maxYVert.getY()) {
            minY = (int)maxYVert.getY();
        }

        if (maxY < midYVert.getY()) {
            maxY = (int)midYVert.getY();
        }
        if (maxY < maxYVert.getY()) {
            maxY = (int)maxYVert.getY();
        }

        if (minX > midYVert.getX()) {
            minX = (int)midYVert.getX();
        }
        if (minX > maxYVert.getX()) {
            minX = (int)maxYVert.getX();
        }

        if (maxX < midYVert.getX()) {
            maxX = (int)midYVert.getX();
        }
        if (maxX < maxYVert.getX()) {
            maxX = (int)maxYVert.getX();
        }

        /* Note: Change in the future. */
        float area = glEdgeFunction(minYVert , midYVert, maxYVert);
        for (int y = minY; y <= maxY; ++y) {
            for (int x = minX; x <= maxX; ++x) {
                Vertex3D p = new Vertex3D(x, y, 1.0f);
                float c1 = glEdgeFunction(midYVert, maxYVert, p);
                float c2 = glEdgeFunction(maxYVert, minYVert, p);
                float c0 = glEdgeFunction(minYVert, midYVert, p);
                if ( c0 > 0.0f &&  c1 > 0.0f &&  c2 > 0.0f && area > 0.0f) {
                    c0 /= area;
                    c1 /= area;
                    c2 /= area;
                    float z = 1/((1-c1-c2)*minYVert.getZ() + c1*midYVert.getZ() + c2 * maxYVert.getZ());
                    if (z < depthBuffer[width * y + x]) {
                        depthBuffer[width * y + x] = z;
                        glDrawPixel(x, y, color);
                    }
                }
            }
        }
    }

    /**
     * @param v0
     * @param v1
     * @param p
     * @return
     */
    public float glEdgeFunction(Vertex3D v0, Vertex3D v1, Vertex3D p) {
        return -((p.getX() - v0.getX())*(v1.getY() - v0.getY()) - (p.getY() - v0.getY())*(v1.getX() - v0.getX()));
    }

    /**
     * @param model
     * @param transform
     */
    public void glDrawWireFrameMesh(Model model, Matrix4f transform) {
        List<Vertex3D> vertices = model.getVertices();
        List<Face> faces        = model.getFaces();
        int nFaces              = faces.size();
        /* Very Important! */
        Matrix4f viewPort = new Matrix4f().glGetViewPortMatrix(0.0f, 0.0f,width, height);
        for (int j = 0; j < nFaces; j += 3) {
            Vertex3D v0 = vertices.get(faces.get(j + 0).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            Vertex3D v1 = vertices.get(faces.get(j + 1).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            Vertex3D v2 = vertices.get(faces.get(j + 2).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            glDrawTriangle(v0, v1, v2, 0x000000);
//            System.out.println("Triangle{" + v0 + ", " + v1 + ", " + v2 +"}");
        }
    }

    /**
     * @param model
     * @param transform
     */
    public void glDrawPointMesh(Model model, Matrix4f transform) {
        List<Vertex3D> vertices = model.getVertices();
        List<Face> faces        = model.getFaces();
        int nFaces              = faces.size();
        /* Very Important! */
        Matrix4f viewPort = new Matrix4f().glGetViewPortMatrix(0.0f, 0.0f,width, height);
        for (int j = 0; j < nFaces; j += 3) {
            Vertex3D v0 = vertices.get(faces.get(j + 0).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            Vertex3D v1 = vertices.get(faces.get(j + 1).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            Vertex3D v2 = vertices.get(faces.get(j + 2).getVertexIndex()).transform(transform).glNormalize().transform(viewPort);
            glDrawPointTriangle(v0, v1, v2, 0x000000);
//            System.out.println("Triangle{" + v0 + ", " + v1 + ", " + v2 +"}");
        }
    }

    /**
     * @param v0
     * @param v1
     * @param v2
     * @param color
     */
    public void glDrawPointTriangle(Vertex3D v0, Vertex3D v1, Vertex3D v2, int color) {
        glDrawPixel((int)v0.getX(), (int)v0.getY(), color);
        glDrawPixel((int)v1.getX(), (int)v1.getY(), color);
        glDrawPixel((int)v2.getX(), (int)v2.getY(), color);
    }
    /**
     * @param v0
     * @param v1
     * @param v2
     * @param color
     */
    public void glDrawTriangle(Vertex3D v0, Vertex3D v1, Vertex3D v2, int color) {
        glDrawLine((int)v0.getX(), (int)v0.getY(), (int)v1.getX(),(int)v1.getY(), color   );
        glDrawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY(), color  );
        glDrawLine((int)v2.getX(), (int)v2.getY(), (int)v0.getX(), (int)v0.getY(), color  );
    }
    /**
     * @param v1
     * @param v2
     * @param color
     */
    public void glDrawLine(Vertex3D v1, Vertex3D v2, int color) {
        glBresenhamLineDraw((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY(), color);
    }

    /**
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param r
     * @param g
     * @param b
     */
    public void glDrawLine(int x0, int y0, int x1, int y1, int r, int g, int b) {
        glBresenhamLineDraw(x0, y0, x1, y1, r, g, b);
    }

    /**
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param color
     */
    public void glDrawLine(int x0, int y0, int x1, int y1, int color) {
        glBresenhamLineDraw(x0, y0, x1, y1, color);
    }

    /**
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param r
     * @param g
     * @param b
     */
    public void glBresenhamLineDraw(int x0, int y0, int x1, int y1, int r, int g, int b) {
        int color = (r << 16 | g << 8 | b);
        glBresenhamLineDraw(x0, y0, x1, y1, color);
    }

    /**
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param color
     */
    public void glBresenhamLineDraw(int x0, int y0, int x1, int y1, int color) {
        int dx, ax, sgnx, dy, ay, sgny, x, y, d;
        dx = x1 - x0;
        dy = y1 - y0;
        ax = Math.abs(dx) * 2;
        ay = Math.abs(dy) * 2;
        sgnx = dx < 0 ? -1 : 1;
        sgny = dy < 0 ? -1 : 1;
        x = x0;
        y = y0;
        if (ax > ay) {
            d = ay - ax/2;
            while(true) {
                glDrawPixel(x, y, color);
                if (x == x1) {
                    break;
                }
                if (d >= 0) {
                    y = y + sgny;
                    d = d - ax;
                }
                x = x + sgnx;
                d = d + ay;
            }
        }else {
            d = ax - ay/2;
            while(true) {
                glDrawPixel(x, y, color);
                if (y == y1) {
                    break;
                }
                if (d >= 0) {
                    x = x + sgnx;
                    d = d - ay;
                }
                y = y + sgny;
                d = d + ax;
            }
        }
    }
}
