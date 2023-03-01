package com.ns.theendcompose.ui.screens.details.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.ExternalId
import com.ns.theendcompose.data.model.ShareDetails
import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.ui.components.chips.GenreChip
import com.ns.theendcompose.ui.components.sections.GenresSection
import com.ns.theendcompose.ui.components.texts.AdditionalInfoText
import com.ns.theendcompose.ui.components.texts.ExpandableText
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.formattedRuntime
import com.ns.theendcompose.utils.timeString
import com.ns.theendcompose.utils.yearString
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MovieDetailsInfoSection(
    movieDetails: MovieDetails?,
    watchAtTime: Date?,
    modifier: Modifier = Modifier,
    imdbExternalId: ExternalId.Imdb? = null,
    onShareClicked: (ShareDetails) -> Unit = {}
) {
    val otherOriginalTitle: Boolean by derivedStateOf {
        movieDetails?.run { originalTitle.isNotEmpty() && title != originalTitle } ?: false
    }
    val watchAtTimeString = watchAtTime?.let { time ->
        stringResource(R.string.movie_details_watch_at, time.timeString())
    }
    Crossfade(modifier = modifier, targetState = movieDetails) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = details.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                        if (otherOriginalTitle) {
                            Text(text = details.originalTitle)
                        }
                        AdditionalInfoText(
                            modifier = Modifier.fillMaxWidth(),
                            infoTexts = details.run {
                                listOfNotNull(
                                    releaseDate?.yearString(),
                                    runtime?.formattedRuntime(),
                                    watchAtTimeString
                                )
                            }
                        )
                    }
                    AnimatedVisibility(
                        visible = imdbExternalId != null,
                        enter = fadeIn() + scaleIn(initialScale = 0.7f),
                        exit = fadeOut() + scaleOut()
                    ) {
                        IconButton(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ),
                            onClick = {
                                imdbExternalId?.let { id ->
                                    val shareDetails = ShareDetails(
                                        title = details.title,
                                        imdbId = id
                                    )

                                    onShareClicked(shareDetails)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "share",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                if (details.genres.isNotEmpty()) {
                    GenresSection(genres = details.genres)
                }
                Column(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                ) {
                    details.tagline?.let { tagline ->
                        if (tagline.isNotEmpty()) {
                            Text(
                                text = "\"$tagline\"",
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp
                            )
                        }
                    }

                    details.overview.let { overview ->
                        if (overview.isNotBlank()) {
                            ExpandableText(
                                modifier = Modifier.fillMaxWidth(),
                                text = overview
                            )
                        }
                    }
                }
            }
        }
    }
}