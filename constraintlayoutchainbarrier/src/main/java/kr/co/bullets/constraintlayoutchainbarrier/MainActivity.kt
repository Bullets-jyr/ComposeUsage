package kr.co.bullets.constraintlayoutchainbarrier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import kr.co.bullets.constraintlayoutchainbarrier.ui.theme.ComposeUsageTheme

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
                    ConstraintLayoutEx()
                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutEx() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (redBox, yellowBox, magentaBox, text) = createRefs()

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .constrainAs(redBox) {
                    start.linkTo(parent.start, margin = 38.dp)
                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    start.linkTo(parent.start, margin = 32.dp)
                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .constrainAs(magentaBox) {
                    start.linkTo(parent.start, margin = 50.dp)
                }
        )

        // 단계 1: `createVerticalChain`, `createHorizontalChain`를
        // 이용해서 세 박스의 레퍼런스를 연결해봅시다.
//        createVerticalChain(redBox, yellowBox, magentaBox)
//        createHorizontalChain(redBox, yellowBox, magentaBox)

        // 단계 2: `createHorizontalChain`를 사용하고 `chainStyle`
        // 키워드 파라미터를 추가합시다.
        // `ChainStyle.Packed`,`ChainStyle.Spread`,
        // `ChainStyle.SpreadInside`등을 지정해봅시다.
//        createHorizontalChain(redBox, yellowBox, magentaBox, chainStyle = ChainStyle.SpreadInside)
        createVerticalChain(redBox, yellowBox, magentaBox, chainStyle = ChainStyle.SpreadInside)

        // 단계 3: 세 박스의 top을 parent.top에 연결하고 각각
        // 다른 마진을 줍시다.

        // 단계 4: `createBottomBarrier`로 배리어를 만듭시다.
        // 이는 모든 박스들의 하단을 포함하는 배리어입니다.
//        val barrier = createBottomBarrier(redBox, yellowBox, magentaBox)
//        val barrier = createTopBarrier(redBox, yellowBox, magentaBox)
//        val barrier = createStartBarrier(redBox, yellowBox, magentaBox)
        val barrier = createEndBarrier(redBox, yellowBox, magentaBox)

        // 단계 5: `Text` 하나 만들고 top을 박스 베리어로 지정합니다.
        Text(
            text = "나라말슴이 듕귁에 달아 어린 백성이 나라말슴이 듕귁에 달아 어린 백성이 나라말슴이 듕귁에 달아 어린 백성이 나라말슴이 듕귁에 달아 어린 백성이 나라말슴이 듕귁에 달아 어린 백성이",
            modifier = Modifier
                .width(100.dp)
                .constrainAs(text) {
                start.linkTo(barrier)
                top.linkTo(yellowBox.bottom)
            }
        )

        // 단계 6: 체이닝 방향이나 베리어 방향을 바꾸어 보며 다양하게 테스트해봅시다.
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        ConstraintLayoutEx()
    }
}