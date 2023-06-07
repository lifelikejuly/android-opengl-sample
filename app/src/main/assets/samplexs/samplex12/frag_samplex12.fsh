#version 300 es
precision highp float;


uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord;//接收从顶点着色器过来的参数
out vec4 fragColor;

vec2 tile_num = vec2(40.0,20.0);


void main()
{


    vec2 fragCoord = vTextureCoord.xy;

    vec2 uv = fragCoord.xy;
    vec2 uv2 = floor(uv*tile_num)/tile_num;
    uv -= uv2;
    uv *= tile_num;
    fragColor = texture(sTexture, uv2 + vec2(step(1.0-uv.y, uv.x)/(2.0*tile_num.x), step(uv.x, uv.y)/(2.0*tile_num.y)));

}


