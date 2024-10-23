package com.example.healthyfitness.presentation.screens.sign_up

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthyfitness.R
import com.example.healthyfitness.presentation.screens.common_components.PasswordFieldComponent
import com.example.healthyfitness.presentation.screens.common_components.TextFieldComponent
import com.example.healthyfitness.presentation.screens.viewmodels.SignUpViewModel
import com.example.healthyfitness.presentation.theme.arvoFontFamily
import com.example.healthyfitness.presentation.theme.robotoFontFamily


@SuppressLint("RememberReturnType")
@Composable
fun SignUpScreen(viewModel: SignUpViewModel = viewModel(),
                 onSignUpSuccess: () -> Unit,
                 onNavigateToLogin: () -> Unit) {
    var firstName by rememberSaveable {mutableStateOf("")}
    var lastName by rememberSaveable {mutableStateOf("")}
    var email by rememberSaveable {mutableStateOf("")}
    var password by rememberSaveable {mutableStateOf("")}

    val context = LocalContext.current
    val signUpResult by viewModel.signUpResult.observeAsState()
    val validationErrors by viewModel.validationErrors.observeAsState(emptyMap())

    LaunchedEffect(signUpResult) {
        signUpResult?.let { result ->
            result.onSuccess { response ->
                // Save token to SharedPreferences
                val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", response.token)
                    apply()
                }
                onSignUpSuccess()
            }
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
                text = "Shape Your Muscles",
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
                            text = "Sign up to create account",
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
                            icon = R.drawable.ic_profile_icon,
                            label = "First Name",
                            value = firstName,
                            onValueChange = {
                                firstName = it
                                viewModel.clearFieldError("firstName")
                            },
                            error = validationErrors["firstName"]
                        )
                        if (validationErrors.containsKey("firstName")) {
                            Text(validationErrors["firstName"] ?: "",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 15.sp, fontFamily = arvoFontFamily), modifier = Modifier.padding(4.dp))
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        TextFieldComponent(
                            icon = R.drawable.ic_profile_icon,
                            label = "Last Name",
                            value = lastName,
                            onValueChange = { lastName = it
                                viewModel.clearFieldError("lastName")},
                                    error = validationErrors["lastName"])
                        if (validationErrors.containsKey("lastName")) {
                            Text(validationErrors["lastName"] ?: "",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 15.sp, fontFamily = arvoFontFamily), modifier = Modifier.padding(4.dp))
                        }
                        Spacer(modifier = Modifier.height(18.dp))

                        TextFieldComponent(
                            icon = R.drawable.ic_email_icon,
                            label = "Enter Email Address",
                            value = email,
                            onValueChange = {
                                email = it
                                viewModel.clearFieldError("email") // Validate email on change
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
                                viewModel.clearFieldError("password")
                            },
                            error = validationErrors["password"]
                        )
                        if (validationErrors.containsKey("password")) {
                            Text(validationErrors["password"] ?: "",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 15.sp, fontFamily = arvoFontFamily), modifier = Modifier.padding(4.dp))
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = { viewModel.signUp(firstName, lastName, email, password) },
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
                                text = "Sign Up",
                                style = TextStyle(
                                    fontFamily = arvoFontFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W700,
                                    color = Color(0xFF1C2126)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(onClick = onNavigateToLogin) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Already have an account? ",
                                    style = TextStyle(
                                        fontFamily = robotoFontFamily,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "SIGN IN",
                                    style = TextStyle(
                                        fontFamily = robotoFontFamily,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                            signUpResult?.let { result ->
                                when {
                                    result.isSuccess -> {
                                        val response = result.getOrNull()
                                        LaunchedEffect(response) {
                                            response?.let {
                                                // Save token to SharedPreferences
                                                val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                                                with(sharedPref.edit()) {
                                                    putString("token", it.token)
                                                    apply()
                                                }
                                                // Navigate to next screen or show success message
                                                Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                    }
                                    result.isFailure -> {
                                        val exception = result.exceptionOrNull()
                                        Text("Sign up failed: ${exception?.message}",color =MaterialTheme.colorScheme.error,style = TextStyle(fontSize = 10.sp, fontFamily = arvoFontFamily))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
//@Composable
//fun PreviewSignUpScreen() {
//    HealthyFitnessTheme(darkTheme = true) {
//        SignUpScreen(viewModel = SignUpViewModel(UserRepository()))
//    }
//}
