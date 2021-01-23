package com.bytesmyth.graphics.texture;

import com.bytesmyth.resources.ResourceLoadException;
import com.bytesmyth.resources.Assets;
import org.lwjgl.opengl.GL40;

import java.nio.ByteBuffer;

import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private String path;
    private int textureId;

    private int width;
    private int height;

    public Texture(String path) {
        this.path = path;
        init();
    }

    private void init() {
        textureId = GL40.glGenTextures();
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, textureId);
//        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
//        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
        GL40.glTexParameteri(GL40.GL_TEXTURE_2D, GL40.GL_TEXTURE_WRAP_S, GL40.GL_CLAMP_TO_EDGE);
        GL40.glTexParameteri(GL40.GL_TEXTURE_2D, GL40.GL_TEXTURE_WRAP_T, GL40.GL_CLAMP_TO_EDGE);

        GL40.glTexParameteri(GL40.GL_TEXTURE_2D, GL40.GL_TEXTURE_MIN_FILTER, GL40.GL_NEAREST);
        GL40.glTexParameteri(GL40.GL_TEXTURE_2D, GL40.GL_TEXTURE_MAG_FILTER, GL40.GL_NEAREST);

        ByteBuffer bytes = Assets.loadBytes(path);

        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];

        boolean success = stbi_info_from_memory(bytes, width, height, channels);
        if (!success) {
            throw new ResourceLoadException("Could not decode texture resource'" + path + "'");
        }

        ByteBuffer image = stbi_load_from_memory(bytes, width, height, channels, 0);
        if (image == null) {
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }

        this.width = width[0];
        this.height = height[0];

        GL40.glTexImage2D(GL40.GL_TEXTURE_2D, 0, GL40.GL_RGBA, this.width, this.height, 0, GL40.GL_RGBA, GL40.GL_UNSIGNED_BYTE, image);
    }

    public void bind() {
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return textureId;
    }
}
