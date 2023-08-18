package com.example.myapplication

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class MusicPlayer : AppCompatActivity() {

    private var mediaPlayer:MediaPlayer? = null
    private lateinit var runner:Runnable
    private var updateSeekBar: Thread? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        getSupportActionBar()?.hide();


        var seekbar = findViewById<SeekBar>(R.id.seekBar)

        seekbar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?)= Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit


        })



        var isplaying = true

        var text = intent.getParcelableExtra<SongInformation>("Song")

        var tview = findViewById<TextView>(R.id.MPtextview)
        tview.text = text?.displayName

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(text?.path)
        mediaPlayer?.prepare()
        mediaPlayer?.start()


        seekbar.max = mediaPlayer!!.duration

        updateSeekBar = Thread {
            while (mediaPlayer != null) {
                try {
                    // Delay the thread for 100 milliseconds
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                // Update the seek bar with the current position of the media player
                runOnUiThread {
                    if (mediaPlayer != null) {
                        seekbar?.progress = mediaPlayer!!.currentPosition
                    }
                }
            }
        }
        updateSeekBar!!.start()

        var ppbtn = findViewById<Button>(R.id.pauseplaybutton)
        ppbtn.setOnClickListener {

            if(isplaying){
                mediaPlayer?.pause()
                isplaying = false
                ppbtn.text = "Play"
            }
            else{
                mediaPlayer?.start()
                isplaying = true
                ppbtn.text = "Pause"
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Stop the thread and release the media player resources
        mediaPlayer?.release()
        mediaPlayer = null
    }
    override fun onBackPressed() {

        if(mediaPlayer!=null){
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
        super.onBackPressed()
    }

}