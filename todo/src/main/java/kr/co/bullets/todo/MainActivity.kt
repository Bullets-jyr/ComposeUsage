package kr.co.bullets.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.bullets.todo.ui.theme.ComposeUsageTheme

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
                    TopLevel()
                }
            }
        }
    }
}

@Composable
fun TopLevel() {
    val (text, setText) = remember { mutableStateOf("") }
    val toDoList = remember { mutableStateListOf<ToDoData>() }
    // MutableStateList가 추가, 삭제, 변경되었을 때만 UI 갱신
    // 항목 하나의 값을 바꾸는 것보다 항목 자체를 바꾸는게 더 좋겠다라고 생각함

    // 단계 4: `onSubmit`, `onEdit`, `onToggle`, `onDelete`를
    // 만들어 `ToDo`에 연결합니다.
    val onSubmit: (String) -> Unit = { text ->
        val key = (toDoList.lastOrNull()?.key ?: 0) + 1
        toDoList.add(ToDoData(key, text))
        setText("")
    }

    val onToggle: (Int, Boolean) -> Unit = { key, checked ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList[i] = toDoList[i].copy(done = checked)
    }

    val onDelete: (Int) -> Unit = { key ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList.removeAt(i)
    }

    val onEdit: (Int, String) -> Unit = { key, text ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList[i] = toDoList[i].copy(text = text)
    }

    Scaffold {
        Column {
            ToDoInput(
                text = text,
                onTextChange = setText,
                onSubmit = onSubmit
            )
            // 단계 3: `LazyColumn`으로 `toDoList`를 표시합시다.
            // `key`를 `toDoData`의 `key`를 사용합니다.
            LazyColumn {
                items(toDoList, key = { it.key }) { toDoData ->
//                    Text(toDoData.text)
                    ToDo(
                        toDoData= toDoData,
                        onEdit = onEdit,
                        onToggle = onToggle,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUsageTheme {
        TopLevel()
    }
}

@Composable
fun ToDoInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {
            onSubmit(text)
        }) {
            Text("입력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoInputPreview() {
    ComposeUsageTheme {
        ToDoInput("테스트", {}, {})
    }
}

@Composable
fun ToDo(
    toDoData: ToDoData,
    onEdit: (key: Int, text: String) -> Unit = { _, _ -> },
    onToggle: (key: Int, checked: Boolean) -> Unit = { _, _ -> },
    onDelete: (key: Int) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp
    ) {

        // 단계 1: `Row`를 만들고 `toDoData.text`를 출력하고
        // 완료를 체크하는 체크박스, 수정 버튼, 삭제 버튼을 만드세요.
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = toDoData.text,
//                modifier = Modifier.weight(1f)
//            )
//            Text(
//                text = "완료"
//            )
//            Checkbox(
//                checked = toDoData.done,
//                onCheckedChange = { checked -> onToggle(toDoData.key, checked)}
//            )
//            Button(onClick = {}) {
//                Text(text = "수정")
//            }
//            Spacer(modifier = Modifier.size(4.dp))
//            Button(onClick = {}) {
//                Text(text = "삭제")
//            }
//        }

        // 단계 2: `Crossfade`를 통해 `isEditing`을 따라 다른
        // UI를 보여줍니다. `OutlinedTextField`와 `Button을
        // 넣어봅시다.
        Crossfade(targetState = isEditing) {
            when (it) {
                false -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = toDoData.text,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "완료"
                        )
                        Checkbox(
                            checked = toDoData.done,
                            onCheckedChange = { checked -> onToggle(toDoData.key, checked)}
                        )
                        Button(
                            onClick = {
                                isEditing = true
                            }
                        ) {
                            Text(text = "수정")
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(
                            onClick = {
                                onDelete(toDoData.key)
                            }
                        ) {
                            Text(text = "삭제")
                        }
                    }
                }
                true -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        var (newText, setNewText) = remember { mutableStateOf(toDoData.text) }

                        OutlinedTextField(
                            value = newText,
                            onValueChange = setNewText,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(
                            onClick = {
                                onEdit(toDoData.key, newText)
                                isEditing = false
                            }
                        ) {
                            Text("완료")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoPreview() {
    ComposeUsageTheme {
        ToDo(ToDoData(1, "nice", true))
    }
}

// immutable,
data class ToDoData(
    val key: Int,
    val text: String,
    val done: Boolean = false
)