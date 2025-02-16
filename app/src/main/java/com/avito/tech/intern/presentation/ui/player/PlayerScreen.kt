package com.avito.tech.intern.presentation.ui.player


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.avito.tech.intern.presentation.theme.AvitoInternMusicAppTheme
import java.time.Duration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.avito.tech.intern.R
import com.avito.tech.intern.presentation.common.DownloadState
import com.avito.tech.intern.presentation.nav.TrackDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update



@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    trackDetails: TrackDetails,
) {
    val context = LocalContext.current
    var isLocal by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        playerViewModel.initializeMediaController(context)
        if (playerViewModel.isTrackLocal(trackDetails)){
            isLocal =  true
            playerViewModel.setLocalTrack(trackDetails)
        }
        else{
            playerViewModel.setTrack(trackDetails)
        }
    }

    val isPlaying = playerViewModel.isPlaying.collectAsState()
    val downloadState = playerViewModel.downloadState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = modifier
                .weight(10f)
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = trackDetails.coverBig,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth(),
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TrackDescription(trackDetails.title, trackDetails.artist)
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(10f)
        ) {
            PlayerSlider(
                onSeekingStarted = {},
                onSeekingFinished = {}
            )
            PlayerButtons(
                hasNext = false,
                isPlaying = isPlaying.value,
                onPlayPress = { playerViewModel.play()},
                onPausePress = { playerViewModel.pause() },
                onAdvanceBy = {},
                onRewindBy = {},
                onNext = {},
                onPrevious = {},
                isLocal = isLocal,
                downloadState = downloadState.value,
                onDownload = { playerViewModel.downloadFileToInternalStorage() },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}




@Composable
private fun TrackDescription(
    title: String,
    artist: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.headlineSmall,
) {
    Text(
        text = title,
        style = titleTextStyle,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.basicMarquee()
    )
    Text(
        text = artist,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1
    )
}

@Composable
private fun PlayerSlider(
    onSeekingStarted: () -> Unit,
    onSeekingFinished: (newElapsed: Duration) -> Unit,
    timeElapsed: Duration = Duration.ofSeconds(0),
    trackDuration: Duration = Duration.ofSeconds(30),
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        var sliderValue by remember(timeElapsed) { mutableStateOf(timeElapsed) }
        val maxRange = trackDuration.toSeconds().toFloat()

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "${sliderValue.formatString()} • ${trackDuration.formatString()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Slider(
            value = sliderValue.seconds.toFloat(),
            valueRange = 0f..maxRange,
            onValueChange = {
                onSeekingStarted()
                sliderValue = Duration.ofSeconds(it.toLong())
            },
            onValueChangeFinished = { onSeekingFinished(sliderValue) }
        )
    }
}

@Composable
private fun PlayerButtons(
    hasNext: Boolean,
    isPlaying: Boolean,
    onPlayPress: () -> Unit,
    onPausePress: () -> Unit,
    onAdvanceBy: (Duration) -> Unit,
    onRewindBy: (Duration) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    downloadState: DownloadState,
    isLocal : Boolean,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp,
) {
    val primaryButtonModifier = Modifier
        .size(playerButtonSize)
        .background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = CircleShape
        )
        .semantics { role = Role.Button }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val sideButtonsModifier = Modifier
            .size(sideButtonSize)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                shape = CircleShape
            )
            .semantics { role = Role.Button }


        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = stringResource(R.string.cd_skip_previous),
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = sideButtonsModifier
                .clickable(enabled = isPlaying, onClick = onPrevious)
                .alpha(if (isPlaying) 1f else 0.25f)
        )
        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = stringResource(R.string.cd_replay10),
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = sideButtonsModifier
                .clickable {
                    onRewindBy(Duration.ofSeconds(10))
                }
        )
        if (isPlaying) {
            Image(
                imageVector = Icons.Outlined.Pause,
                contentDescription = stringResource(R.string.cd_pause),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = primaryButtonModifier
                    .padding(8.dp)
                    .clickable {
                        onPausePress()
                    }
            )
        } else {
            Image(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = stringResource(R.string.cd_play),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = primaryButtonModifier
                    .padding(8.dp)
                    .clickable {
                        onPlayPress()
                    }
            )
        }
        Image(
            imageVector = Icons.Filled.Forward10,
            contentDescription = stringResource(R.string.cd_forward10),
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = sideButtonsModifier
                .clickable {
                    onAdvanceBy(Duration.ofSeconds(10))
                }
        )
        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = stringResource(R.string.cd_skip_next),
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = sideButtonsModifier
                .clickable(enabled = hasNext, onClick = onNext)
                .alpha(if (hasNext) 1f else 0.25f)
        )
    }

    if (!isLocal){
        when (downloadState){
            DownloadState.Error -> {
                Toast.makeText(LocalContext.current, "Download error", Toast.LENGTH_SHORT).show()
                Image(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = stringResource(R.string.download),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = primaryButtonModifier
                        .padding(8.dp)
                        .clickable {
                            onDownload()
                        }
                )
            }
            DownloadState.Loading -> {
                CircularProgressIndicator(
                    modifier = primaryButtonModifier,
                )
            }
            DownloadState.Ready -> {
                Image(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = stringResource(R.string.download),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = primaryButtonModifier
                        .padding(8.dp)
                        .clickable {
                            onDownload()
                        }
                )
            }
            DownloadState.Success -> {
                Toast.makeText(LocalContext.current, "Download complete", Toast.LENGTH_SHORT).show()
                Image(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = stringResource(R.string.download),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = primaryButtonModifier
                        .padding(8.dp)
                        .clickable {
                            onDownload()
                        }
                )
            }
        }

    }

}


fun Duration.formatString(): String {
    val minutes = this.toMinutes().toString().padStart(2, '0')
    val secondsLeft = (this.toSeconds() % 60).toString().padStart(2, '0')
    return "$minutes:$secondsLeft"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvitoInternMusicAppTheme {
//        PlayerScreen()
    }
}