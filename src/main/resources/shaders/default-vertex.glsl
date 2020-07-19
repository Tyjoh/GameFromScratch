#version 400 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexturePos;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexturePos;

void main() {
    fColor = aColor;
    fTexturePos = aTexturePos;
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}