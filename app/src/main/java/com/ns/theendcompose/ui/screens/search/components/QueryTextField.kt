package com.ns.theendcompose.ui.screens.search.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.PopupProperties
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.partiallyAnnotatedString

@SuppressLint("UnrememberedMutableState")
@Composable
fun QueryTextField(
    query: String?,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    loading: Boolean = false,
    showClearButton: Boolean = false,
    voiceSearchAvailable: Boolean = false,
    cameraSearchAvailable: Boolean = false,
    info: @Composable () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onQueryClear: () -> Unit = {},
    onKeyboardSearchClicked: (KeyboardActionScope.() -> Unit)? = null,
    onVoiceSearchClick: () -> Unit = {},
    onCameraSearchClick: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {}
) {
    var hasFocus by remember {
        mutableStateOf(false)
    }
    val suggestionsExpanded by derivedStateOf {
        hasFocus && suggestions.isNotEmpty()
    }

    Column(
        modifier = modifier.onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            TextField(
                value = query.orEmpty(),
                onValueChange = onQueryChange,
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(text = stringResource(id = com.ns.theendcompose.R.string.search_placeholder))
                },
                trailingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(visible = loading, enter = fadeIn(), exit = fadeOut()) {
                            CircularProgressIndicator()
                        }
                        if (showClearButton) {
                            IconButton(onClick = onQueryClear) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                            }
                        } else {
                            Row {
                                if (voiceSearchAvailable) {
                                    IconButton(onClick = onVoiceSearchClick) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardVoice,
                                            contentDescription = "voice"
                                        )
                                    }
                                }
                                if (cameraSearchAvailable) {
                                    IconButton(onClick = onCameraSearchClick) {
                                        Icon(
                                            imageVector = Icons.Filled.Camera,
                                            contentDescription = "camera"
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = onKeyboardSearchClicked
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            SuggestionsDropdown(
                query = query,
                expanded = suggestionsExpanded,
                modifier = Modifier.fillMaxWidth(),
                suggestions = suggestions,
                onSuggestionClick = onSuggestionClick
            )
        }
        info()
    }
}

@Composable
fun SuggestionsDropdown(
    query: String?,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {}
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {},
        modifier = modifier,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        suggestions.map { suggestion ->
            DropdownMenuItem(
                text = {
                    val annotatedString = partiallyAnnotatedString(
                        text = suggestion,
                        content = query.orEmpty()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = null
                        )
                        Text(
                            text = annotatedString,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = MaterialTheme.spacing.small)
                        )
                    }
                },
                onClick = { onSuggestionClick(suggestion) })
        }
    }
}