#version 300 es
precision highp float;
uniform sampler2D sTexture;//纹理内容数据
uniform sampler2D sTexture2;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
out vec4 fragColor;

uniform vec3                iResolution;
uniform float               iGlobalTime;

#define t iGlobalTime







void main()
{


//    vec2 fragCoord = vTextureCoord;
//
//
//    vec2 uv = fragCoord.xy;
//
//    vec4 bump = texture(sTexture2, uv + iGlobalTime * 0.05);
//
//    vec2 vScale = vec2 (0.01, 0.01);
//    vec2 newUV = uv + bump.xy * vScale.xy;
//
//    vec4 col = texture(sTexture, newUV);
//
//    fragColor = vec4(col.xyz, 1.0);

    fragColor = texture(sTexture, vTextureCoord);

}


