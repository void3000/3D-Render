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

import com.edu.core.Vector4f;
import com.edu.core.Vertex3D;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Vertex3D>  vertices;
    private List<Vertex3D>  normals;
    private List<Vertex3D>  textures;
    private List<Face>      faces;

    {
        vertices    = new ArrayList<>();
        normals     = new ArrayList<>();
        textures    = new ArrayList<>();
        faces       = new ArrayList<>();
    }

    public List<Vertex3D> getVertices() {
        return vertices;
    }

    public List<Vertex3D> getNormals() {
        return normals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    /**
     * @param filename
     * @throws IOException
     */
    public Model(final String filename) throws IOException {
        BufferedReader modelBufferedReader = new BufferedReader(new FileReader(filename));
        String lineBuffer;
        while((lineBuffer = modelBufferedReader.readLine()) != null) {

            String[] tokens = lineBuffer.split(" ");
            if (tokens.length == 0 || tokens[0].equals("#")) {
                continue;
            }

            if (tokens[0].equals("v")) {
                int j = 0;
                if (tokens[1].equals("")) {
                    j = 1;
                }
                vertices.add(new Vertex3D(new Vector4f(
                        Float.valueOf(tokens[1 + j]),
                        Float.valueOf(tokens[2 + j]),
                        Float.valueOf(tokens[3 + j]),
                        1.0f
                )));
            }else if (tokens[0].equals("vn")) {
                int j = 0;
                if (tokens[1].equals("")) {
                    j = 1;
                }
                normals.add(new Vertex3D(new Vector4f(
                        Float.valueOf(tokens[1 + j]),
                        Float.valueOf(tokens[2 + j]),
                        Float.valueOf(tokens[3 + j]),
                        1.0f
                )));
            }else if (tokens[0].equals("vt")) {
                int j = 0;
                if (tokens[1].equals("")) {
                    j = 1;
                }
                float vt = 0.0f;
                if (tokens.length > 3) {
                    vt = Float.valueOf(tokens[3 + j]);
                }
                textures.add(new Vertex3D(new Vector4f(
                            Float.valueOf(tokens[1 + j]),
                            Float.valueOf(tokens[2 + j]),
                            vt,
                            1.0f
                    )));
            }else if (tokens[0].equals("f")) {
                int j = 0;
                if (tokens[1].equals("")) {
                    j = 1;
                }
                for (int m = 0; m < tokens.length - 3 - j; ++m) {
                    faces.add(parseFace(tokens[1 + j + m]));
                    faces.add(parseFace(tokens[2 + j +m]));
                    faces.add(parseFace(tokens[3 + j +m]));
                }
            }
        }
//        System.out.println(vertices);
//        System.out.println(faces);
    }

    public Face parseFace(String buffer) {
        String[] tokens = buffer.split("/");
        Face face       = new Face();

        face.setVertexIndex(Integer.parseInt(tokens[0]));
        if (tokens.length > 1) {
            face.setTextureIndex(Integer.parseInt(tokens[1]));
        }
        if (tokens.length > 2) {
            face.setNormalIndex(Integer.parseInt(tokens[2]));
        }

        return face;
    }
}
