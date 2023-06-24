package kr.co.bullets.statestatehoisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.bullets.statestatehoisting.ui.theme.ComposeUsageTheme

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
                    PyeongToSquareMeter()
                }
            }
        }
    }
}

@Composable
fun PyeongToSquareMeter() {
    var pyeong by rememberSaveable {
        mutableStateOf("23")
    }
    var squareMeter by rememberSaveable {
        mutableStateOf((23 * 3.306).toString())
    }

    // 단계 1: remember를 이용해 상태를 만들고 평 값을 입력하면
    // 제곱미터가 출력되도록 화면을 구성하시오.
    // 평을 제곱미터로 바꾸기 위해서는 3.306을 곱하면 됩니다.
//    Column(modifier = Modifier.padding(16.dp)) {
//        OutlinedTextField(
//            value = pyeong,
//            onValueChange = {
//                if (it.isBlank()) {
//                    pyeong = ""
//                    squareMeter = ""
//                    return@OutlinedTextField
//                }
//                val numericValue = it.toFloatOrNull() ?: return@OutlinedTextField
//                pyeong = it
//                squareMeter = (numericValue * 3.306).toString()
//            }, label = {
//                Text("평")
//            }
//        )
//        OutlinedTextField(
//            value = squareMeter,
//            onValueChange = {},
//            label = {
//                Text("제곱미터")
//            }
//        )
//    }

    // 단계 2: `Composable` 함수를 만들고 `Column`의 항목들을 옮기세요.
    // 단 상태는 옮기지 말아야 합니다.

    // 파라미터는 아래와 같이 구성합니다.
    // `pyeong: String, squareMeter: String, onPyeongChange: (String) -> Unit`
    PyeongToSquareMeterStateless(pyeong, squareMeter) {
        if (it.isBlank()) {
            pyeong = ""
            squareMeter = ""
            return@PyeongToSquareMeterStateless
        }
        val numericValue = it.toFloatOrNull() ?: return@PyeongToSquareMeterStateless
        pyeong = it
        squareMeter = (numericValue * 3.306).toString()
    }
}

@Composable
fun PyeongToSquareMeterStateless(
    pyeong: String,
    squareMeter: String,
    onPyeongChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = pyeong,
            onValueChange = onPyeongChange,
            label = {
                Text("평")
            }
        )
        OutlinedTextField(
            value = squareMeter,
            onValueChange = {},
            label = {
                Text("제곱미터")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        PyeongToSquareMeter()
    }
}