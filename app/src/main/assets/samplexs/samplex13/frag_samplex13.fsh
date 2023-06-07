#version 300 es
precision highp float;


uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord;//接收从顶点着色器过来的参数
out vec4 fragColor;
uniform vec3  iResolution;




void main()
{
    float r_limit = 1.0;//radius limit
    float size = 0.1;

    vec2 fragCoord = vTextureCoord.xy;
    // 向下取整
    vec2 q = floor(fragCoord / size) * vec2(size) / iResolution.xy;
    // 返回x的小数部分
    vec2 q2 = fract(fragCoord / size);
    // 红色通道
    float value = texture(sTexture,q).r;
    // 取最小值
    float r = min(value,r_limit);
    vec4 final = vec4(step(length(q2-vec2(0.5)),r)) * texture(sTexture,q);

//    fragColor = final;
    fragColor = texture(sTexture,q2);
//    fragColor = texture(sTexture, vTextureCoord);


//    vec2 fragCoord = vTextureCoord.xy;
//
//    vec2 uv = fragCoord.xy;
//    vec2 uv2 = floor(uv*tile_num)/tile_num;
//    uv -= uv2;
//    uv *= tile_num;
//    fragColor = texture(sTexture, uv2 + vec2(step(1.0-uv.y, uv.x)/(2.0*tile_num.x), step(uv.x, uv.y)/(2.0*tile_num.y)));

//    fragColor = texture(sTexture, vTextureCoord);
}


