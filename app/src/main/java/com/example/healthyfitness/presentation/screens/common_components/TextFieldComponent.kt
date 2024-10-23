package com.example.healthyfitness.presentation.screens.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.healthyfitness.presentation.theme.robotoFontFamily

@Composable
fun TextFieldComponent(@DrawableRes icon: Int, label: String, value: String, onValueChange: (String) -> Unit,error: String?) {
    TextField(
        leadingIcon = { Image(painter = painterResource(icon), contentDescription = "Text Icon") } ,
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(text = label,
            style= TextStyle(
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.W400,
            ),
            ) },
        isError = error != null,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(Color.White),
        textStyle = TextStyle(color =Color.White),
        shape = MaterialTheme.shapes.large,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true

    )
}

//
//@Preview
//@Composable
//private fun TextFieldPreview() {
//
//    TextFieldComponent(icon = R.icon,label = "Your Name", value = "sd", onValueChange = {} )
//}