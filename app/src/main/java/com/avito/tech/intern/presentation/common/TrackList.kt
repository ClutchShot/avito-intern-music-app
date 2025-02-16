package com.avito.tech.intern.presentation.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.avito.tech.intern.R
import com.avito.tech.intern.presentation.nav.TrackDetails
import com.avito.tech.intern.presentation.ui.model.TrackUI

@Composable
fun TrackList(
    trackList: UiState<List<TrackUI>>,
    query: String,
    onValueChange: (String) -> Unit,
    onTrackClick: (TrackDetails) -> Unit,
) {
    val listState = rememberLazyListState()
    Column {
        SearchField(
            value = query,
            onValueChange = onValueChange
        )
        LazyColumn(
            state = listState
        ) {
            when (trackList) {
                is UiState.Success ->
                    itemsIndexed(trackList.data, key = { _, it -> it.id }) { id, track ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    onTrackClick(
                                        TrackDetails(
                                            track.id,
                                            track.title,
                                            track.artist,
                                            track.uri,
                                            track.cover
                                        )
                                    )
                                },
                            leadingContent = {
                                AsyncImage(
                                    model = track.cover,
                                    contentDescription = "track img",
                                )
                            },
                            headlineContent = { Text(track.title) },
                            supportingContent = { Text(track.artist) },
                        )
                    }

                is UiState.Error -> item { ErrorText() }
                UiState.Loading -> item {  Loading() }
            }
        }

    }
}


@Composable
fun SearchField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(stringResource(R.string.seach_label))
        },
        singleLine = true,
    )
}

@Composable
fun ErrorText()
{
    Box(modifier = Modifier.fillMaxSize()) {
            Text("Error",
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Composable
fun Loading()
{
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
                .size(72.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AvitoInternMusicAppTheme {
//        TrackList(UiState.Error(""), "", {}, {})
//    }
//}