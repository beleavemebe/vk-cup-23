package com.example.app_semi_final.feature.rating.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_semi_final.R
import com.example.app_semi_final.feature.rating.NO_STARS
import com.example.app_semi_final.feature.rating.Rating
import com.example.app_semi_final.feature.rating.RatingViewModel
import com.example.app_semi_final.ui.theme.Accent
import com.example.app_semi_final.ui.theme.TextSecondary
import com.example.app_semi_final.ui.theme.Typography
import com.example.app_semi_final.ui.theme.VKCupTheme

@Composable
fun RatingScreen() {
    val viewModel = viewModel<RatingViewModel>()
    val state by viewModel.state.collectAsState()

    LazyColumn {
        items(state.content.size) { index ->
            val element = state.content[index]

            RatePost(
                pRating = element,
                onRatingChanged = { rating ->
                    viewModel.changeRating(index, rating)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RatePost(
    pRating: Rating,
    onRatingChanged: (Int) -> Unit,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        AnimatedContent(targetState = pRating) { rating ->
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val stringResId =
                        if (rating.stars == NO_STARS) R.string.rate_post else R.string.thx
                    Text(
                        text = stringResource(id = stringResId),
                        style = Typography.body2.copy(color = TextSecondary),
                        modifier = Modifier.weight(1f)
                    )

                    RatingBar(
                        rating = rating.stars,
                        onRatingChanged = onRatingChanged
                    )
                }
            }
        }
    }
}

const val MAX_STARS = 5

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
) {
    val starSize = 20.dp
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(rating) { i ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star_filled),
                contentDescription = null,
                tint = Accent,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        onRatingChanged(i + 1)
                    }
            )
        }

        repeat(MAX_STARS - rating) { i ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star_outlined),
                contentDescription = null,
                tint = Accent,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        onRatingChanged(rating + i + 1)
                    }
            )
        }
    }
}


@Preview
@Composable
fun RatingBarPreview() = VKCupTheme {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
        RatingBar(rating = 1) {

        }
        RatingBar(rating = 2) {

        }
        RatingBar(rating = 3) {

        }
        RatingBar(rating = 4) {

        }
        RatingBar(rating = 5) {

        }
    }
}