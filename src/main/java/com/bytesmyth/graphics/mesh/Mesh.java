package com.bytesmyth.graphics.mesh;

import org.lwjgl.opengl.GL40;

public class Mesh {

    public static final int VERTEX_SIZE = 9;

    private final float[] vertices;
    private final int[] elements;
    private int vao;
    private int vbo;
    private int ebo;

    public Mesh(float[] vertices, int[] elements) {
        this.vertices = vertices;
        this.elements = elements;

        init();
    }

    private void init() {
        vao = GL40.glGenVertexArrays();
        GL40.glBindVertexArray(vao);

        vbo = GL40.glGenBuffers();
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, vbo);
        GL40.glBufferData(GL40.GL_ARRAY_BUFFER, vertices, GL40.GL_DYNAMIC_DRAW);

        ebo = GL40.glGenBuffers();
        GL40.glBindBuffer(GL40.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL40.glBufferData(GL40.GL_ELEMENT_ARRAY_BUFFER, elements, GL40.GL_STATIC_DRAW);

        GL40.glVertexAttribPointer(0, 3, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
        GL40.glVertexAttribPointer(1, 4, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 3 * Float.BYTES);
        GL40.glVertexAttribPointer(2, 2, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 7 * Float.BYTES);
    }

    public void draw() {
        GL40.glBindVertexArray(vao);
        GL40.glEnableVertexAttribArray(0);
        GL40.glEnableVertexAttribArray(1);
        GL40.glEnableVertexAttribArray(2);

        GL40.glDrawElements(GL40.GL_TRIANGLES, elements.length, GL40.GL_UNSIGNED_INT, 0);

        GL40.glDisableVertexAttribArray(2);
        GL40.glDisableVertexAttribArray(1);
        GL40.glDisableVertexAttribArray(0);
        GL40.glBindVertexArray(0);
    }

    public void update() {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] += 0.005f;
        }
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, vbo);
        GL40.glBufferData(GL40.GL_ARRAY_BUFFER, vertices, GL40.GL_DYNAMIC_DRAW);
    }
}
