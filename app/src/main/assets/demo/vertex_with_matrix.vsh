#version 300 es
layout (location = 0) in vec3 aPosition;  //顶点位置
layout (location = 1) in vec4 aColor;    //顶点颜色
uniform mat4 u_Matrix;
out  vec4 vColor;  //用于传递给片元着色器的变量

void main()
{
   gl_Position = u_Matrix * vec4(aPosition,1.0f); //根据总变换矩阵计算此次绘制此顶点位置
   vColor = aColor;//将接收的颜色传递给片元着色器
   gl_PointSize=10.0;//点大小
}