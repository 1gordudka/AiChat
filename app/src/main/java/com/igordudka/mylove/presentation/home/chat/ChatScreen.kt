package com.igordudka.mylove.presentation.home.chat

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.igordudka.mylove.data.database.ChatMessage
import com.igordudka.mylove.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import com.igordudka.mylove.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = hiltViewModel(),
    isDark: Boolean,
    goToSettings: () -> Unit
) {

    val context = LocalContext.current
    val messages by chatViewModel.messages.collectAsState()
    var value by rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    var typing by rememberSaveable {
        mutableStateOf("Печатает")
    }
    var isTyping by rememberSaveable {
        mutableStateOf(false)
    }
    if (messages != null) {
        isTyping = messages!!.isNotEmpty() && messages!!.last()["author"] == "user"
    }

    coroutineScope.launch {
        scrollState.animateScrollToItem(0)
    }
    if (isTyping){
        LaunchedEffect(key1 = Unit){
            while (true){
                typing = "Печатает"
                delay(300)
                typing = "Печатает."
                delay(300)
                typing = "Печатает.."
                delay(300)
                typing = "Печатает..."
                delay(300)
            }
        }
    }

    Scaffold(topBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)) {
        Row(Modifier.align(Alignment.Center), horizontalArrangement = Arrangement.Center) {
            Text(text = if (isTyping) typing else stringResource(id = R.string.chat), style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.width(mediumPadding))
            IconButton(onClick = {
                isAlertDialog = true
            }) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.delete_button),
                    contentDescription = null)
            }
        }
            IconButton(onClick = { goToSettings() }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Image(painter = painterResource(id = if (isDark || isSystemInDarkTheme()) R.drawable.settings_icon_dark else
                    R.drawable.settings_icon), contentDescription = null)
            }
    }}) {paddingValues ->
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddingValues)) {
            Column(Modifier.weight(4f)) {
                if (messages != null) {
                    MessagesList(messages = messages!!.reversed(), state = scrollState,
                    )
                }

            }
            MessageTextField(value = value, onValueChange = {
                value = it
            }, onDone = {chatViewModel.onSendClicked(value, context = context)
                value = ""
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
                keyboardController?.hide()
            },
                isDark = isDark)
        }


        if (isAlertDialog){
            AlertDialog(onDismissRequest = {
                isAlertDialog = false
            },
                dismissButton = {
                    Button(onClick = { isAlertDialog = false },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface)) {
                        Text("Нет", style = MaterialTheme.typography.displayMedium, color = Color.Red,
                        modifier = Modifier.alpha(0.6f))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        chatViewModel.deleteMessages()
                        isAlertDialog = false},
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface)) {
                        Text(text = "Да", style = MaterialTheme.typography.displayMedium, color = Color.Blue,
                            modifier = Modifier.alpha(0.6f))
                    }
                },
                shape = RoundedCornerShape(16.dp),
                title = {
                    Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onBackground)
                },
                containerColor = MaterialTheme.colorScheme.surface)
        }
    }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BotMessageCard(message: ChatMessage) {

    val haptic = LocalHapticFeedback.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Card(shape = RoundedCornerShape(21.dp), modifier = Modifier
            .padding(mediumPadding)
            .sizeIn(
                minWidth = 180.dp, minHeight = 40.dp, maxWidth = 360.dp
            )) {
            Column(
                Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.onTertiary
                            )
                        )
                    )
                    .combinedClickable(
                        onDoubleClick = {
                            clipboardManager.setText(AnnotatedString((message.message)))
                            Toast
                                .makeText(context, "Сообщение скорованно", Toast.LENGTH_SHORT)
                                .show()
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        onClick = {

                        }
                    )
                    .sizeIn(
                        minWidth = 180.dp, minHeight = 40.dp, maxWidth = 360.dp
                    )
                    .padding(standardPadding)) {
                SelectionContainer {
                    Text(text = message.message, style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier
                            .padding(mediumPadding))
                }
                Column {
                    Row(modifier = Modifier.sizeIn(
                        minWidth = 180.dp, maxWidth = 360.dp
                    ),
                        horizontalArrangement = Arrangement.Start) {
                        Text(text = message.time, style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                                .padding(
                                    standardPadding
                                ))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserMessageCard(message: ChatMessage) {


    Box {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Card(shape = RoundedCornerShape(21.dp), modifier = Modifier
                .padding(mediumPadding)
                .sizeIn(
                    minWidth = 180.dp, minHeight = 40.dp, maxWidth = 360.dp
                )) {
                Column(
                    Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        )
                        .padding(standardPadding)
                        .sizeIn(
                            minWidth = 180.dp, minHeight = 40.dp, maxWidth = 360.dp
                        )) {
                    Text(text = message.message, style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier
                            .padding(mediumPadding))
                    Row(modifier = Modifier.sizeIn(
                        minWidth = 180.dp, maxWidth = 360.dp
                    ),
                        horizontalArrangement = Arrangement.Start) {
                        Text(text = message.time, style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onSurface, modifier = Modifier
                                .padding(
                                    standardPadding
                                ))
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesList(
    messages: List<Map<String, Any>>,
    state: LazyListState
) {
    Column(Modifier.padding(smallPadding)) {
        LazyColumn(state = state, reverseLayout = true){
            items(
                messages
            ){
                if (it["author"] == "bot"){
                    BotMessageCard(message = ChatMessage(author = it["author"] as String, message = it["message"] as String,
                        time = it["time"] as String,))
                }
                else if(it["author"] == "user"){
                    UserMessageCard(message = ChatMessage(author = it["author"] as String, message = it["message"] as String,
                        time = it["time"] as String,))
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: (String) -> Unit,
    isDark: Boolean
) {

    Row(
        androidx.compose.ui.Modifier
            .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center) {
        TextField(value = value, onValueChange = {onValueChange(it)},
            shape = RoundedCornerShape(40.dp),
        modifier = androidx.compose.ui.Modifier
            .padding(mediumPadding)
            .sizeIn(minWidth = 300.dp, maxWidth = 300.dp),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, autoCorrect = true),
        placeholder = {
            Text(text = stringResource(id = R.string.message), style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface )
        },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor =  Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
        textStyle = MaterialTheme.typography.displayMedium)
        IconButton(onClick = { onDone(value) }) {
            Image(
                imageVector = ImageVector.vectorResource(id = if (isDark || isSystemInDarkTheme()) R.drawable.send_icon_dark else
                    R.drawable.send_icon),
                contentDescription = null,
            )
        }
    }
}
