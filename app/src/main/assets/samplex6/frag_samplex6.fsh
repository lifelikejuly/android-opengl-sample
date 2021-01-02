#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
out vec4 fragColor;

void main()
{
    //进行纹理采样
    fragColor = texture(sTexture, vTextureCoord);


//    vec3 color = texture(sTexture, vTextureCoord).rgb;
//    float threshold = 0.7;//阈值
//    float mean = (color.r + color.g + color.b) / 3.0;
    // 1红色处理
//    color.g = color.b = mean >= threshold ? 1.0 : 0.0;
//    fragColor = vec4(1,color.gb,1);

    // 2蓝色处理
//    color.r = color.g = mean >= threshold ? 1.0 : 0.0;
//    fragColor = vec4(color.rg, 1, 1);

    // 3负片处理
//    vec4 color = texture(sTexture, vTextureCoord);
//    float r = 1.0 - color.r;
//    float g = 1.0 - color.g;
//    float b = 1.0 - color.b;
//    fragColor = vec4(r, g, b, 1.0);

   // 4灰度处理
//    vec4 color = texture(sTexture, vTextureCoord);
//    fragColor = vec4(color.g, color.g, color.g, 1.0);

    // 5怀旧处理
//    vec4 color = texture(sTexture, vTextureCoord);
//    float r = color.r;
//    float g = color.g;
//    float b = color.b;
//
//    r = 0.393* r + 0.769 * g + 0.189* b;
//    g = 0.349 * r + 0.686 * g + 0.168 * b;
//    b = 0.272 * r + 0.534 * g + 0.131 * b;
//    fragColor = vec4(r, g, b, 1.0);


    // 6流年处理
//    float arg = 1.5;
//
//    vec4 color= texture(sTexture, vTextureCoord);
//    float r = color.r;
//    float g = color.g;
//    float b = color.b;
//    b = sqrt(b)*arg;
//
//    if (b>1.0) b = 1.0;
//
//    fragColor = vec4(r, g, b, 1.0);

    // 7镜像
//    vec2 pos = vTextureCoord;
//    if (pos.x > 0.5) {
//        pos.x = 1.0 - pos.x;
//    }
//    fragColor = texture(sTexture, pos);



    // 8分镜
    vec2 pos = vTextureCoord.xy;
    if (pos.x <= 0.5 && pos.y<= 0.5){ //左上
        pos.x = pos.x * 2.0;
        pos.y = pos.y * 2.0;
    } else if (pos.x > 0.5 && pos.y< 0.5){ //右上
        pos.x = (pos.x - 0.5) * 2.0;
        pos.y = (pos.y) * 2.0;
    } else if (pos.y> 0.5 && pos.x < 0.5) { //左下
        pos.y = (pos.y - 0.5) * 2.0;
        pos.x = pos.x * 2.0;
    } else if (pos.y> 0.5 && pos.x > 0.5){ //右下
        pos.y = (pos.y - 0.5) * 2.0;
        pos.x = (pos.x - 0.5) * 2.0;
    }
    fragColor = texture(sTexture, pos);


    // 9马赛克

//    float rate= 2264.0 / 1080.0;
//    float cellX= 1.0;
//    float cellY= 1.0;
//    float count = 80.0;
//
//    vec2 pos = vTextureCoord;
//    pos.x = pos.x*count;
//    pos.y = pos.y*count/rate;
//
//    pos = vec2(floor(pos.x/cellX)*cellX/count, floor(pos.y/cellY)*cellY/(count/rate))+ 0.5/count*vec2(cellX, cellY);
//    fragColor = texture(sTexture, pos);

    // 10 灵魂出窍
    //周期
//    float duration = 0.7;
//    //生成的第二个图层的最大透明度
//    float maxAlpha = 0.4;
//    //第二个图层放大的最大比率
//    float maxScale = 1.8;
//
//    //进度
//    float progress = mod(0.2, duration) / duration; // 0~1
//    //当前的透明度
//    float alpha = maxAlpha * (1.0 - progress);
//    //当前的放大比例
//    float scale = 1.0 + (maxScale - 1.0) * progress;
//
//    //根据放大比例获取对应的x、y值坐标
//    float weakX = 0.5 + (vTextureCoord.x - 0.5) / scale;
//    float weakY = 0.5 + (vTextureCoord.y - 0.5) / scale;
//    //新的图层纹理坐标
//    vec2 weakTextureCoords = vec2(weakX, weakY);
//
//    //新图层纹理坐标对应的纹理像素值
//    vec4 weakMask = texture(sTexture, weakTextureCoords);
//
//    vec4 mask = texture(sTexture, vTextureCoord);
//
//    //纹理像素值的混合公式，获得混合后的实际颜色
//    fragColor = mask * (1.0 - alpha) + weakMask * alpha;


    // 11 色散
    //周期
//    float duration = 0.7;
//    //生成的第二个图层的最大透明度
//    float maxAlpha = 0.4;
//    //第二个图层放大的最大比率
//    float maxScale = 1.8;
//
//    //进度
//    float progress = mod(0.2, duration) / duration; // 0~1
//    //当前的透明度
//    float alpha = maxAlpha * (1.0 - progress);
//    //当前的放大比例
//    float scale = 1.0 + (maxScale - 1.0) * progress;
//
//    //根据放大比例获取对应的x、y值坐标
//    float weakX = 0.5 + (vTextureCoord.x - 0.5) / scale;
//    float weakY = 0.5 + (vTextureCoord.y - 0.5) / scale;
//    //新的图层纹理坐标
//    vec2 weakTextureCoords = vec2(weakX, weakY);
//
//    //新图层纹理坐标对应的纹理像素值
//    vec4 weakMask = texture(sTexture, weakTextureCoords);
//
//    vec4 mask = texture(sTexture, vTextureCoord);
//
//    //纹理像素值的混合公式，获得混合后的实际颜色
//    fragColor = mask * (1.0 - alpha) + weakMask * alpha;

}