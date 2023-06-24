package kr.co.bullets.snackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kr.co.bullets.snackbar.ui.theme.ComposeUsageTheme

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
                    SnackbarEx()
                }
            }
        }
    }
}

@Composable
fun SnackbarEx() {
    var counter by remember { mutableStateOf(0) }

    // 단계 3: coroutineScope를 만듭시다.
    // `rememberCoroutineScope`를 사용합니다.
    val coroutineScope = rememberCoroutineScope()

    // 단계 1: scaffoldState를 만들고 Scaffold에 설정합시다.
    // scaffoldState를 만들기 위해 `rememberScaffoldState`를 사용합니다.
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        // 단계 2: "더하기" 버튼을 만들어 봅시다.
        // action에서 counter를 증가시킵시다.

//        LaunchedEffect(scaffoldState.snackbarHostState) {
//            coroutineScope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(
//                    message = "카운터는 ${counter}입니다.",
//                    actionLabel = "닫기",
//                    duration = SnackbarDuration.Short
//                )
//            }
//        }

        Button(onClick = {
            counter++
            coroutineScope.launch {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = "카운터는 ${counter}입니다.",
                    actionLabel = "닫기",
                    duration = SnackbarDuration.Short
                )
                when (result) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {}
                }
            }
        }) {
            Text("더하기")
        }

        // 단계 4: 버튼의 onClick에서 `coroutineScope.launch`를
        // 사용합니다.

        // 단계 5: 스낵바를 사용하기 위해 `scaffoldState.snackbarHostState.showSnackbar`
        // 사용합니다.

        // `message`에 카운터를 출력합시다.
        // `actionLabel`를 "닫기"로 지정합시다.
        // `duration`에 `SnackbarDuration.Short`를 사용합니다.
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        SnackbarEx()
    }
}