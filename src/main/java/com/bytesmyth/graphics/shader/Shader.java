package com.bytesmyth.graphics.shader;

import com.bytesmyth.resources.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL40;

public class Shader {

    private String vertexShaderPath;
    private String fragmentShaderPath;

    private int vertexShaderId;
    private int fragmentShaderId;

    private int programId;

    public Shader(String vertexShaderPath, String fragmentShaderPath) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;

        load();
        link();
    }

    public Shader(String name) {
        this("/shaders/" + name + "-vertex.glsl", "/shaders/" + name + "-fragment.glsl");
    }

    private void load() {
        vertexShaderId = loadShader(vertexShaderPath, GL40.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentShaderPath, GL40.GL_FRAGMENT_SHADER);
    }

    private void link() {
        programId = GL40.glCreateProgram();
        GL40.glAttachShader(programId, vertexShaderId);
        GL40.glAttachShader(programId, fragmentShaderId);
        GL40.glLinkProgram(programId);

        int success = GL40.glGetProgrami(programId, GL40.GL_LINK_STATUS);
        if (success == GL40.GL_FALSE) {
            String log = GL40.glGetProgramInfoLog(programId);
            throw ShaderException.linkError(log, vertexShaderPath, fragmentShaderPath);
        }
    }

    private static int loadShader(String sourcePath, int shaderType) {
        String source = Resources.loadText(sourcePath);
        int shaderId = GL40.glCreateShader(shaderType);
        GL40.glShaderSource(shaderId, source);
        GL40.glCompileShader(shaderId);

        int success = GL40.glGetShaderi(shaderId, GL40.GL_COMPILE_STATUS);
        if (success == GL40.GL_FALSE) {
            String log = GL40.glGetShaderInfoLog(shaderId);
            throw ShaderException.compileFailure(sourcePath, log);
        }

        return shaderId;
    }

    public void bind() {
        GL40.glUseProgram(programId);
    }

    public void close() {
        GL40.glDeleteProgram(programId);
        GL40.glDeleteShader(vertexShaderId);
        GL40.glDeleteShader(fragmentShaderId);
    }

    public void unbind() {
        GL40.glUseProgram(0);
    }

    public void setMat4(String name, Matrix4f matrix4f) {
        int uniformLocation = GL40.glGetUniformLocation(programId, name);
        GL40.glUniformMatrix4fv(uniformLocation, false, matrix4f.get(new float[16]));
    }

    public void setTextureSlot(String name, int slot) {
        int uniformLocation = GL40.glGetUniformLocation(programId, name);
        GL40.glUniform1i(uniformLocation, slot);
    }

    public void setVector4f(String name, Vector4f vec) {
        int uniformLocation = GL40.glGetUniformLocation(programId, name);
        GL40.glUniform4f(uniformLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void setVector2f(String name, Vector2f vec) {
        int uniformLocation = GL40.glGetUniformLocation(programId, name);
        GL40.glUniform2f(uniformLocation, vec.x, vec.y);
    }

    public void setFloat(String name, float f) {
        int uniformLocation = GL40.glGetUniformLocation(programId, name);
        GL40.glUniform1f(uniformLocation, f);
    }

}
