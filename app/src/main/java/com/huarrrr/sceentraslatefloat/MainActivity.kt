package com.huarrrr.sceentraslatefloat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        findViewById<TextView>(R.id.tv).setOnClickListener {
//            val intent = Intent(context, TakePhoteActivity::class.java)
//            startActivity(intent)
        }


        Thread { deepFile("tessdata") }.start()
    }

    /**
     * 将assets中的文件复制出
     *
     * @param path
     */
    fun deepFile(path: String) {
        var path1 = path
        val newPath = getExternalFilesDir(null).toString() + "/"
        try {
            val str = assets.list(path1)
            if (str!!.isNotEmpty()) { //如果是目录
                val file = File(newPath + path1)
                file.mkdirs()
                for (string in str) {
                    path1 = "$path1/$string"
                    deepFile(path1)
                    path1 = path1.substring(0, path1.lastIndexOf('/')) //回到原来的path
                }
            } else { //如果是文件
                val `is`: InputStream = assets.open(path1)
                val fos = FileOutputStream(File(newPath + path1))
                val buffer = ByteArray(1024)
                while (true) {
                    val len: Int = `is`.read(buffer)
                    if (len == -1) {
                        break
                    }
                    fos.write(buffer, 0, len)
                }
                `is`.close()
                fos.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}