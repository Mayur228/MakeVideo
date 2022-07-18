package com.example.makevideo.cameraview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makevideo.databinding.ActivityMainBinding
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Mode
import java.io.File
import com.example.makevideo.R
import com.example.makevideo.cameraview.adapter.EmojiAdapter
import com.example.makevideo.videopreview.VideoPreviewActivity
import com.example.makevideo.cameraview.vo.VideoStates
import com.google.android.flexbox.*
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: EmojiAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpView()
        setUpEmojiList()
        setupButtonListener()
    }

    private fun setUpView() {
        binding.camera.open()
        binding.camera.mode = Mode.VIDEO
        binding.videoPB.setCurrentProgress(45.0)
        binding.videoPB.isAnimationEnabled = true
        videoState(VideoStates.INIT)

    }

    private fun setUpEmojiList() {
        val emojis = listOf<Int>(
            R.drawable.hand_emoji_1,
            R.drawable.hand_emoji_2,
            R.drawable.hand_emoji_3,
            R.drawable.hand_emoji_1,
            R.drawable.hand_emoji_2,
            R.drawable.hand_emoji_3
        )
        val layoutManager = FlexboxLayoutManager(applicationContext).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }

        binding.emojiRV.layoutManager = layoutManager
        adapter = EmojiAdapter()
        adapter.emojiList = emojis
        binding.emojiRV.adapter = adapter
    }

    /*private fun setUpImages() {
        val emojis =
            listOf<Int>(R.drawable.hand_emoji_1, R.drawable.hand_emoji_2, R.drawable.hand_emoji_3)
        val ran = Random()
        val i: Int = ran.nextInt(emojis.size)
        binding.emojiIV.setImageResource(emojis[i])
        if (binding.camera.isTakingVideo) {
            startTimer().start()
        } else {
            startTimer().cancel()
        }
    }*/

    private fun startTimer(): CountDownTimer {
        return object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countTimerTV.isVisible = 0 != (millisUntilFinished / 1000).toInt()
                binding.countTimerTV.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                recodeVideo()
            }
        }

    }

    private fun setupButtonListener() {
        binding.recodeVideo.setOnClickListener {
            if (binding.camera.isTakingVideo) {
                binding.camera.stopVideo()
            } else {
                videoState(VideoStates.START)
                startTimer().start()
            }
        }

        binding.flashIB.setOnClickListener {
            if (binding.camera.flash == Flash.TORCH) {
                binding.camera.flash = Flash.OFF
            } else {
                binding.camera.flash = Flash.TORCH
            }
        }

        binding.flipCamera.setOnClickListener {
            if (binding.camera.facing == Facing.FRONT) {
                binding.camera.facing = Facing.BACK
            } else {
                binding.camera.facing = Facing.FRONT
            }
        }
    }

    private fun recodeVideo() {
        binding.camera.addCameraListener(object : CameraListener() {
            override fun onVideoRecordingStart() {
                super.onVideoRecordingStart()
                videoState(VideoStates.STARTED)
                binding.group1.isVisible = false
                binding.emojiIV.isVisible = false
                binding.emojiCV.isVisible = true
            }

            override fun onVideoTaken(result: VideoResult) {
                super.onVideoTaken(result)
                VideoPreviewActivity.videoResult = result
                startActivity(Intent(applicationContext, VideoPreviewActivity::class.java))
            }

            override fun onVideoRecordingEnd() {
                super.onVideoRecordingEnd()
                binding.emojiIV.isVisible = false
                binding.group1.isVisible = true
                binding.emojiCV.isVisible = false
                videoState(VideoStates.INIT)
            }

        })

        binding.camera.takeVideoSnapshot(
            File(filesDir.absolutePath, "demoVideo.mp4"),
            Int.MAX_VALUE
        )

    }

    fun videoState(states: VideoStates) {
        when (states) {
            VideoStates.INIT -> binding.recodeVideo.setImageResource(R.drawable.ic_init_record)
            VideoStates.START -> binding.recodeVideo.setImageResource(R.drawable.ic_start_record)
            VideoStates.STARTED -> binding.recodeVideo.setImageResource(R.drawable.ic_video_start)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.camera.open()
    }

    override fun onPause() {
        super.onPause()
        binding.camera.open()
    }


}