#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision mediump float;

in vec2 vTexCoord;
out vec4 outColor;
uniform samplerExternalOES sTexture;

void main(){
    outColor = texture(sTexture, vTexCoord);
}
