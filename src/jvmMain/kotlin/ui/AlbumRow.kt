package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.Album
import data.Song

@Composable
fun AlbumRow(album: Album, songs: List<Song>, onSongSelected: (Song) -> Unit) {
    val cover = songs.firstOrNull { it.cover != null }?.cover

    Column {
        Row(Modifier) {
            Column(
                Modifier.width(128.dp + 32.dp).padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                key(album) {
                    AlbumCover(cover, 128.dp, MaterialTheme.shapes.medium)
                }
                Spacer(Modifier.height(16.dp))
                Text(album.title, textAlign = TextAlign.Center)
                Spacer(Modifier.height(8.dp))
                Text(album.artist.name, textAlign = TextAlign.Center)
            }
            Column {
                songs.forEach { song ->
                    SongRow(song, inAlbumContext = true, onSongSelected = { onSongSelected(song) })
                }
            }
        }
        Divider()
    }
}