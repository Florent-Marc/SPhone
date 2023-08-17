package com.dev.sphone.mod.client;

import com.jme3.math.Vector3f;

public class Point {
    private Vector3f pos;

    public Point(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getPos() {
        return this.pos;
    }
}
