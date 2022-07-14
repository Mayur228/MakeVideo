package com.example.makevideo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.makevideo.databinding.ActivityMainBinding
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Mode
import java.io.File
import java.util.*
import android.os.Environment




class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setUpView()

    }

    private fun setUpView() {
        binding.camera.open()
        binding.camera.mode = Mode.VIDEO
        binding.recodeVideo.setOnClickListener {
            recodeVideo()
        }
    }

    private fun setUpImages() {
        val emojis = listOf<Int>(R.drawable.hand_emoji_1,R.drawable.hand_emoji_2,R.drawable.hand_emoji_3)
        val ran = Random()
        val i: Int = ran.nextInt(emojis.size)
        binding.emojiIV.setImageResource(emojis.get(i))
        if (binding.camera.isTakingVideo) {
            startTimer().start()
        }else {
            startTimer().cancel()
        }
    }

    private fun startTimer(): CountDownTimer {
        return object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                setUpImages()
            }
        }

    }

    private fun recodeVideo() {
        binding.camera.addCameraListener(object : CameraListener() {
            override fun onVideoRecordingStart() {
                super.onVideoRecordingStart()
                startTimer().start()
                Toast.makeText(applicationContext,"Video Start",Toast.LENGTH_LONG).show()

            }

            override fun onVideoTaken(result: VideoResult) {
                super.onVideoTaken(result)
                VideoPreviewActivity.videoResult = result
                startActivity(Intent(applicationContext,VideoPreviewActivity::class.java))
            }

            override fun onVideoRecordingEnd() {
                super.onVideoRecordingEnd()
                startTimer().cancel()
                Toast.makeText(applicationContext,"Video Stop",Toast.LENGTH_LONG).show()
            }

        })

        binding.camera.takeVideoSnapshot(File(filesDir.absolutePath,"demoVideo.mp4"),5000)

    }

    override fun onResume() {
        super.onResume()
        binding.camera.open()
    }

}