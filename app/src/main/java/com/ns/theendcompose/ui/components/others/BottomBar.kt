package com.ns.theendcompose.ui.components.others

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ns.theendcompose.R
import com.ns.theendcompose.ui.screens.destinations.FavoriteScreenDestination
import com.ns.theendcompose.ui.screens.destinations.MovieScreenDestination
import com.ns.theendcompose.ui.screens.destinations.SearchScreenDestination
import com.ns.theendcompose.ui.screens.destinations.TvShowScreenDestination

@Composable
@Preview
fun BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String? = null,
    backQueueRoutes: List<String?> = emptyList(),
    visible: Boolean = true,
    onItemClicked: (String) -> Unit = {}
) {
    val bottomBarRoute = remember {
        mutableSetOf(
            MovieScreenDestination.route,
            TvShowScreenDestination.route,
            FavoriteScreenDestination.route,
            SearchScreenDestination.route
        )
    }

    val selectedRoute = when (currentRoute) {
        in bottomBarRoute -> currentRoute
        else -> {
            backQueueRoutes.firstOrNull { route ->
                route in bottomBarRoute
            } ?: MovieScreenDestination.route
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        NavigationBar(modifier = modifier) {
            TheEndNavBarItem(
                label = stringResource(id = R.string.movies_label),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Movie"
                    )
                },
                selected = selectedRoute == MovieScreenDestination.route,
                onClick = { onItemClicked(MovieScreenDestination.route) }
            )
            TheEndNavBarItem(
                label = stringResource(id = R.string.tv_series_label),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Tv Show"
                    )
                },
                selected = selectedRoute == TvShowScreenDestination.route,
                onClick = { onItemClicked(TvShowScreenDestination.route) }
            )
            TheEndNavBarItem(
                label = stringResource(id = R.string.favourites_label),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorites"
                    )
                },
                selected = selectedRoute == FavoriteScreenDestination.route,
                onClick = { onItemClicked(FavoriteScreenDestination.route) }
            )
            TheEndNavBarItem(
                label = stringResource(id = R.string.search_label),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                selected = selectedRoute == SearchScreenDestination.route,
                onClick = { onItemClicked(SearchScreenDestination.route) }
            )
        }
    }
}

@Composable
fun RowScope.TheEndNavBarItem(
    label: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        icon = icon
    )
}