package com.bytesmyth.resources;

import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.texture.Texture;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Assets {

    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, BitmapFont> fonts = new HashMap<>();

    public static Texture loadTexture(String path) {
        if (!textures.containsKey(path)) {
            textures.put(path, new Texture(path));
        }
        return textures.get(path);
    }

    public static BitmapFont loadFont(String name) {
        if (!fonts.containsKey(name)) {
            Texture texture = loadTexture("/font/" + name + ".png");
            String fontDescriptor = Assets.loadText("/font/" + name + ".fnt");
            fonts.put(name, new BitmapFont(texture, fontDescriptor));
        }
        return fonts.get(name);
    }

    public static String loadText(String path) {
        try {
            InputStream resourceAsStream = Assets.class.getResourceAsStream(path);

            if (resourceAsStream == null) {
                resourceAsStream = Files.newInputStream(Paths.get(path));
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String value = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            return value;
        } catch (IOException e) {
            throw new ResourceNotFoundException("Failed to load text file '" + path + "'", e);
        }
    }

    public static ByteBuffer loadBytes(String path) {
        try (InputStream resourceAsStream = Assets.class.getResourceAsStream(path)) {
            if(resourceAsStream == null) {
                throw new ResourceNotFoundException(path);
            }

            byte[] bytes = resourceAsStream.readAllBytes();
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bytes.length);
            byteBuffer.put(bytes);
            return byteBuffer.flip();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
