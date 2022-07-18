package com.example.makevideo.videopreview

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makevideo.R
import com.example.makevideo.databinding.ActivityVideoPreviewBinding
import com.otaliastudios.cameraview.VideoResult

class VideoPreviewActivity : AppCompatActivity() {
    companion object {
        var videoResult: VideoResult? = null
    }

    lateinit var binding: ActivityVideoPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_preview)

        setUpView()
    }

    private fun setUpView() {
        binding.video.setVideoURI(Uri.fromFile(videoResult?.file))
        binding.video.start()

        binding.video.setOnCompletionListener {
            binding.video.start()
        }
    }
}