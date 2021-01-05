package com.bytesmyth.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class OrthographicCamera2D {

    private float left;
    private float right;
    private float top;
    private float bottom;
    private float near;
    private float far;

    private Vector2f position = new Vector2f();
    private Vector2f prevPosition = new Vector2f();
    private float alpha = 0;

    private Matrix4f projection = new Matrix4f();
    private Matrix4f view = new Matrix4f();

    private Matrix4f combined = new Matrix4f();
    private Matrix4f inverseCombined = new Matrix4f();

    public OrthographicCamera2D() {
        setCameraView(-1, 1, -1, 1, 0, 1);
    }

    public void prepare(float alpha) {
        this.alpha = alpha;
        updateMatrices();
    }

    private void updateMatrices() {
        Vector2f position = new Vector2f(this.prevPosition).lerp(this.position, alpha);

        projection.identity();
        projection.ortho(left, right, bottom, top, near, far);

        Vector3f eye = new Vector3f(position.x, position.y, 1);
        Vector3f target = new Vector3f(position.x, position.y, -1);
        Vector3f up = new Vector3f(0, 1, 0);

        view.identity();
        view.lookAt(eye, target, up);

        combined.set(projection).mul(view);
        inverseCombined.set(combined).invert();
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
        Vector2f position = new Vector2f(this.prevPosition).lerp(this.position, alpha);
        return new Vector4f(left + position.x, right + position.x, bottom + position.y, top + position.y);
    }

    public float getWidth() {
        return right - left;
    }

    public float getHeight() {
        return top - bottom;
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public Vector2f toCameraCoordinates(Vector2f screenCoordinates) {
        Vector4f mouseVec = new Vector4f(screenCoordinates.x, screenCoordinates.y, 0, 1);
        Vector4f out = mouseVec.mul(inverseCombined);
        return new Vector2f(out.x, out.y);
    }

    public void setCameraView(float left, float right, float bottom, float top, float near, float far) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.near = near;
        this.far = far;
        updateMatrices();
    }

    public void setAlpha(float dt) {
        this.alpha = dt;
        updateMatrices();
    }

    public void writePrevTransform() {
        this.prevPosition.set(this.position);
        updateMatrices();
    }
}
