#version 300 es
precision mediump float;

varying vec2                texCoord;
uniform sampler2D           iChannel0;

void main() {
    gl_FragColor = texture2D(iChannel0, texCoord);


    vec4 mask = texture2D(iChannel0, texCoord);
    float color = (mask.r + mask.g + mask.b) /3.0;
    vec4 tempColor =vec4(color, color, color,1.0);
    gl_FragColor = tempColor;
}