package com.ns.theendcompose.ui.components.others
//
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ns.theendcompose.R
//import com.ns.theendcompose.ui.screens.destinations.FavoritesScreenDestination
import com.ns.theendcompose.ui.screens.destinations.MovieScreenDestination
import com.ns.theendcompose.ui.screens.destinations.SearchScreenDestination
import com.ns.theendcompose.ui.screens.destinations.TvShowScreenDestination
//
//@Composable
//@Preview
fun BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String? = null,
    backQueueRoutes: List<String?> = emptyList(),
    visible: Boolean = true,
    onItemClicked: (String) -> Unit = {}
) {
//    val bottomBarRoute = remember {
//        mutableSetOf(
//            MovieScreenDestination.route,
//            TvShowScreenDestination.route,
//            FavoritesScreenDestination.route,
//            SearchScreenDestination.route
//        )
//    }
//
//    val selectedRoute = when (currentRoute) {
//        in bottomBarRoute -> currentRoute
//        else -> {
//            backQueueRoutes.firstOrNull { route ->
//                route in bottomBarRoute
//            } ?: MovieScreenDestination.route
//        }
//    }
//
//    AnimatedVisibility(
//        visible = visible,
//        enter = slideInVertically { it },
//        exit = slideOutVertically { it }
//    ) {
//        NavigationBar(modifier = modifier) {
//            TheEndNavBarItem(
//                label = stringResource(id = R.string.movies_label),
//                selectedIcon = Icons.Filled.Movie,
//                unSelectedIcon = Icons.Outlined.Movie,
//                selected = selectedRoute == MovieScreenDestination.route,
//                onClick = { onItemClicked(MovieScreenDestination.route) }
//            )
//            TheEndNavBarItem(
//                label = stringResource(id = R.string.tv_series_label),
//                selectedIcon = Icons.Filled.SmartDisplay,
//                unSelectedIcon = Icons.Outlined.SmartDisplay,
//                selected = selectedRoute == TvShowScreenDestination.route,
//                onClick = { onItemClicked(TvShowScreenDestination.route) }
//            )
//            TheEndNavBarItem(
//                label = stringResource(id = R.string.favourites_label),
//                selectedIcon = Icons.Filled.Favorite,
//                unSelectedIcon = Icons.Outlined.FavoriteBorder,
//                selected = selectedRoute == FavoritesScreenDestination.route,
//                onClick = { onItemClicked(FavoritesScreenDestination.route) }
//            )
//            TheEndNavBarItem(
//                label = stringResource(id = R.string.search_label),
//                selectedIcon = Icons.Filled.ZoomIn,
//                unSelectedIcon = Icons.Outlined.Search,
//                selected = selectedRoute == SearchScreenDestination.route,
//                onClick = { onItemClicked(SearchScreenDestination.route) }
//            )
//        }
//    }
//}
//
//@Composable
//fun RowScope.TheEndNavBarItem(
//    label: String,
//    selected: Boolean,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit = {},
//    selectedIcon: ImageVector,
//    unSelectedIcon: ImageVector,
//    contentDescription: String? = null
//) {
//    NavigationBarItem(
//        selected = selected,
//        onClick = onClick,
//        label = { Text(text = label) },
//        icon = {
//            Icon(
//                imageVector = if (selected) selectedIcon else unSelectedIcon,
//                contentDescription = contentDescription
//            )
//        }
//    )
}