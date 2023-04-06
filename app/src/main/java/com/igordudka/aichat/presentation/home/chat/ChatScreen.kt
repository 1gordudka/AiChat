package com.igordudka.aichat.presentation.home.chat

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.igordudka.aichat.data.database.ChatMessage
import com.igordudka.aichat.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import com.igordudka.aichat.R
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    isDark: Boolean,
    goToSettings: () -> Unit
) {


    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(if (isDark) Color(0xFF161616) else Color(0xFFC6C6C6))
    val colorPalette = if (isDark) darkColorThemes else lightColorThemes
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
    val density = LocalDensity.current;
    val configuration = LocalConfiguration.current;
    val screenHeightPx = with(density) {configuration.screenHeightDp.dp.roundToPx()}
    var isKeyboardShown by rememberSaveable {
        mutableStateOf(false)
    }
    if (messages != null) {
        isTyping = messages!!.isNotEmpty() && messages!!.last()["author"] == "user"
    }

    val topPadding: Dp by animateDpAsState(
        if (!isKeyboardShown) {
            10.dp
        } else {
            (screenHeightPx / 7.5).dp
        }
    )

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


        if (settingsViewModel.colorTheme.collectAsState().value != null){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                colorPalette[settingsViewModel.colorTheme.collectAsState().value!!]
                                    .pairColors.first.copy(
                                        alpha = if (settingsViewModel.colorTheme.collectAsState().value!! == 0 && isDark)
                                            0.5f else 0.2f
                                    ),
                                if (isDark) Color(0xFF161616) else Color(0xFFC6C6C6)
                            )
                        )
                    )) {

                Column(Modifier.align(Alignment.BottomCenter)) {
                    if (messages != null) {
                        Column(Modifier.weight(9f)) {
                            MessagesList(messages = messages!!.reversed(), state = scrollState,
                                colorPalette = colorPalette)
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0f))) {
                        MessageTextField(value = value, onValueChange = {
                            value = it
                        }, onDone = {
                            chatViewModel.onSendClicked(value, context = context)
                            value = ""
                            coroutineScope.launch {
                                scrollState.animateScrollToItem(0)
                            }
                        }, colorPalette = colorPalette, onTextClick = {isKeyboardShown = true}, onDisFocus = {
                            isKeyboardShown = false
                        })
                    }

                }
                Box(modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    ,
                    contentAlignment = Alignment.TopCenter
                    ){
                    Surface(
                        Modifier
                            .padding(top = topPadding)
                            .sizeIn(minWidth = 100.dp),
                        shape = RoundedCornerShape(35.dp),
                        color = MaterialTheme.colorScheme.background,
                        tonalElevation = 6.dp
                    ) {
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)) {
                            Text(text = if (isTyping) typing else stringResource(id = R.string.chat), style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground)
                            Spacer(modifier = Modifier.width(mediumPadding))
                            IconButton(onClick = {
                                isAlertDialog = true
                            }) {
                                Image(imageVector = ImageVector.vectorResource(id = R.drawable.delete_button),
                                    contentDescription = null)
                            }
                            Spacer(modifier = Modifier.width(mediumPadding))
                            IconButton(onClick = { goToSettings() }) {
                                Image(painter = painterResource(id = if (isDark) R.drawable.settings_icon_dark else
                                    R.drawable.settings_icon), contentDescription = null)
                            }
                        }
                    }
                }

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
fun BotMessageCard(message: ChatMessage, colorPalette: List<ColorTheme>,
settingsViewModel: SettingsViewModel = hiltViewModel()) {

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
                                colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.first,
                                colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.second
                            )
                        )
                    )
                    .combinedClickable(
                        onDoubleClick = {
                            clipboardManager.setText(AnnotatedString((message.message)))
                            Toast
                                .makeText(context, "Сообщение скорованно", Toast.LENGTH_SHORT)
                                .show()
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
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

    val haptic = LocalHapticFeedback.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

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
                        .combinedClickable(
                            onDoubleClick = {
                                clipboardManager.setText(AnnotatedString((message.message)))
                                Toast
                                    .makeText(context, "Сообщение скорованно", Toast.LENGTH_SHORT)
                                    .show()
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onClick = {

                            }
                        )
                        .padding(standardPadding)
                        .sizeIn(
                            minWidth = 180.dp, minHeight = 40.dp, maxWidth = 360.dp
                        )) {
                    SelectionContainer() {
                        Text(text = message.message, style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier
                                .padding(mediumPadding))
                    }
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
    state: LazyListState,
    colorPalette: List<ColorTheme>
) {
    Column(Modifier.padding(smallPadding)) {
        LazyColumn(state = state, reverseLayout = true){
            items(
                messages,
                key = {it}
            ){
                if (it["author"] == "bot"){
                    BotMessageCard(message = ChatMessage(author = it["author"] as String, message = it["message"] as String,
                        time = it["time"] as String,), colorPalette = colorPalette)
                }
                else if(it["author"] == "user"){
                    UserMessageCard(message = ChatMessage(author = it["author"] as String, message = it["message"] as String,
                        time = it["time"] as String,))
                }
            }

            item{
                Spacer(modifier = Modifier.height(130.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: (String) -> Unit,
    colorPalette: List<ColorTheme>,
    onTextClick: () -> Unit,
    onDisFocus: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current
    val source = remember {
        MutableInteractionSource()
    }

    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            onTextClick()
        } else {
            onDisFocus()
        }
    }



    Row(
        androidx.compose.ui.Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0f)),
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
            color = MaterialTheme.colorScheme.onSurface)
        },
            interactionSource = source,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor =  Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
        textStyle = MaterialTheme.typography.displayMedium)
        IconButton(onClick = {
            onDone(value) }) {
            Image(
                imageVector = ImageVector.vectorResource(id =
                colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].sendIcon),
                contentDescription = null,
                Modifier.size(55.dp)
            )
        }
    }
}
