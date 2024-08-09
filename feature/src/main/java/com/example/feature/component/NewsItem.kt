package com.example.feature.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.theme.BaseTypography
import com.example.core.util.getTimeDiffLabel
import com.example.domain.model.NewsUiModel

@Composable
fun LazyItemScope.NewsItem(
    item: NewsUiModel,
    context: Context,
    currentTime: Long,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateItem()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.5f)
                .background(Color.LightGray),
            model = ImageRequest.Builder(context)
                .data(item.bannerUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = item.title,
                style = BaseTypography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                style = BaseTypography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            val timeDiff = currentTime - item.timeCreated
            Text(
                text = timeDiff.getTimeDiffLabel(),
                style = BaseTypography.labelMedium.copy(
                    color = Color.Gray
                )
            )
        }
    }
}

@Preview
@Composable
fun NewsItemPreview() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            NewsItem(
                item = NewsUiModel(
                    id = "",
                    title = "title",
                    description = "description",
                    bannerUrl = "",
                    timeCreated = 1532853058,
                    rank = 0
                ),
                context = LocalContext.current,
                currentTime = 1532856058
            )
        }
    }
}