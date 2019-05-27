package com.julyyu.learn.opengl

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import android.opengl.Matrix
import android.os.SystemClock




/**
 *    author : JulyYu
 *    date   : 2019-05-27
 */
class AppGLRenderer3 : GLSurfaceView.Renderer {


    private val TAG = "AppGLRenderer2"


    // new 类成员
    private var mTriangle1Vertices: FloatBuffer? = null
    private var mTriangle2Vertices: FloatBuffer? = null
    private var mTriangle3Vertices: FloatBuffer? = null

    /** 每个Float多少字节 */
    private val mBytePerFloat = 4

    /** 这将用于传递变换矩阵 */
    private var mMVPMatrixHandle: Int = 0
    /** 用于传递model位置信息 */
    private var mPositionHandle: Int = 0
    /** 用于传递模型颜色信息 */
    private var mColorHandle: Int = 0

    // 存放投影矩阵，用于将场景投影到2D视角
    private val mProjectionMatrix = FloatArray(16)

    // 存放模型矩阵，该矩阵用于将模型从对象空间（可以认为每个模型开始都位于宇宙的中心）移动到世界空间
    private val mModelMatrix = FloatArray(16)

    // new class 定义

    /**
     * 存储view矩阵。可以认为这是一个相机，我们通过相机将世界空间转换为眼睛空间
     * 它定位相对于我们眼睛的东西
     */
    private val mViewMatrix = FloatArray(16)

    init {

        // 这个三角形是红色，蓝色和绿色组成
        val triangle1VerticesData = floatArrayOf(
            // X, Y, Z,
            // R, G, B, A
            -0.5f, -0.25f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,

            0.5f, -0.25f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.559016994f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f
        )

        // This triangle is yellow, cyan, and magenta.
        val triangle2VerticesData = floatArrayOf(
            // X, Y, Z,
            // R, G, B, A
            -0.5f, -0.25f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,

            0.5f, -0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,

            0.0f, 0.559016994f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f
        )

        // This triangle is white, gray, and black.
        val triangle3VerticesData = floatArrayOf(
            // X, Y, Z,
            // R, G, B, A
            -0.5f, -0.25f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,

            0.5f, -0.25f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f,

            0.0f, 0.559016994f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        )

        // 初始化缓冲区
        mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.size * mBytePerFloat)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTriangle2Vertices = ByteBuffer.allocateDirect(triangle2VerticesData.size * mBytePerFloat)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTriangle3Vertices = ByteBuffer.allocateDirect(triangle3VerticesData.size * mBytePerFloat)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        mTriangle1Vertices?.put(triangle1VerticesData)?.position(0)
        mTriangle2Vertices?.put(triangle2VerticesData)?.position(0)
        mTriangle3Vertices?.put(triangle3VerticesData)?.position(0)
    }


    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "onDrawFrame")
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)

        // 每10s完成一次旋转
        val time = SystemClock.uptimeMillis() % 10000L
        val angleDegrees = 360.0f / 10000.0f * time.toInt()

        // 画三角形
        Matrix.setIdentityM(mModelMatrix, 0)
