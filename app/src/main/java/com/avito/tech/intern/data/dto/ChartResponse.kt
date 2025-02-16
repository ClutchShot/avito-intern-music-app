import com.avito.tech.intern.data.dto.Albums
import com.avito.tech.intern.data.dto.Artists
import com.avito.tech.intern.data.dto.Playlists
import com.avito.tech.intern.data.dto.Podcasts
import com.avito.tech.intern.data.dto.Tracks


data class ChartResponse(
    val tracks: Tracks,
    val albums: Albums,
    val artists: Artists,
    val playlists: Playlists,
    val podcasts: Podcasts
)


