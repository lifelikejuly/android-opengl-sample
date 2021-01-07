#version 300 es
precision highp float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
out vec4 fragColor;

uniform vec3                iResolution;
uniform float               iGlobalTime;

#define t iGlobalTime







void main()
{


    vec2 fragCoord = vTextureCoord;

    vec2 uv = fragCoord.xy;

    float amount = 0.0;

    amount = (1.0 + sin(iGlobalTime*6.0)) * 0.5;
    amount *= 1.0 + sin(iGlobalTime*16.0) * 0.5;
    amount *= 1.0 + sin(iGlobalTime*19.0) * 0.5;
    amount *= 1.0 + sin(iGlobalTime*27.0) * 0.5;
    amount = pow(amount, 3.0);

    amount *= 0.05;

    vec3 col;
    col.r = texture( sTexture, vec2(uv.x+amount,uv.y) ).r;
    col.g = texture( sTexture, uv ).g;
    col.b = texture( sTexture, vec2(uv.x-amount,uv.y) ).b;

    col *= (1.0 - amount * 0.5);

    fragColor = vec4(col,1.0);

}


