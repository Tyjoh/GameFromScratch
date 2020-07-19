package com.bytesmyth.resources;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

public class Resources {

    public static String loadText(String path) {
        try {
            InputStream resourceAsStream = Resources.class.getResourceAsStream(path);

            if(resourceAsStream == null) {
                throw new ResourceNotFoundException(path);
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
        try (InputStream resourceAsStream = Resources.class.getResourceAsStream(path)) {
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
