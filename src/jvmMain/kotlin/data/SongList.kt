package data

import orNoop

data class SongListOptions(
    val artistSorter: ArtistSorter = ArtistSorter.ALPHABETICAL,
    val albumSorter: AlbumSorter = AlbumSorter.YEAR,
    val songSorter: SongSorter = SongSorter.ALPHABETICAL,
    val songSorterInAlbum: SongSorter = SongSorter.TRACK,
    val artistFilter: Artist? = null,
    val albumFilter: Album? = null,
) {
    val isInAlbumMode: Boolean
        get() {
            return albumSorter != AlbumSorter.NONE || albumFilter != null
        }
    val isInArtistMode: Boolean
        get() {
            return !isInAlbumMode && (artistSorter != ArtistSorter.NONE || artistFilter != null)
        }

    fun withArtistFilter(artistFilter: Artist?): SongListOptions {
        return if (artistFilter == null) {
            copy(artistFilter = null, albumFilter = null)
        } else copy(
            artistFilter = artistFilter,
            albumFilter = if (albumFilter != null && albumFilter.artist == artistFilter) albumFilter
            else null
        )
    }

    fun withAlbumFilter(albumFilter: Album?): SongListOptions {
        return if (albumFilter == null) {
            copy(albumFilter = null)
        } else copy(
            artistFilter = albumFilter.artist,
            albumFilter = albumFilter
        )
    }

    fun withSongSorter(sorter: SongSorter): SongListOptions {
        return copy(
            songSorter = if (sorter.inAlbumOnly) songSorter else sorter,
            songSorterInAlbum = sorter,
        )
    }
}

sealed interface SongListItem {
    data class ArtistListItem(val artist: Artist, val songs: List<Song>) : SongListItem
    data class AlbumListItem(val album: Album, val songs: List<Song>) : SongListItem
    data class SingleSongListItem(
        val song: Song
    ) : SongListItem
}

fun Library.filterAndSort(
    options: SongListOptions,
): Library {
    return filter(options.artistFilter, options.albumFilter)
        .sort(
            options.artistSorter.comparator.orNoop(),
            options.albumSorter.comparator.orNoop(),
            options.songSorter.comparator.orNoop(),
            options.songSorterInAlbum.comparator.orNoop(),
        )
}

fun Library.toListItems(
    options: SongListOptions,
): List<SongListItem> {
    return if (options.isInAlbumMode) {
        albums.map { album ->
            SongListItem.AlbumListItem(
                album,
                songsByAlbum.getValue(album),
            )
        }
    } else if (options.isInArtistMode) {
        artists.map { artist ->
            SongListItem.ArtistListItem(
                artist,
                songsByArtist.getValue(artist),
            )
        }
    } else {
        songs.map { song ->
            SongListItem.SingleSongListItem(song)
        }
    }
}