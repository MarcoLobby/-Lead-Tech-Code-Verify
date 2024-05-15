package com.example.verificacodiceleadtech.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.verificacodiceleadtech.utils.getErrorBorder
import com.example.verificacodiceleadtech.utils.getFocusedBorder

//Definisco i parametri personalizzati dei composable che andrò ad utilizzare
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldValue: String = "",
    placeholderText: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false,
    validateCondition: ((String) -> Boolean)? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyle.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }

    DefaultTextField(
        textFieldValue = textFieldValue,
        onValueChange = onValueChange,
        validateCondition = validateCondition,
        interactionSource = interactionSource,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        singleLine = singleLine,
        keyboardType = keyboardType,
        imeAction = imeAction,
        onAction = onAction,
        placeholderText = placeholderText,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        textStyle = textStyle
    )
}

@Composable
fun DefaultTextField(
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    validateCondition: ((String) -> Boolean)?,
    interactionSource: MutableInteractionSource,
    modifier: Modifier,
    readOnly: Boolean,
    enabled: Boolean,
    singleLine: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onAction: KeyboardActions,
    placeholderText: String,
    trailingIcon: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    visualTransformation: VisualTransformation,
    textStyle: TextStyle = TextStyle.Default
) {
    TextField(
        value = textFieldValue,
        onValueChange = { newInput ->
            onValueChange(newInput)
            validateCondition?.invoke(newInput)
        },
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .getFocusedBorder(if (validateCondition?.invoke(textFieldValue) == true) false else interactionSource.collectIsFocusedAsState().value)
            .getErrorBorder(validateCondition?.invoke(textFieldValue) == true),
        readOnly = readOnly,
        enabled = enabled,
        isError = validateCondition?.invoke(textFieldValue) ?: false,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        textStyle = textStyle.copy(lineHeight = 20.sp),
        placeholder = {
            Text(text = placeholderText, maxLines = 1, overflow = TextOverflow.Ellipsis)

        },
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            errorContainerColor = Color.Red.copy(alpha = 0.6f)
        )
    )
}