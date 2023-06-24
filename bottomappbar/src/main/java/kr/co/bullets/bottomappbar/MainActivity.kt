package kr.co.bullets.bottomappbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kr.co.bullets.bottomappbar.ui.theme.ComposeUsageTheme

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
                    BottomAppBarEx()
                }
            }
        }
    }
}

@Composable
fun BottomAppBarEx() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var counter by remember { mutableStateOf(0) }

//    LaunchedEffect(scaffoldState.snackbarHostState) {
//        coroutineScope.launch {
//            scaffoldState.snackbarHostState.showSnackbar("안녕하세요")
//        }
//    }

    // 단계 1: `Scaffold`에 `scaffoldState`를 설정합니다.
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar() {
                Text("헬로")
                Button(onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("안녕하세요")
                    }
                }) {
                    Text("인사하기")
                }
                Button(
                    onClick = {
                        counter++
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("${counter}입니다")
                        }
                    }
                ) {
                    Text("더하기")
                }
                Button(
                    onClick = {
                        counter--
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("${counter}입니다")
                        }
                    }
                ) {
                    Text("빼기")
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "카운터는 ${counter}회입니다.",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        //        LaunchedEffect(scaffoldState.snackbarHostState) {
//            coroutineScope.launch {
//                scaffoldState.snackbarHostState.showSnackbar("안녕하세요")
//            }
//        }

//        coroutineScope.launch {
//            scaffoldState.snackbarHostState.showSnackbar("안녕하세요")
//        }
    }

    // 단계 2: `bottomBar` 파라미터에 `BottomAppBar`를 넣읍시다.
    // 내용은 텍스트와 버튼을 넣어 봅시다. 버튼에는 `snackBar`를
    // 연동해 메시지를 출력합니다.

    // 단계 3: 더하기와 빼기 버튼을 추가로 만들고 `MutableState`
    // 만듭시다. `Scaffold`의 `content`에 `Text`를 넣어 카운터를 출력하게
    // 합시다.
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        BottomAppBarEx()
    }
}