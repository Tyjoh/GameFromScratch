package com.bytesmyth.graphics.batch;

import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.shader.Shader;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL40;

public class QuadTextureBatcher {

    private static final int NUM_VERTICES = 4;
    private static final int VERTEX_SIZE = 9;
    private static final int INDICES_PER_ELEMENT = 6;

    private final int maxQuads = 2048;

    private final float[] vertexData;
    private final int[] elements;
    private int vao;
    private int vbo;
    private int ebo;

    private Shader shader;
    private Texture currentTexture;

    private int vertexIndex = 0;
    private int numQuads = 0;

    private Vector4f color = new Vector4f(1,1,1,1);
    private OrthographicCamera2D camera;

    public QuadTextureBatcher(OrthographicCamera2D camera) {
        this(camera, new Shader("default"));
    }

    public QuadTextureBatcher(OrthographicCamera2D camera, Shader shader) {
        this.shader = shader;
        this.camera = camera;
        vertexData = new float[maxQuads * NUM_VERTICES * VERTEX_SIZE];
        elements = new int[maxQuads * INDICES_PER_ELEMENT];

        //Pre calculate indices since we always draw quads.
        int offset = 0;
        for (int i = 0; i < maxQuads; i++) {
            int n = i * NUM_VERTICES;
            elements[offset++] = n + 2;
            elements[offset++] = n + 1;
            elements[offset++] = n;

            elements[offset++] = n;
            elements[offset++] = n + 3;
            elements[offset++] = n + 2;
        }

        init();
    }

    private void init() {
        vao = GL40.glGenVertexArrays();
        GL40.glBindVertexArray(vao);

        vbo = GL40.glGenBuffers();
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, vbo);
        GL40.glBufferData(GL40.GL_ARRAY_BUFFER, vertexData, GL40.GL_DYNAMIC_DRAW);

        ebo = GL40.glGenBuffers();
        GL40.glBindBuffer(GL40.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL40.glBufferData(GL40.GL_ELEMENT_ARRAY_BUFFER, elements, GL40.GL_STATIC_DRAW);

        GL40.glVertexAttribPointer(0, 3, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
        GL40.glVertexAttribPointer(1, 4, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 3 * Float.BYTES);
        GL40.glVertexAttribPointer(2, 2, GL40.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 7 * Float.BYTES);
    }

    public void begin(Texture texture) {
        if (currentTexture != null) {
            if (currentTexture.getId() == texture.getId()) {
                return;
            } else {
                throw new IllegalStateException("Must call end() before begin() when using a different texture");
            }
        }

        currentTexture = texture;

        shader.bind();

        shader.setMat4("uProjection", camera.getProjection());
        shader.setMat4("uView", camera.getView());
        shader.setTextureSlot("uTexture", 0);

        GL40.glActiveTexture(GL40.GL_TEXTURE0);
        currentTexture.bind();
    }

    public void end() {
        if (numQuads > 0) {
            flush();
        }

        currentTexture.unbind();
        shader.unbind();

        currentTexture = null;
        color.set(1,1,1,1);
    }

    private void flush() {
        GL40.glBindVertexArray(vao);

        //push new vertex data
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, vbo);
        GL40.glBufferSubData(GL40.GL_ARRAY_BUFFER, 0, vertexData);

        //draw
        GL40.glEnableVertexAttribArray(0);
        GL40.glEnableVertexAttribArray(1);
        GL40.glEnableVertexAttribArray(2);

        GL40.glDrawElements(GL40.GL_TRIANGLES, numQuads * INDICES_PER_ELEMENT, GL40.GL_UNSIGNED_INT, 0);

        GL40.glDisableVertexAttribArray(2);
        GL40.glDisableVertexAttribArray(1);
        GL40.glDisableVertexAttribArray(0);
        GL40.glBindVertexArray(0);

        //reset
        vertexIndex = 0;
        numQuads = 0;
    }

    public void draw(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2) {
        vertex(x1, y1, u1, v1);
        vertex(x2, y1, u2, v1);
        vertex(x2, y2, u2, v2);
        vertex(x1, y2, u1, v2);

        numQuads++;
        if(numQuads >= maxQuads) {
            flush();
        }
    }

    public void draw(float x1, float y1, float x2, float y2, TextureRegion region) {
        draw(x1, y1, x2, y2, region.getU1(), region.getV1(), region.getU2(), region.getV2());
    }

    @Deprecated
    /*
     * Usages of this method should be removed and replaced by the other draw functions.
     */
    public void draw(Rectangle rectangle, TextureRegion region, Vector2f translation) {
        float topY = rectangle.getTopLeft().y + translation.y;
        float leftX = rectangle.getTopLeft().x + translation.x;
        float bottomY = rectangle.getBottomRight().y + translation.y;
        float rightX = rectangle.getBottomRight().x + translation.x;

        draw(leftX, topY, rightX, bottomY, region);
    }

    private void vertex(float x, float y, float u, float v) {
        vertexData[vertexIndex++] = x;
        vertexData[vertexIndex++] = y;
        vertexData[vertexIndex++] = 0f;//TODO: take in layer / z order

        vertexData[vertexIndex++] = color.get(0);
        vertexData[vertexIndex++] = color.get(1);
        vertexData[vertexIndex++] = color.get(2);
        vertexData[vertexIndex++] = color.get(3);

        vertexData[vertexIndex++] = u;
        vertexData[vertexIndex++] = v;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }
}
