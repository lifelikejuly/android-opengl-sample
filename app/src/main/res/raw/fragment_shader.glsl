#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision mediump float;

in vec2 v_texCoord;
out vec4 outColor;
uniform samplerExternalOES s_texture;

void main() {


    //灰
//    vec2 uv = v_texCoord.xy;
//    vec4 color = texture(s_texture, v_texCoord);
//    float gray = length(color.rgb);
//    outColor = vec4(vec3(step(0.06, length(vec2(dFdx(gray), dFdy(gray))))), 1.0);

    // 分屏
    vec2 pos2 = v_texCoord.xy;
    if(pos2.x < 0.5){
        pos2.x += 0.5;
    }
    if(pos2.x > 0.5){
        pos2.x -= 0.5;
    }
    outColor = texture(s_texture, pos2);

    // 灰度
//    vec4 color = texture(s_texture, v_texCoord);
//    outColor = vec4(color.r * 0.3f,color.g * 0.59f,color.b * 0.11f,1);


//    vec4 color3 = texture(s_texture, v_texCoord);
//    outColor = vec4(color3.g, color3.g, color3.g, 1.0);

    // 怀旧处理
//    vec4 color4 = texture(s_texture, v_texCoord);
//    float r4 = color4.r;
//    float g4 = color4.g;
//    float b4 = color4.b;
//
//    r4 = 0.393* r4 + 0.769 * g4 + 0.189* b4;
//    g4 = 0.349 * r4 + 0.686 * g4 + 0.168 * b4;
//    b4 = 0.272 * r4 + 0.534 * g4 + 0.131 * b4;
//    outColor = vec4(r4, g4, b4, 1.0);
}
