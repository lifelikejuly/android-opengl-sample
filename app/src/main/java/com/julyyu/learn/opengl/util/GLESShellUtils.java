package com.julyyu.learn.opengl.util;

/**
 * @author julyyu
 * @date 11/17/21.
 * description：常用OpenGL脚本特效
 */
public class GLESShellUtils {

    // 三分屏
    public static String fsTextureThreeGrid1 = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "    float three = 1.0 / 3.0; \n"+
            "    vec2 pos2 = vTexCoord.xy;\n" +
            "     if(pos2.y < three){\n" +
            "     pos2.y += three;}\n" +
            "     if(pos2.y > three){\n" +
            "     pos2.y -= three;}\n" +

            "    vFragColor = texture(uTextureUnit,pos2);\n" +
            "}\n";
    // 三分屏
    public static String fsTextureThreeGrid2 = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "    float three = 1.0 / 3.0; \n"+
            "    vec2 pos2 = vTexCoord.xy;\n" +
            "     if(pos2.y < three){\n" +
            "       pos2.y = (pos2.y + three) / 2.0 + 0.25;" +
            "       pos2.x =  pos2.x / 2.0;" +
            "     }else\n" +
            "     if(pos2.y > three && pos2.y < three * 2.0){\n" +
            "       pos2.x = pos2.x / 2.0 + 0.25;" +
            "       pos2.y = pos2.y / 2.0 + 0.25;" +
            "     }else \n" +
            "     if(pos2.y > three * 2.0){\n" +
            "       pos2.y -= three;" +
            "     }\n" +
            "     vFragColor = texture(uTextureUnit,pos2);\n" +
            "}\n";
    // 三分屏
    public static String fsTextureMore = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "    float four = 1.0 / 4.0; \n"+
            "    vec2 pos2 = vTexCoord.xy;\n" +
            "    vec2 pos3 = vTexCoord.xy;\n" +
            "     pos2.y = (pos2.y + 0.25) /2.0;\n" +
            "     pos2.x = (pos2.x + 0.25) / 2.0;\n" +
            "  if(pos3.y > four && pos3.y < four * 3.0  && pos3.x > 0.25 && pos3.x < 0.85){\n" +
                "     pos2.y = pos3.y;\n" +
                "     pos2.x = pos3.x;\n" +
            "     }\n" +
            "     vFragColor = texture(uTextureUnit,pos2);\n" +
            "}\n";
    // 左右mix
    public static String fsTextureMore1 = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "     vec4 newColor1 = texture(uTextureUnit, vTexCoord);\n" +
            "     vec4 newColor2 = texture(uTextureUnit, vec2(1.0 - vTexCoord.x,vTexCoord.y));\n" +
            "     vFragColor = mix(newColor1,newColor2,0.5);\n" +
            "}\n";
    // 上下阴影
    public static String fsTextureMore2 = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "    float three = 1.0 / 3.0; \n"+
            "    vec2 pos2 = vTexCoord.xy;\n" +
            "     if(pos2.y <= three){\n" +
            "       pos2.y  +=  three * 2.0;" +
            "     }else if(pos2.y > three && pos2.y < three * 2.0){" +
            "       pos2.y = pos2.y; \n" +
            "       pos2.x = pos2.x; \n" +
            "     }else if(pos2.y >= three * 2.0){\n" +
            "       pos2.y -= three * 2.0;\n" +
            "     }\n" +
            "     vec4 newColor1 = texture(uTextureUnit, vTexCoord);\n" +
            "     vec4 newColor2 = texture(uTextureUnit, pos2);\n" +
            "     vFragColor = mix(newColor1,newColor2,0.5);\n" +
            "}\n";
    /// 增加爱心
    public static final String fsTextureAddHeart = "#version 300 es\n" +
            "precision highp float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "/// 画爱心 \n" +
            "float sdHeart(vec2 uv, float size, vec2 offset) {\n" +
            "  float x = uv.x - offset.x;\n" +
            "  float y = - (uv.y - offset.y);\n" +
            "  float xx = x * x;\n" +
            "  float yy = y * y;\n" +
            "  float yyy = yy * y;\n" +
            "  float group = xx + yy - size;\n" +
            "  float d = group * group * group - xx * yyy;\n" +
            "  return d;\n" +
            "}" +
            "/// 画布 step(0., circle) 相当于判断只是否是大于0 大于则 返回1 否则是0\n" +
            "vec4 drawScene(vec4 col,vec2 uv,float value) {\n" +
            "  vec4 col2 = texture(uTextureUnit, vTexCoord + 0.2);\n" +
            "  float heart = sdHeart(uv * 2., 0.01, vec2(value, value));\n" +
            "  col = mix(col2,col,step(0., heart));\n" +
            "  return col;\n" +
            "}"+
            "void main() {\n" +
            "     vec2 pos2 = vTexCoord.xy;\n" +
            "     vec4 newColor1 = texture(uTextureUnit, vTexCoord);\n" +
            "     vFragColor  = drawScene(newColor1,pos2,0.5);\n" +
            "}\n";
}
