package com.example.healthyfitness.presentation.screens.notification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.healthyfitness.data.data_source.repository.SignUpRepository
import com.example.healthyfitness.presentation.navigation.NavRoutes
import com.example.healthyfitness.presentation.screens.notification.components.NotificationItem
import com.example.healthyfitness.presentation.screens.notification.components.Banner

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Notifications(itemsList: List<String> = (1..10).map { "Item $it" },signUpRepository: SignUpRepository) {
    val navController = rememberNavController()

        Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
            // Logout button

            Button(
                onClick = {
                    // Clear the login state in SharedPreferences
                    signUpRepository.clearLoginState()

                    // Navigate to the SignIn screen
                    navController.navigate(NavRoutes.SignIn.route) {
                        // Remove the backstack to prevent going back after logout
                        popUpTo(NavRoutes.SignIn.route) {
                            inclusive = true
                        }
                    }
                },
                shape = MaterialTheme.shapes.small,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onPrimary, disabledContentColor = MaterialTheme.colorScheme.onPrimary, disabledContainerColor = MaterialTheme.colorScheme.error) ,
                modifier = Modifier
                    .shadow(
                        elevation = 11.dp,
                        shape = RoundedCornerShape(10.dp),
                        spotColor = MaterialTheme.colorScheme.error
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(8.dp,16.dp,8.dp,0.dp)

            ){
                Text(text = "Logout", color = MaterialTheme.colorScheme.onPrimary)
            }

                LazyColumn (
                    modifier = Modifier.fillMaxSize().padding(8.dp).weight(1f),

                    ){
                    items(itemsList) { item ->
                        NotificationItem(item)
                    }}


            /* NotificationItem()
             NotificationItem()
             NotificationItem()*/
        }
    }






@Preview
@Composable
fun PreviewNotifications() {
//    Notifications()
}