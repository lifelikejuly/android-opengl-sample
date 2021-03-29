#version 300 es
in vec2 aPosition;//顶点位置
in vec2 aTexCoor;//顶点纹理坐标
out vec2 vTextureCoord;//用于传递给片元着色器的out变量

void main()
{
   gl_Position = vec4(aPosition.x,aPosition.y,1.0,1.0);//根据总变换矩阵计算此次绘制此顶点位置
   vTextureCoord = aTexCoor;//将接收的纹理坐标传递给片元着色器
}