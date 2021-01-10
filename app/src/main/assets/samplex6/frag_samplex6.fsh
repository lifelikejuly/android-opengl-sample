#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord;//接收从顶点着色器过来的参数
out vec4 fragColor;

uniform int iShaderType;
uniform float fProgress;

#define t iShaderType

void main()
{
    //进行纹理采样
    //    fragColor = texture(sTexture, vTextureCoord);


    vec3 color = texture(sTexture, vTextureCoord).rgb;
    float threshold = 0.7;//阈值
    float mean = (color.r + color.g + color.b) / 3.0;
    switch (iShaderType){
        case 0:
        //1红色处理
        color.g = color.b = mean >= threshold ? 1.0 : 0.0;
        fragColor = vec4(1, color.gb, 1);
        break;
        case 1:
        // 2蓝色处理
        color.r = color.g = mean >= threshold ? 1.0 : 0.0;
        fragColor = vec4(color.rg, 1, 1);
        break;
        case 2:
        // 3负片处理
        vec4 color2 = texture(sTexture, vTextureCoord);
        float r = 1.0 - color2.r;
        float g = 1.0 - color2.g;
        float b = 1.0 - color2.b;
        fragColor = vec4(r, g, b, 1.0);
        break;
        case 3:
        // 4灰度处理
        vec4 color3 = texture(sTexture, vTextureCoord);
        fragColor = vec4(color3.g, color3.g, color3.g, 1.0);
        break;
        case 4:
        // 5怀旧处理
        vec4 color4 = texture(sTexture, vTextureCoord);
        float r4 = color4.r;
        float g4 = color4.g;
        float b4 = color4.b;

        r4 = 0.393* r4 + 0.769 * g4 + 0.189* b4;
        g4 = 0.349 * r4 + 0.686 * g4 + 0.168 * b4;
        b4 = 0.272 * r4 + 0.534 * g4 + 0.131 * b4;
        fragColor = vec4(r4, g4, b4, 1.0);
        break;

        case 5:

        // 6流年处理
        float arg = 1.5;

        vec4 color5= texture(sTexture, vTextureCoord);
        float r5 = color5.r;
        float g5 = color5.g;
        float b5 = color5.b;
        b5 = sqrt(b5)*arg;

        if (b5>1.0) b5 = 1.0;

        fragColor = vec4(r5, g5, b5, 1.0);
        break;

        case 6:

        // 7镜像
        vec2 pos6 = vTextureCoord;
        if (pos6.x > 0.5) {
            pos6.x = 1.0 - pos6.x;
        }
        fragColor = texture(sTexture, pos6);
        break;
        case 7:
        // 8分镜
        vec2 pos8 = vTextureCoord.xy;
        if (pos8.x <= 0.5 && pos8.y<= 0.5){ //左上
            pos8.x = pos8.x * 2.0;
            pos8.y = pos8.y * 2.0;
        } else if (pos8.x > 0.5 && pos8.y< 0.5){ //右上
            pos8.x = (pos8.x - 0.5) * 2.0;
            pos8.y = (pos8.y) * 2.0;
        } else if (pos8.y> 0.5 && pos8.x < 0.5) { //左下
            pos8.y = (pos8.y - 0.5) * 2.0;
            pos8.x = pos8.x * 2.0;
        } else if (pos8.y> 0.5 && pos8.x > 0.5){ //右下
            pos8.y = (pos8.y - 0.5) * 2.0;
            pos8.x = (pos8.x - 0.5) * 2.0;
        }
        fragColor = texture(sTexture, pos8);
        break;
        case 8:
        // 9马赛克

        float rate= 2264.0 / 1080.0;
        float cellX= 1.0;
        float cellY= 1.0;
        float count = 80.0;

        vec2 pos9 = vTextureCoord;
        pos9.x = pos9.x*count;
        pos9.y = pos9.y*count/rate;

        pos9 = vec2(floor(pos9.x/cellX)*cellX/count, floor(pos9.y/cellY)*cellY/(count/rate))+ 0.5/count*vec2(cellX, cellY);
        fragColor = texture(sTexture, pos9);
        break;
        case 9:
        // 10 灵魂出窍
        //周期
        float duration9 = 0.7;
        //生成的第二个图层的最大透明度
        float maxAlpha9 = 0.4;
        //第二个图层放大的最大比率
        float maxScale9 = 1.8;

        //进度
        float progress9 = mod(fProgress, duration9) / duration9;// 0~1
        //当前的透明度
        float alpha9 = maxAlpha9 * (1.0 - progress9);
        //当前的放大比例
        float scale9 = 1.0 + (maxScale9 - 1.0) * progress9;

        //根据放大比例获取对应的x、y值坐标
        float weakX9 = 0.5 + (vTextureCoord.x - 0.5) / scale9;
        float weakY9 = 0.5 + (vTextureCoord.y - 0.5) / scale9;
        //新的图层纹理坐标
        vec2 weakTextureCoords9 = vec2(weakX9, weakY9);

        //新图层纹理坐标对应的纹理像素值
        vec4 weakMask9 = texture(sTexture, weakTextureCoords9);

        vec4 mask9 = texture(sTexture, vTextureCoord);

        //纹理像素值的混合公式，获得混合后的实际颜色
        fragColor = mask9 * (1.0 - alpha9) + weakMask9 * alpha9;
        break;
        case 10:

        // 11 色散
        //周期
        float duration10 = 0.7;
        //放大的最大比例
        float maxScale10 = 1.1;
        //颜色偏移值
        float offset10 = 0.02;

        //当前时间在整个周期中的进度,在0~1
        float progress10 = mod(fProgress, duration10) / duration10; // 0~1
        //具体的偏移量
        vec2 offsetCoords10 = vec2(offset10, offset10) * progress10;
        //图层放大缩小的比例
        float scale10 = 1.0 + (maxScale10 - 1.0) * progress10;

        //获取缩放之后实际纹理坐标
        vec2 ScaleTextureCoords10 = vec2(0.5, 0.5) + (vTextureCoord - vec2(0.5, 0.5)) / scale10;

        //设置缩放之后的纹理坐标和经过具体的颜色偏移坐标
        //三组分别代表RGB不同方向的纹理像素值
        vec4 maskR10 = texture(sTexture, ScaleTextureCoords10 + offsetCoords10);
        vec4 maskB10 = texture(sTexture, ScaleTextureCoords10 - offsetCoords10);
        vec4 mask10 = texture(sTexture, ScaleTextureCoords10);

        //根据不同的纹理坐标值得到经过颜色偏移之后的颜色
        fragColor = vec4(maskR10.r, mask10.g, maskB10.b, mask10.a);

        break;
    }


}