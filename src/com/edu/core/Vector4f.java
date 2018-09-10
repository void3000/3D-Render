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

public class Vector4f {
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    /**
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public Vector4f(final float x, final float y, final float z, final float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * @param vec
     * @return
     */
    public Vector4f glAdd(Vector4f vec) {
        return new Vector4f(x + vec.getX(), y + vec.getY(), z + vec.getZ(), w);
    }

    /**
     * @param lambda
     * @return
     */
    public Vector4f glAdd(float lambda) {
        return new Vector4f(x + lambda, y + lambda, z + lambda, w);
    }

    /**
     * @param vec
     * @return
     */
    public Vector4f glSub(Vector4f vec) {
        return new Vector4f(x - vec.getX(), y - vec.getY(), z - vec.getZ(), w);
    }

    public Vector4f glDiv(float lambda) {
        return new Vector4f(x /lambda, y / lambda, z / lambda, w);
    }
    /**
     * @param vec
     * @returns
     */
    public Vector4f glDiv(Vector4f vec) {
        return new Vector4f(x / vec.getX(), y / vec.getY(), z / vec.getZ(), w);
    }
    /**
     * @param vec
     * @return
     */
    public float glDotProduct(Vector4f vec) {
        return (x * vec.getX() + y * vec.getY() + z * vec.getZ());
    }

    /**
     * @param lambda
     * @return
     */
    public float glDotProduct(float lambda) {
        return (x * lambda + y * lambda + z * lambda);
    }

    /**
     * @param vec
     * @return
     */
    public Vector4f glCrossProduct(Vector4f vec) {
        return new Vector4f((y*vec.getZ() - z*vec.getY()), (z*vec.getX() - x*vec.getZ()), (x*vec.getY() - y*vec.getX()), w);
    }

    public Vector4f glNormalize() {
        float norm = glGetNorm();
        if (norm == 0.0) {
            norm = 1.0f;
        }
        return new Vector4f(x/norm, y/norm, z/norm, w/w);
    }

    public float glGetNorm() {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public Vector4f glScale(float factor) {
        return new Vector4f(factor * x, factor * y, factor * z, w);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Vector4f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector4f vector4f = (Vector4f) o;
        return Float.compare(vector4f.x, x) == 0 &&
                Float.compare(vector4f.y, y) == 0 &&
                Float.compare(vector4f.z, z) == 0 &&
                Float.compare(vector4f.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
