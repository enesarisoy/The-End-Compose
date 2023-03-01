package com.ns.theendcompose.ui.screens.discover.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ns.theendcompose.R
import com.ns.theendcompose.ui.components.lists.ProvidersSourceList
import com.ns.theendcompose.ui.components.others.LabeledSwitch
import com.ns.theendcompose.ui.components.others.SectionDivider
import com.ns.theendcompose.ui.components.others.VoteRangeSlider
import com.ns.theendcompose.ui.components.sections.ExpandableSection
import com.ns.theendcompose.ui.components.selectors.DateRangeSelector
import com.ns.theendcompose.ui.components.selectors.GenresSelector
import com.ns.theendcompose.ui.screens.discover.movies.MovieFilterState
import com.ns.theendcompose.ui.theme.spacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterMoviesModalBottomSheetContent(
    filterState: MovieFilterState,
    sheetState: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onSaveFilterClick: (MovieFilterState) -> Unit = {}
) {
    var currentFilterState by remember(filterState, sheetState.isVisible) {
        mutableStateOf(filterState)
    }

    val enableSaveButton = currentFilterState != filterState

    var genresSectionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var watchProvidersSectionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var voteRangeSectionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var dateSectionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var otherSectionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(top = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.width(64.dp),
            thickness = 4.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onCloseClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close filter",
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            ExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_filter_bottom_sheet_genres_section_label),
                infoText = stringResource(R.string.movie_filter_bottom_sheet_genres_info_label),
                expanded = genresSectionExpanded,
                onClick = { genresSectionExpanded = !genresSectionExpanded },
                trailing = {
                    SectionDivider(modifier = Modifier.fillMaxWidth())
                }
            ) {
                GenresSelector(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.extraSmall,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium,
                        ),
                    genres = currentFilterState.availableGenres,
                    selectedGenres = currentFilterState.selectedGenres,
                    onGenreClick = { genre ->
                        val selectedGenres = currentFilterState.selectedGenres.run {
                            if (genre in this) {
                                minus(genre)
                            } else {
                                plus(genre)
                            }
                        }
                        currentFilterState = currentFilterState.copy(
                            selectedGenres = selectedGenres
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }

            ExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_filter_bottom_sheet_availability_label),
                infoText = stringResource(R.string.movie_filter_bottom_sheet_availability_info_label),
                expanded = watchProvidersSectionExpanded,
                onClick = { watchProvidersSectionExpanded = !watchProvidersSectionExpanded },
                trailing = {
                    SectionDivider(modifier = Modifier.fillMaxWidth())
                }
            ) {
                ProvidersSourceList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.extraSmall,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    availableProvidersSources = currentFilterState.availableWatchProviders,
                    selectedProvidersSources = currentFilterState.selectedWatchProviders
                ) { selectedProvider ->
                    val selectedProviders = currentFilterState.selectedWatchProviders.run {
                        if (selectedProvider in this) {
                            minus(selectedProvider)
                        } else {
                            plus(selectedProvider)
                        }
                    }
                    currentFilterState = currentFilterState.copy(
                        selectedWatchProviders = selectedProviders
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }

            ExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_filter_bottom_sheet_score_section_label),
                infoText = stringResource(R.string.movie_filter_bottom_sheet_score_info_label),
                expanded = voteRangeSectionExpanded,
                onClick = { voteRangeSectionExpanded = !voteRangeSectionExpanded },
                trailing = {
                    SectionDivider(modifier = Modifier.fillMaxWidth())
                }
            ) {
                VoteRangeSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.extraSmall,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    voteRange = currentFilterState.voteRange,
                    onCurrentVoteRangeChange = { voteRange ->
                        currentFilterState = currentFilterState.copy(
                            voteRange = currentFilterState.voteRange.copy(
                                current = voteRange
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }

            ExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_filter_bottom_sheet_date_section_label),
                infoText = stringResource(R.string.movie_filter_bottom_sheet_date_info_label),
                expanded = dateSectionExpanded,
                onClick = { dateSectionExpanded = !dateSectionExpanded },
                trailing = {
                    SectionDivider(modifier = Modifier.fillMaxWidth())
                }
            ) {
                DateRangeSelector(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.extraSmall,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    fromDate = currentFilterState.releaseDateRange.from,
                    toDate = currentFilterState.releaseDateRange.to,
                    onFromDateChanged = { date ->
                        currentFilterState = currentFilterState.copy(
                            releaseDateRange = currentFilterState.releaseDateRange.copy(
                                from = date
                            )
                        )
                    },
                    onToDateChanged = { date ->
                        currentFilterState = currentFilterState.copy(
                            releaseDateRange = currentFilterState.releaseDateRange.copy(
                                to = date
                            )
                        )
                    },
                    onFromDateClearClicked = {
                        currentFilterState = currentFilterState.copy(
                            releaseDateRange = currentFilterState.releaseDateRange.copy(
                                from = null
                            )
                        )
                    },
                    onToDateClearClicked = {
                        currentFilterState = currentFilterState.copy(
                            releaseDateRange = currentFilterState.releaseDateRange.copy(
                                to = null
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }

            ExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.movie_filter_bottom_sheet_other_section_label),
                infoText = stringResource(R.string.movie_filter_bottom_sheet_other_info_label),
                expanded = otherSectionExpanded,
                onClick = { otherSectionExpanded = !otherSectionExpanded }
            ) {
                LabeledSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    label = stringResource(R.string.movie_filter_bottom_sheet_posters_switch_text),
                    checked = currentFilterState.showOnlyWithPoster,
                    onCheckedChanged = { show ->
                        currentFilterState = currentFilterState.copy(
                            showOnlyWithPoster = show
                        )
                    }
                )

                LabeledSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    label = stringResource(R.string.movie_filter_bottom_sheet_scores_switch_text),
                    checked = currentFilterState.showOnlyWithScore,
                    onCheckedChanged = { show ->
                        currentFilterState = currentFilterState.copy(
                            showOnlyWithScore = show
                        )
                    }
                )

                LabeledSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        ),
                    label = stringResource(R.string.movie_filter_bottom_sheet_overview_switch_text),
                    checked = currentFilterState.showOnlyWithOverview,
                    onCheckedChanged = { show ->
                        currentFilterState = currentFilterState.copy(
                            showOnlyWithOverview = show
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
                enabled = enableSaveButton,
                onClick = { onSaveFilterClick(currentFilterState) }) {
                Text(text = stringResource(R.string.movie_filter_bottom_sheet_save_button_label))
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
                onClick = {
                    currentFilterState = currentFilterState.clear()
                }) {
                Text(text = stringResource(R.string.movie_filter_bottom_sheet_clear_button_label))
            }
        }
    }
}