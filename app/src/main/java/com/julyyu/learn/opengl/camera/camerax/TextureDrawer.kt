package com.android.example.cameraxbasic.fragments

import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLES20.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class TextureDrawer(OESTextureId: Int) {
    val buffer: FloatBuffer?
    private var mOESTextureId = -1
    private var vertexShader = -1
    private var fragmentShader = -1
    var shaderProgram = -1

    private var aPositionLocation = -1
    private var aTextureCoordLocation = -1
    private var uTextureMatrixLocation = -1
    private var uTextureSamplerLocation = -1

    init {
        mOESTextureId = OESTextureId
        buffer = createBuffer(vertexData)
        vertexShader = loadShader(GL_VERTEX_SHADER, VERTEX_SHADER)
        fragmentShader = loadShader(GL_FRAGMENT_SHADER, FRAGMENT_SHADER)
        shaderProgram = linkProgram(vertexShader, fragmentShader)
    }

    private fun createBuffer(vertexData: FloatArray): FloatBuffer {
        val buffer = ByteBuffer.allocateDirect(vertexData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        buffer.put(vertexData, 0, vertexData.size).position(0)
        return buffer
    }

    private fun loadShader(type: Int, shaderSource: String): Int {
        val shader = glCreateShader(type)
        if (shader == 0) {
            throw RuntimeException("Create Shader Failed!" + glGetError())
        }
        glShaderSource(shader, shaderSource)
        glCompileShader(shader)
        return shader
    }

    private fun linkProgram(verShader: Int, fragShader: Int): Int {
        val program = glCreateProgram()
        if (program == 0) {
            throw RuntimeException("Create Program Failed!" + glGetError())
        }
        glAttachShader(program, verShader)
        glAttachShader(program, fragShader)
        glLinkProgram(program)

        glUseProgram(program)
        return program
    }

    fun drawTexture(transformMatrix: FloatArray) {
        aPositionLocation = glGetAttribLocation(shaderProgram, TextureDrawer.POSITION_ATTRIBUTE)
        aTextureCoordLocation = glGetAttribLocation(shaderProgram, TextureDrawer.TEXTURE_COORD_ATTRIBUTE)
        uTextureMatrixLocation = glGetUniformLocation(shaderProgram, TextureDrawer.TEXTURE_MATRIX_UNIFORM)
        uTextureSamplerLocation = glGetUniformLocation(shaderProgram, TextureDrawer.TEXTURE_SAMPLER_UNIFORM)

        glActiveTexture(GLES20.GL_TEXTURE0)
        glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mOESTextureId)
        glUniform1i(uTextureSamplerLocation, 0)
        glUniformMatrix4fv(uTextureMatrixLocation, 1, false, transformMatrix, 0)

        if (buffer != null) {
            buffer.position(0)
            glEnableVertexAttribArray(aPositionLocation)
            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 16, buffer)

            buffer.position(2)
            glEnableVertexAttribArray(aTextureCoordLocation)
            glVertexAttribPointer(aTextureCoordLocation, 2, GL_FLOAT, false, 16, buffer)

            glDrawArrays(GL_TRIANGLES, 0, 6)
        }
    }

    companion object {

        private val vertexData = floatArrayOf(
                1f, 1f, 1f, 1f,
                -1f, 1f, 0f, 1f,
                -1f, -1f, 0f, 0f,
                1f, 1f, 1f, 1f,
                -1f, -1f, 0f, 0f,
                1f, -1f, 1f, 0f)

        private val POSITION_ATTRIBUTE = "aPosition"
        private val TEXTURE_COORD_ATTRIBUTE = "aTextureCoordinate"
        private val TEXTURE_MATRIX_UNIFORM = "uTextureMatrix"
        private val TEXTURE_SAMPLER_UNIFORM = "uTextureSampler"

        private val VERTEX_SHADER = "attribute vec4 aPosition;\n" +
                "uniform mat4 uTextureMatrix;\n" +
                "attribute vec4 aTextureCoordinate;\n" +
                "varying vec2 vTextureCoord;\n" +
                "void main()\n" +
                "{\n" +
                "  vTextureCoord = (uTextureMatrix * aTextureCoordinate).xy;\n" +
                "  gl_Position = aPosition;\n" +
                "}"

        private val FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\n" +
                "precision mediump float;\n" +
                "uniform samplerExternalOES uTextureSampler;\n" +
                "varying vec2 vTextureCoord;\n" +
                "void main()\n" +
                "{\n" +
//                "  vec4 vCameraColor = texture2D(uTextureSampler, vTextureCoord);\n" +
//                "  float fGrayColor = (0.3*vCameraColor.r + 0.59*vCameraColor.g + 0.11*vCameraColor.b);\n" +
                "  gl_FragColor = texture2D(uTextureSampler, vTextureCoord);\n" +
                "}\n"


        fun createOESTextureObject(): Int {
            val tex = IntArray(1)
            GLES20.glGenTextures(1, tex, 0)
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0])
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
            return tex[0]
        }
    }
}

