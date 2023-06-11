# Music Player

A music player for your local music library.

Tenets:
 - Local only: no internet connection will ever be established.
 - Read only: your music will be accessed only in read mode.
 - Stateless: no cache/database/whatever will be created. The metadata in your songs _are_ the database.
 - Imperfect: there will be use-cases that are not solved by this software, and that's fine.

## Features

TBD

## Roadmap

- Search
- Metadata
   - Disk n°
   - Lyrics
- Improve queue UI
   - Remaining songs/duration
   - Shuffle/repeat options
   - Drag & drop
- Improve player UI
   - Waveform in seeker
   - Improve spectrometer
- Improve library load speed and memory consumption
- Packaging
   - [ ] Flatpak
   - [ ] ...
- Integration with the OS
   - [ ] Media notification
   - [ ] Media keys
   - [ ] Background API?
