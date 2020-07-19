package com.bytesmyth.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class OrthographicCamera2D {

    private final float left;
    private final float right;
    private final float top;
    private final float bottom;
    private final float near;
    private final float far;

    private Vector2f position = new Vector2f();
    private Matrix4f projection = new Matrix4f();
    private Matrix4f view = new Matrix4f();

    public OrthographicCamera2D(float left, float right, float bottom, float top, float near, float far) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.near = near;
        this.far = far;
        updateMatrices();
    }

    private void updateMatrices() {
        projection.identity();
        projection.ortho(left, right, bottom, top, near, far);

        Vector3f eye = new Vector3f(position.x, position.y, 1);
        Vector3f target = new Vector3f(position.x, position.y, -1);
        Vector3f up = new Vector3f(0, 1, 0);

        view.identity();
        view.lookAt(eye, target, up);
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
        updateMatrices();
    }

    public void move(Vector2f delta) {
        this.position.add(delta);
        updateMatrices();
    }

    public Vector4f getViewBounds() {
        return new Vector4f(left + position.x, right + position.x, bottom + position.y, top + position.y);
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }
}
