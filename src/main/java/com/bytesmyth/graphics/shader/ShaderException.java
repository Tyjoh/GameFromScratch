package com.bytesmyth.graphics.shader;

import org.lwjgl.opengl.GL40;

public class ShaderException extends RuntimeException {

    public ShaderException() {
    }

    public ShaderException(String message) {
        super(message);
    }

    public static ShaderException compileFailure(String path, String log) {
        return new ShaderException(String.format("Failed to compile '%s':\n%s", path, log));
    }

    public static ShaderException linkError(String log, String... paths) {
        return new ShaderException(String.format("Failed to link shaders '%s':\n%s", String.join(", ", paths), log));
    }
}
