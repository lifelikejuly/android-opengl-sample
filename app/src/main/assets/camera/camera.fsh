#version 300 es

precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
void main()
{
   gl_FragColor = texture2D(sTexture, vTextureCoord);
}