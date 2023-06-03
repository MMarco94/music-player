package audio

import androidx.compose.ui.graphics.toComposeImageBitmap
import com.mpatric.mp3agic.Mp3File
import org.jetbrains.skia.Image
import java.io.File

class RawImage(
    val bytes: ByteArray,
) {
    fun decode() = Image.makeFromEncoded(bytes).toComposeImageBitmap()

    override fun equals(other: Any?): Boolean {
        return other is RawImage && other.bytes.contentEquals(bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}

data class RawMetadataSong(
    val file: File,
    val track: Int?,
    val title: String?,
    val album: String?,
    val artist: String?,
    val albumArtist: String?,
    val cover: RawImage?,
) {
    val nnTitle get() = title ?: file.nameWithoutExtension
    val nnAlbum get() = album ?: "Unknown"
    val nnArtist get() = artist ?: "Unknown"
    val nnAlbumArtist get() = albumArtist ?: nnArtist

    companion object {
        fun fromMp3(file: File): RawMetadataSong {
            val mp3 = Mp3File(file, 1.shl(16), false)
            val id3v2Tag = mp3.id3v2Tag
            val id3v1Tag = mp3.id3v1Tag
            return if (id3v2Tag != null) {
                RawMetadataSong(
                    file,
                    id3v2Tag.track.toIntOrNull(),
                    id3v2Tag.title,
                    id3v2Tag.album,
                    id3v2Tag.artist,
                    id3v2Tag.albumArtist,
                    id3v2Tag.albumImage?.let { RawImage(it) },
                )
            } else if (id3v1Tag != null) {
                RawMetadataSong(
                    file,
                    id3v1Tag.track.toIntOrNull(),
                    id3v1Tag.title,
                    id3v1Tag.album,
                    id3v1Tag.artist,
                    null,
                    null,
                )
            } else {
                RawMetadataSong(
                    file,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                )
            }
        }
    }
}
