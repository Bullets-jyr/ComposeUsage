package kr.co.bullets.constraintlayoutusage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import kr.co.bullets.constraintlayoutusage.ui.theme.ComposeUsageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUsageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CardEx(cardData)
                        CardEx(cardData)
                        CardEx(cardData)
                    }
                }
            }
        }
    }
}

@Composable
fun CardEx(cardData: CardData) {
    val placeHolderColor = Color(0x33000000)

    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(4.dp),
    ) {
        // 단계 1: 아래의 Row 레이아웃을 ConstraintLayout로 바꾸어 봅시다.
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
//                .height(100.dp)
        ) {
            val (profileImage, author, description) = createRefs()

            AsyncImage(
                model = cardData.imageUri,
                contentDescription = cardData.imageDescription,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(color = placeHolderColor),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .constrainAs(profileImage) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start, margin = 8.dp)
//                        linkTo(parent.top, parent.bottom)
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = cardData.author,
                modifier = Modifier.constrainAs(author) {
                    linkTo(
                        profileImage.end,
                        parent.end,
                        startMargin = 8.dp,
                        endMargin = 8.dp
                    )
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = cardData.description,
                modifier = Modifier.constrainAs(description) {
                    linkTo(
                        profileImage.end,
                        parent.end,
                        startMargin = 8.dp,
                        endMargin = 8.dp
                    )
                    width = Dimension.fillToConstraints
                }
            )

            val chain = createVerticalChain(
                author,
                description,
                chainStyle = ChainStyle.Packed
            )

            constrain(chain) {
                top.linkTo(parent.top, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }
        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(8.dp)
//        ) {
//            AsyncImage(
//                model = cardData.imageUri,
//                contentDescription = cardData.imageDescription,
//                contentScale = ContentScale.Crop,
//                placeholder = ColorPainter(color = placeHolderColor),
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .size(40.dp)
//            )
//            Spacer(modifier = Modifier.size(8.dp))
//            Column {
//                Text(text = cardData.author)
//                Text(text = cardData.description)
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        CardEx(cardData)
    }
}

data class CardData(
    val imageUri: String,
    val imageDescription: String,
    val author: String,
    val description: String
)

val cardData = CardData(
    imageUri = "https://github-production-user-asset-6210df.s3.amazonaws.com/117099209/245334276-ec4da51f-7b72-4e1e-9d73-749dc14fbc11.jpg",
    imageDescription = "엔텔로프 캐년",
    author = "Dalinaum",
    description = "엔텔로프 캐년은 죽기 전에 꼭 봐야할 절경으로 소개되었습니다."
)