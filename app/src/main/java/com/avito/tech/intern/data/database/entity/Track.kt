package com.avito.tech.intern.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avito.tech.intern.data.dto.toTrackUI
import com.avito.tech.intern.presentation.ui.model.TrackUI
import java.time.LocalDateTime

@Entity
data class Track(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "cover") val cover: String,
    @ColumnInfo(name = "file_name") val file_name: String,
)


fun Track.toTrackUI(): TrackUI {
    return TrackUI(id = this.id, title = this.title, artist = this.artist, uri = this.file_name, cover = this.cover)
}
