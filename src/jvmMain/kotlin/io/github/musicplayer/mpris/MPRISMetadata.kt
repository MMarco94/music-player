package io.github.musicplayer.mpris

import io.github.musicplayer.data.Song
import io.github.musicplayer.utils.variant
import org.freedesktop.dbus.types.Variant
import kotlin.time.Duration

// See https://specifications.freedesktop.org/mpris-spec/2.2/Track_List_Interface.html#Mapping:Metadata_Map
data class MPRISMetadata(
    // TODO: this supports lyrics!
    // TODO: artUrl
    val trackId: String,
    val length: Duration,
    val artist: List<String>,
    val albumArtist: List<String>,
    val album: String,
    val title: String,
    val discNumber: Long?,
) {

    val variant: Variant<*> = buildMap {
        put("mpris:trackid", trackId.variant())
        put("mpris:length", length.inWholeMicroseconds.variant())
        put("xesam:artist", artist.variant())
        put("xesam:albumArtist", albumArtist.variant())
        put("xesam:album", album.variant())
        put("xesam:title", title.variant())
        if (discNumber != null) {
            put("xesam:discNumber", discNumber.variant())
        }
    }.variant()
}

fun Song.mprisMetadata(): MPRISMetadata {
    return MPRISMetadata(
        // TODO: a better ID
        trackId = "/io/github/MMarco94/music-player/" + hashCode().toString(),
        length = length,
        artist = listOf(artist.name),
        albumArtist = listOf(artist.name),
        album = album.title,
        title = title,
        discNumber = track?.toLong(),
    )
}