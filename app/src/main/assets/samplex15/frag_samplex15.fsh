#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数

uniform float fProgress;

out vec4 fragColor;

void main()
{
    //进行纹理采样

    vec2 direction = vec2(1.0, 0.0); //vec2(1.0, 0.0)水平

    vec2 p = vTextureCoord.xy + fProgress * sign(direction);
    vec2 f = fract(p);

    fragColor = mix(
    texture(sTexture, f),
    texture(sTexture, f),
    step(0.0, p.y) * step(p.y, 1.0) * step(0.0, p.x) * step(p.x, 1.0)
    );

}