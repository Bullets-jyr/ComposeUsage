package kr.co.bullets.effect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kr.co.bullets.effect.ui.theme.ComposeUsageTheme

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
                    EffectEx()
                }
            }
        }
    }
}

@Composable
fun EffectEx(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scaffoldState = rememberScaffoldState()

    // 단계 1: `LaunchedEffect`을 이용해서 스낵바를 이용해 봅시다.
    // 파라미터에는 `scaffoldState.snackbarHostState`를 전달합시다.
    // "헬로 컴포즈"라고 출력합시다.
    // `LaunchedEffect`는 `CouroutineScope`를 만들기 때문에 스코프를 별도로
    // 만들 필요는 없습니다.
    LaunchedEffect(scaffoldState.snackbarHostState) {
        scaffoldState.snackbarHostState.showSnackbar("헬로 컴포즈. Bullets-jyr")
    }

    // 단계 2: `DisposableEffect`를 호출하고 파리미터로 `lifecycleOwner`를
    // 전달합니다.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // SAM: Single Abstract Method
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("Effect", "ON_START")
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("Effect", "ON_STOP")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("Effect", "ON_PAUSE")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("Effect", "ON_RESUME")
                }
                else -> {
                    Log.d("Effect", "ELSE")
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // `LifecycleEventObserver`를 상속받고 두 상태에 대해 로그를 남깁니다.
    // `Lifecycle.Event.ON_START`, `Lifecycle.Event.ON_STOP`

    // 블록 내에서 `lifecycleOwner.lifecycle.addObserver`로 옵저버를 추가하고
    // onDispose에서 옵저버를 제거합니다.

    Scaffold(
        scaffoldState = scaffoldState
    ) {
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        EffectEx()
    }
}