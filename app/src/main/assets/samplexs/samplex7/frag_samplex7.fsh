#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
out vec4 fragColor;

void main()
{


    vec2 uv = vTextureCoord.xy;
    vec4 color = texture(sTexture, vTextureCoord);
    float gray = length(color.rgb);
    fragColor = vec4(vec3(step(0.06, length(vec2(dFdx(gray), dFdy(gray))))), 1.0);

}