//        Matrix.rotateM(mModelMatrix, 0, angleDegrees, 0.0F, 0.0F, 1.0F);
        drawTriangle(mTriangle1Vertices!!)

        // Draw one translated a bit down and rotated to be flat on the ground.
        Matrix.setIdentityM(mModelMatrix, 0)
        Matrix.translateM(mModelMatrix, 0, 0.0f, -1.0f, 0.0f)
        Matrix.rotateM(mModelMatrix, 0, 90.0f, 1.0f, 0.0f, 0.0f)
        Matrix.rotateM(mModelMatrix, 0, angleDegrees, 0.0f, 0.0f, 1.0f)
        drawTriangle(mTriangle2Vertices!!)

        // Draw one translated a bit to the right and rotated to be facing to the left.
        Matrix.setIdentityM(mModelMatrix, 0)
        Matrix.translateM(mModelMatrix, 0, 1.0f, 0.0f, 0.0f)
        Matrix.rotateM(mModelMatrix, 0, 90.0f, 0.0f, 1.0f, 0.0f)
        Matrix.rotateM(mModelMatrix, 0, angleDegrees, 0.0f, 0.0f, 1.0f)
        drawTriangle(mTriangle3Vertices!!)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged")
        if(width == 0 || height == 0) return
        // 设置OpenGL界面和当前视图相同的尺寸
        GLES30.glViewport(0, 0, width, height)

        // 创建一个新的透视投影矩阵，高度保持不变，而高度根据纵横比而变换
        val ratio = (width.toFloat()  / height.toFloat())
        val left = -ratio
        val bottom = -1.0f
        val top = 1.0f
        val near = 1.0f
        val far = 10f

        Matrix.frustumM(mProjectionMatrix, 0, left, ratio, bottom, top, near, far)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated")
        // 设置背景清理颜色为灰色
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0.5f)

        // 将眼睛放到原点之后
        val eyeX = 0.0f
        val eyeY = 0.0f
        val eyeZ = 2.5f

        // 我们的眼睛望向哪
        val lookX = 0.0f
        val lookY = 0.0f
        val lookZ = -5.0f

        // 设置我们的向量，这是我们拿着相机时头指向的方向
        val upX = 0.0f
        val upY = 1.0f
        val upZ = 0.0f

        // 设置view矩阵，可以说这个矩阵代表相机的位置
        // 注意：在OpenGL 1中使用ModelView matrix，这是一个model和view矩阵的组合。
        //在OpenGL2中，我们选择分别跟踪这些矩阵
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ)

        val vertexShader = "uniform mat4 u_MVPMatrix;    \n" + // 一个表示组合model、view、projection矩阵的常量

                "attribute vec4 a_Position;   \n" + // 我们将要传入的每个顶点的位置信息

                "attribute vec4 a_Color;      \n" + // 我们将要传入的每个顶点的颜色信息


                "varying vec4 v_Color;        \n" + // 他将被传入片段着色器


                "void main()                  \n" + // 顶点着色器入口

                "{                            \n" +
                "   v_Color = a_Color;        \n" + // 将颜色传递给片段着色器

                // 它将在三角形内插值
                "   gl_Position = u_MVPMatrix \n" + // gl_Position是一个特殊的变量用来存储最终的位置

                "               * a_Position; \n" + // 将顶点乘以矩阵得到标准化屏幕坐标的最终点

                "}                            \n"

        val fragmentShader = "precision mediump float;       \n" + // 我们将默认精度设置为中等，我们不需要片段着色器中的高精度

                "varying vec4 v_Color;          \n" + // 这是从三角形每个片段内插的顶点着色器的颜色

                "void main()                    \n" + // 片段着色器入口

                "{                              \n" +
                "   gl_FragColor = v_Color;     \n" + // 直接将颜色传递

                "}                              \n"

        // 加载顶点着色器
        var vertexShaderHandle = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER)
        if (vertexShaderHandle != 0) {
            // 传入顶点着色器源代码
            GLES30.glShaderSource(vertexShaderHandle, vertexShader)
            // 编译顶点着色器
            GLES30.glCompileShader(vertexShaderHandle)

            // 获取编译状态
            val compileStatus = IntArray(1)
            GLES30.glGetShaderiv(vertexShaderHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0)

            // 如果编译失败则删除着色器
            if (compileStatus[0] == 0) {
                GLES30.glDeleteShader(vertexShaderHandle)
                vertexShaderHandle = 0
            }
        }
        if (vertexShaderHandle == 0) {
            throw RuntimeException("Error creating vertex shader.")
        }

        var fragmentShaderHandle = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER)
        if (fragmentShaderHandle != 0) {
            GLES30.glShaderSource(fragmentShaderHandle, fragmentShader)
            GLES30.glCompileShader(fragmentShaderHandle)
            val compileStatus = IntArray(1)
            GLES30.glGetShaderiv(fragmentShaderHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] == 0) {
                GLES30.glDeleteShader(fragmentShaderHandle)
                fragmentShaderHandle = 0
            }
        }
        if (fragmentShaderHandle == 0) {
            throw RuntimeException("Error creating fragment shader.")
        }

        // 创建一个OpenGL程序对象并将引用放进去
        var programHandle = GLES30.glCreateProgram()
        if (programHandle != 0) {
            // 绑定顶点着色器到程序对象中
            GLES30.glAttachShader(programHandle, vertexShaderHandle)
            // 绑定片段着色器到程序对象中
            GLES30.glAttachShader(programHandle, fragmentShaderHandle)
            // 绑定属性
            GLES30.glBindAttribLocation(programHandle, 0, "a_Position")
            GLES30.glBindAttribLocation(programHandle, 1, "a_Color")
            // 将两个着色器连接到程序
            GLES30.glLinkProgram(programHandle)
            // 获取连接状态
            val linkStatus = IntArray(1)
            GLES30.glGetProgramiv(programHandle, GLES30.GL_LINK_STATUS, linkStatus, 0)
            // 如果连接失败，删除这程序
            if (linkStatus[0] == 0) {
                GLES30.glDeleteProgram(programHandle)
                programHandle = 0
            }
        }

        if (programHandle == 0) {
            throw RuntimeException("Error creating program.")
        }

        // 设置程序引用，这将在之后传递值到程序时使用
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix")
        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position")
        mColorHandle = GLES30.glGetAttribLocation(programHandle, "a_Color")

        // 告诉OpenGL渲染的时候使用这个程序
        GLES30.glUseProgram(programHandle)
    }


    // 新的类成员
    /** 为最终的组合矩阵分配存储空间，这将用来传入着色器程序 */
    private val mMVPMatrix = FloatArray(16)

    /** 每个顶点有多少字节组成，每次需要迈过这么一大步（每个顶点有7个元素，3个表示位置，4个表示颜色，7 * 4 = 28个字节） */
    private val mStrideBytes = 7 * mBytePerFloat

    /** 位置数据偏移量 */
    private val mPositionOffset = 0

    /** 一个元素的位置数据大小 */
    private val mPositionDataSize = 3

    /** 颜色数据偏移量 */
    private val mColorOffset = 3

    /** 一个元素的颜色数据大小 */
    private val mColorDataSize = 4


    /**
     * 从给定的顶点数据中绘制一个三角形
     * @param aTriangleBuffer 包含顶点数据的缓冲区
     */
    private fun drawTriangle(aTriangleBuffer: FloatBuffer) {
        aTriangleBuffer.position(mPositionOffset)
        GLES30.glVertexAttribPointer(
            mPositionHandle, mPositionDataSize, GLES30.GL_FLOAT, false,
            mStrideBytes, aTriangleBuffer
        )

        GLES30.glEnableVertexAttribArray(mPositionHandle)

        // 传入颜色信息
        aTriangleBuffer.position(mColorOffset)
        GLES30.glVertexAttribPointer(
            mColorHandle, mColorDataSize, GLES30.GL_FLOAT, false,
            mStrideBytes, aTriangleBuffer
        )

        GLES30.glEnableVertexAttribArray(mColorHandle)

        // 将视图矩阵乘以模型矩阵，并将结果存放到MVP Matrix（model * view）
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0)

        // 将上面计算好的视图模型矩阵乘以投影矩阵，并将结果存放到MVP Matrix（model * view * projection）
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0)

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)

    }
}