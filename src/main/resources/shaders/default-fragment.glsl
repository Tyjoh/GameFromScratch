#version 400 core

in vec4 fColor;
in vec2 fTexturePos;

out vec4 color;

uniform sampler2D uTexture;

void main() {
    color = texture(uTexture, fTexturePos) * fColor;
}