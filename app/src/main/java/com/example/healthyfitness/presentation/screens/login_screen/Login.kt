package com.example.healthyfitness.presentation.screens.login_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.healthyfitness.R
import com.example.healthyfitness.presentation.screens.common_components.PasswordFieldComponent
import com.example.healthyfitness.presentation.screens.common_components.TextFieldComponent
import com.example.healthyfitness.presentation.theme.arvoFontFamily

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val loginResult by viewModel.loginResult.observeAsState()

    val validationErrors by viewModel.validationErrors.observeAsState(emptyMap())

    LaunchedEffect(loginResult) {
        loginResult?.onSuccess { response ->
            // Save token to SharedPreferences
            val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("token", response.token)
                apply()
            }
            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo), contentDescription = "Logo",
                modifier = Modifier
                    .height(115.dp)
                    .width(90.dp)
                    .padding(0.dp, 40.dp, 0.dp, 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Be,Yourself",
                fontSize = 40.sp,
                fontWeight = FontWeight.W700,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = arvoFontFamily,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 51.sp, shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary,
                        offset = Offset(1f, 2f),
                        blurRadius = 26f
                    )
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = Color(0xFF2A3036))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Sign in to your account",
                            style = TextStyle(
                                fontFamily = arvoFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        TextFieldComponent(
                            icon = R.drawable.ic_email_icon,
                            label = "Enter Email Address",
                            value = email,
                            onValueChange = {
                                email = it
                                viewModel.clearFieldErrorLogin("email") // Validate email on change
                            },
                            error = validationErrors["email"]
                        )
                        if (validationErrors.containsKey("email")) {
                            Text(validationErrors["email"] ?: "",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 15.sp, fontFamily = arvoFontFamily), modifier = Modifier.padding(4.dp))
                        }
                        Spacer(modifier = Modifier.height(18.dp))

                        PasswordFieldComponent(
                            icon = R.drawable.ic_lock_icon,
                            label = "Password",
                            password = password,
                            onPasswordChange = {
                                password = it
                                viewModel.clearFieldErrorLogin("password")
                            },
                            error = validationErrors["password"]
                        )
                        if (validationErrors.containsKey("password")) {
                            Text(validationErrors["password"] ?: "",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 15.sp, fontFamily = arvoFontFamily), modifier = Modifier.padding(4.dp))
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = { viewModel.login(email, password) },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .shadow(
                                    elevation = 16.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    clip = true,
                                    spotColor = MaterialTheme.colorScheme.primary
                                )
                        ) {
                            Text(
                                text = "Sign In",
                                style = TextStyle(
                                    fontFamily = arvoFontFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W700,
                                    color = Color(0xFF1C2126)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = onNavigateToSignUp,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text("Don't have an account? Sign Up")
                        }

                        loginResult?.onFailure { error ->
                            Text(
                                text = "Login failed: ${error.message}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
