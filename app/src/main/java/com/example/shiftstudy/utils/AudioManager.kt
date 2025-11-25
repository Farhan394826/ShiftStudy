package com.example.shiftstudy.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.shiftstudy.R

object AudioManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentVolume = 0.5f // 0.0 to 1.0

    /**
     * Initialize and play the ambient sound
     * @param context Application context
     * @param audioResId Resource ID of the audio file (e.g., R.raw.backgroundsound)
     */
    fun playAmbientSound(context: Context, audioResId: Int = R.raw.backgroundsound) {
        if (mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(context, audioResId)
                mediaPlayer?.apply {
                    isLooping = true // Loop the sound continuously
                    setVolume(currentVolume, currentVolume)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (!isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
        }
    }

    /**
     * Pause the ambient sound
     */
    fun pauseAmbientSound() {
        if (isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
        }
    }

    /**
     * Stop and release the media player
     */
    fun stopAmbientSound() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        isPlaying = false
    }

    /**
     * Set volume (0.0 to 1.0)
     */
    fun setVolume(volume: Float) {
        currentVolume = volume.coerceIn(0f, 1f)
        mediaPlayer?.setVolume(currentVolume, currentVolume)
    }

    /**
     * Get current volume
     */
    fun getVolume(): Float {
        return currentVolume
    }

    /**
     * Check if audio is currently playing
     */
    fun isPlaying(): Boolean {
        return isPlaying
    }

    /**
     * Resume playback
     */
    fun resume() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
        }
    }
}