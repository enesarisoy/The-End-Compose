package com.ns.theendcompose.utils

import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ns.theendcompose.LocalImageUrlParser

@Composable
inline fun rememberTmdbImagePainter(
    path: String?,
    type: ImageUrlParser.ImageType,
    preferredSize: Size,
    strategy: ImageUrlParser.MatchingStrategy = ImageUrlParser.MatchingStrategy.FirstBiggerWidth,
    builder: ImageRequest.Builder.() -> Unit = {}
): AsyncImagePainter {
    val imageUrlParser = LocalImageUrlParser.current

    val imageUrl = imageUrlParser?.getImageUrl(
        path = path,
        type = type,
        preferredSize = preferredSize,
        strategy = strategy
    )

    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .apply {
                builder(this)
            }.build()
    )

}

@Composable
fun TmdbImage(
    imagePath: String?,
    imageType: ImageUrlParser.ImageType,
    modifier: Modifier = Modifier,
    strategy: ImageUrlParser.MatchingStrategy = ImageUrlParser.MatchingStrategy.FirstBiggerWidth,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    BoxWithConstraints(modifier = Modifier) {
        val (maxWith, maxHeight) = getMaxSizeInt()

        val painter = rememberTmdbImagePainter(
            path = imagePath,
            type = imageType,
            preferredSize = Size(maxWith, maxHeight),
            strategy = strategy
        ) {
            builder()
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = colorFilter,
            contentScale = contentScale
        )

    }
}