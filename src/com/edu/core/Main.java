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
import com.edu.rasterization.Model;
import com.edu.rasterization.Texture;
import com.edu.rasterization.TextureLoader;

import java.io.IOException;

/*
 * Supported features:
 *
 * Projection: orthographic projection, perspective  projection
 * Rotation : axis rotation (x rotation, y rotation, z rotation)
 * View : camera view, object view
 * Rasterization: wire-frame mesh , point mesh, fill mesh, depth-buffer
 * Shading : Flat Shading
 */
public class Main {

    private final static float LENGTH = 2.0f;
    public static void main(String[] args) throws IOException {
        Window window = new Window("[3D Software Renderer]",600,600);
        Context context = window.getContext();

//        Vertex3D v0 = new Vertex3D(160, 160.0f, 0.0f);
//        Vertex3D v1 = new Vertex3D(450.0f, 380.0f, 0.0f);
//        Vertex3D v2 = new Vertex3D(540f, 510.0f, 0.0f);
        Vertex3D v0 = new Vertex3D(160, 160.0f, 1.0f);
        Vertex3D v1 = new Vertex3D(450.0f, 380.0f, 1.0f);
        Vertex3D v2 = new Vertex3D(90f, 510.0f, 1.0f);
//        Vertex3D v0 = new Vertex3D(260, 160.0f, 1.0f);
//        Vertex3D v1 = new Vertex3D(450.0f, 480.0f, 1.0f);
//        Vertex3D v2 = new Vertex3D(90f, 260.0f, 1.0f);
//        Model terrain2 = new Model("./obj/terrain2.obj");
//        Model monkey = new Model("./obj/monkey0.obj");
//        Model head = new Model("./obj/face.obj");
//        Model tank = new Model("./obj/tank.obj");
//        Model teapot = new Model("./obj/teapot.obj");
        Model icosphere = new Model("./obj/icosphere.obj");
//        Model cow = new Model("./obj/cow-nonormals.obj");
//        Model dragon = new Model("./obj/testalduinsmall.obj");
//        Model demon = new Model("./obj/diablo3_pose.obj");
//        Model gun = new Model("./obj/Handgun_obj.obj");
//        Model cube = new Model("./obj/cube.obj");
        /* Very Important: Errors are cause by the z axis when the object goes beyond the zNear value. */
//        Matrix4f projection  = new Matrix4f().glGetPerspectiveMatrix((float)Math.toRadians(70.0f), context.getWidth()/context.getHeight(), 1.0f, 1000.0f);
        Matrix4f projection = new Matrix4f().glGetOrthographicMatrix(LENGTH, -LENGTH, LENGTH, -LENGTH, 1.0f, 100.0f);
//        /* Need to fix camera and model */
//        Matrix4f camera = new Matrix4f().glGetCameraMatrix(0.0f, 1.5f, 20.0f);
        Matrix4f camera = new Matrix4f().glLookAt(0.0f, 0.0f, -3.0f,0.0f, 0.0f, 0.0f,0.0f, 1.0f, 0.0f);
//        Matrix4f cowModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 15.0f);
//        Matrix4f dragonModel = new Matrix4f().glGetModelMatrix(0.0f, -0.5f, 10.0f);
//        Matrix4f terrain2Model = new Matrix4f().glGetModelMatrix(6.0f, 3.0f, 7.0f);
//        Matrix4f demonModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 1.0f);
//        Matrix4f gunModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 0.0f);
//        Matrix4f cubeModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, -100.0f);
//        Matrix4f teapotModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 5.0f);
        Matrix4f icoshepereModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 1.0f);
//        Matrix4f headModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 20.0f);
//        Matrix4f tankModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 20.0f);
//        Matrix4f monkeyModel = new Matrix4f().glGetModelMatrix(0.0f, 0.0f, 0.0f);

//        Texture texture = TextureLoader.glLoadTexture("./obj/face.jpeg");
        long previousTime = System.nanoTime();
        float rotCounter = 0.0f;
        while(true)
        {
            long currentTime = System.nanoTime();
            float delta = (float)((currentTime - previousTime)/1000000000.0);
            previousTime = currentTime;
            window.setTitle("[3D Software Renderer] Time : " + delta);
            rotCounter = (rotCounter + delta*0.4f )% 180;
            Matrix4f axisRotation = new Matrix4f().glGetRotation(rotCounter, rotCounter,0.0f);
            context.clear(0x0000);
            context.glDrawFillMesh(icosphere, projection.glMultiply(camera.glMultiply(icoshepereModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(teapot, projection.glMultiply(camera.glMultiply(teapotModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(cow, projection.glMultiply(camera.glMultiply(cowModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(head, projection.glMultiply(camera.glMultiply(headModel)).glMultiply(axisRotation),axisRotation);
//            context.glDrawFillMesh(tank, projection.glMultiply(camera.glMultiply(tankModel)).glMultiply(axisRotation),axisRotation);
//            context.glDrawFillMesh(dragon, projection.glMultiply(camera.glMultiply(dragonModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(demon, projection.glMultiply(camera.glMultiply(demonModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(cube, projection.glMultiply(camera.glMultiply(cubeModel)).glMultiply(axisRotation),axisRotation);
//            context.glDrawFillMesh(terrain2, projection.glMultiply(camera.glMultiply(terrain2Model)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(monkey, projection.glMultiply(camera.glMultiply(monkeyModel)).glMultiply(axisRotation), axisRotation);
//            context.glDrawFillMesh(gun, projection.glMultiply(camera.glMultiply(gunModel)).glMultiply(axisRotation), axisRotation);

//            context.glDrawWireFrameMesh(icosphere, projection.glMultiply(camera.glMultiply(icoshepereModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(teapot, projection.glMultiply(camera.glMultiply(teapotModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(cow, projection.glMultiply(camera.glMultiply(cowModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(dragon, projection.glMultiply(camera.glMultiply(dragonModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(monkey, projection.glMultiply(camera.glMultiply(mokeyModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(demon, projection.glMultiply(camera.glMultiply(demonModel)).glMultiply(axisRotation));
//            context.glDrawWireFrameMesh(head, projection.glMultiply(camera.glMultiply(headModel)).glMultiply(axisRotation));

//            context.glDrawPointMesh(cow, projection.glMultiply(camera.glMultiply(cowModel)).glMultiply(axisRotation));
//            context.glDrawPointMesh(dragon, projection.glMultiply(camera.glMultiply(dragonModel)).glMultiply(axisRotation));
//            context.glDrawPointMesh(monkey, projection.glMultiply(camera.glMultiply(mokeyModel)).glMultiply(axisRotation));
//            context.glDrawPointMesh(demon, projection.glMultiply(camera.glMultiply(demonModel)).glMultiply(axisRotation));

//            context.glTriangleRasterization(v0, v1, v2, 0xffffff);
            window.flush();
        }
    }
}
