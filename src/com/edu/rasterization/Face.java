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

import java.util.Objects;

public class Face {
    private int vertexIndex;
    private int textureIndex;
    private int normalIndex;

    public Face() {}

    /**
     * @param vertex
     * @param texture
     * @param normal
     */
    public Face(int vertex, int texture, int normal) {
        vertexIndex     = vertex;
        textureIndex    = texture;
        normalIndex     = normal;
    }

    /**
     * @param vertexIndex
     */
    public void setVertexIndex(int vertexIndex) {
        this.vertexIndex = vertexIndex;
    }

    /**
     * @param textureIndex
     */
    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    /**
     * @param normalIndex
     */
    public void setNormalIndex(int normalIndex) {
        this.normalIndex = normalIndex;
    }

    public int getVertexIndex() {
        return (vertexIndex - 1);
    }

    public int getTextureIndex() { return (textureIndex - 1); }

    public int getNormalIndex() {
        return (normalIndex - 1);
    }

    @Override
    public String toString() {
        return "Face{" +
                "vertexIndex=" + vertexIndex +
                ", textureIndex=" + textureIndex +
                ", normalIndex=" + normalIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        return vertexIndex == face.vertexIndex &&
                textureIndex == face.textureIndex &&
                normalIndex == face.normalIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexIndex, textureIndex, normalIndex);
    }
}
