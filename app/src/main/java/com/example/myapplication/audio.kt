import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.myapplication.SongInformation

class AudioFileUtils {

    companion object {
        fun getAudioFiles(context: Context): MutableList<SongInformation> {
            val audioFiles = mutableListOf<SongInformation>()
            val contentResolver: ContentResolver = context.contentResolver

            // Columns to query from the MediaStore for audio files
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM
            )

            // Query the MediaStore for audio files
            val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            if (cursor != null) {
                Log.d("AudioFileUtils", "Cursor count: ${cursor.count}") // Log cursor count
                if (cursor.moveToFirst()) {
                    do {
                        // Extract the audio file data from the cursor
                        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                        val displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                        val duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                        val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        val album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                        Log.d("AudioFileUtils", "Found song: $displayName, Path: $path") // Log song details

                        // Create an AudioFile object and add it to the list
                        val audioFile = SongInformation(id, displayName, duration, path, album)

                        if (!displayName.startsWith("AUD")) {
                            audioFiles.add(audioFile)
                        }
                    } while (cursor.moveToNext())
                }
                cursor.close()
            } else {
                Log.d("AudioFileUtils", "Cursor is null")
            }

            return audioFiles
        }
    }
}
