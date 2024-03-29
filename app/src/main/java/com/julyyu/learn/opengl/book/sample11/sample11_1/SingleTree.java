package com.julyyu.learn.opengl.book.sample11.sample11_1;


import static com.julyyu.learn.opengl.book.sample11.sample11_1.SignBoardRenderer.cx;
import static com.julyyu.learn.opengl.book.sample11.sample11_1.SignBoardRenderer.cz;

import com.julyyu.learn.opengl.MatrixState;

//单个的树类
public class SingleTree implements Comparable<SingleTree>
{
	float x;//植物的x坐标
	float z;//植物的z坐标
	float yAngle;//植物纹理矩形绕y轴旋转的角度
	TreeGroup tg;//所属TreeGroup的引用
	public SingleTree(float x,float z,float yAngle,TreeGroup tg)
	{
		//初始化植物的x、z坐标
		this.x=x;
		this.z=z;
		this.yAngle=yAngle;//初始化植物纹理矩形绕y轴旋转的角度
		this.tg=tg;
	}
	public void drawSelf(int texId)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(x, 0, z);
		MatrixState.rotate(yAngle, 0, 1, 0);
		tg.tfd.drawSelf(texId);//调用TreeForDraw中的drawSelf方法绘制树木
		MatrixState.popMatrix();
	}
	public void calculateBillboardDirection()
	{//根据摄像机位置计算树木面朝向
		float xspan=x-cx;//计算从植物位置到摄像机位置向量的x分量
		float zspan=z-cz;//计算从植物位置到摄像机位置向量的z分量
		
		//根据向量的两个分量计算出纹理矩形绕y轴旋转的角度
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
	}
	@Override
	public int compareTo(SingleTree another)
	{
		//根据植物到摄像机的距离比较两个植物“大小”的方法
		float xs=x-cx;//计算从本植物位置到摄像机位置向量的x分量
		float zs=z-cz;//计算从本植物位置到摄像机位置向量的z分量
		
		float xo=another.x-cx;//计算从另一植物位置到摄像机位置向量的x分量
		float zo=another.z-cz;//计算从另一植物位置到摄像机位置向量的z分量
		
		float disA=(float)Math.sqrt(xs*xs+zs*zs);//计算当前植物到摄像机的距离
		float disB=(float)Math.sqrt(xo*xo+zo*zo);//计算另一植物到摄像机的距离
		
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  //根据距离大小决定方法返回值
	}
}