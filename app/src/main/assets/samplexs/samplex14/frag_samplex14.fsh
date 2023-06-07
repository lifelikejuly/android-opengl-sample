#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
out vec4 fragColor;

void main()
{
    //进行纹理采样

    vec2 pos2 = vTextureCoord.xy;

    if(pos2.x < 0.5){
        pos2.x += 0.5;
    }
    if(pos2.x > 0.5){
        pos2.x -= 0.5;
    }



    fragColor = texture(sTexture, pos2);

//    fragColor  = vec4(fragColor.r * 0.5, fragColor.g, fragColor.b, fragColor.a);
}