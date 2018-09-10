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

import java.util.Objects;

public class Vertex3D {
    private Vector4f _3DSpacePosition;

    private final float WORLD_SPACE = 1.0f;

    /**
     * @param position
     */
    public Vertex3D(Vector4f position) {
        _3DSpacePosition = position;
    }

    /**
     * @param x
     * @param y
     * @param z
     */
    public Vertex3D(float x, float y, float z) {
        _3DSpacePosition = new Vector4f(x, y, z, WORLD_SPACE);
    }

    /**
     * Homogenous space to Coordinate space.
     *
     * @return
     */
    public Vertex3D glNormalize() {
        float w = _3DSpacePosition.getW();
        return new Vertex3D(new Vector4f(_3DSpacePosition.getX()/w, _3DSpacePosition.getY()/w, _3DSpacePosition.getZ()/w, _3DSpacePosition.getW()/w));
    }

    public Vertex3D transform(Matrix4f transform) {
        return new Vertex3D(transform.glTransform(_3DSpacePosition));
    }

    public Vector4f getVector() {
        return _3DSpacePosition;
    }

    public float getX() {
        return _3DSpacePosition.getX();
    }

    public float getY() {
        return _3DSpacePosition.getY();
    }

    public float getZ() {
        return _3DSpacePosition.getZ();
    }

    @Override
    public String toString() {
        return "Vertex3D{" +
                "Position=" + _3DSpacePosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex3D vertex3D = (Vertex3D) o;
        return Objects.equals(_3DSpacePosition, vertex3D._3DSpacePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_3DSpacePosition, WORLD_SPACE);
    }
}
