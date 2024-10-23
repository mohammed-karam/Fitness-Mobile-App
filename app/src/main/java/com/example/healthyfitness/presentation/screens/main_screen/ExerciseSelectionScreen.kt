package com.example.healthyfitness.presentation.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthyfitness.R
import com.example.healthyfitness.data.data_source.repository.SignUpRepository
import com.example.healthyfitness.presentation.navigation.NavRoutes
import com.example.healthyfitness.presentation.theme.HealthyFitnessTheme


@Composable
fun ExerciseSelectionScreen(signUpRepository: SignUpRepository,onExerciseSelected: (String) -> Unit) { // List of exercises
    val exercises = listOf("Shoulder", "Back", "Cardio", "Neck")
    val firstName = remember { mutableStateOf(signUpRepository.getFirstName()) }
    val photoUrl = remember { mutableStateOf(signUpRepository.getPhotoUrl()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
Row (modifier = Modifier
    .padding(8.dp),
    horizontalArrangement = Arrangement.Start){
    if (photoUrl.value != null) {
        Image(
            painter = rememberAsyncImagePainter(photoUrl.value),
            contentDescription = "Profile Image",
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(100.dp))
                .clip(CircleShape)
                .size(50.dp)
        )
    } else {
        Image(
            painter = painterResource(R.drawable.logo_man),
            contentDescription = "Profile Image",
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(100.dp))
                .clip(CircleShape)
                .size(80.dp)
        )
    }

    Column {
        Text(modifier = Modifier.align(Alignment.Start).padding(start = 12.dp, top = 8.dp),
            text = "Hello, ${firstName.value}!",
            style = TextStyle(fontFamily = MaterialTheme.typography.bodyMedium.fontFamily, fontSize = 16.sp,))
        Text(modifier = Modifier.align(Alignment.Start).padding(start = 12.dp,bottom = 8.dp),
            text = "GET IN SHAPE",
            style = TextStyle(fontFamily = MaterialTheme.typography.bodyMedium.fontFamily, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary))
    }
}
        LazyColumn(  modifier = Modifier
            .weight(2f)
            ) {
            items(exercises) { exercise ->
                ExerciseButton(exercise) { selectedExercise ->
                    onExerciseSelected(selectedExercise)

                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ExerciseButton(exercise: String, onClick: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(309.dp)
                .padding(16.dp,0.dp,16.dp,0.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .clickable { onClick(exercise) },

                ) {

                Text(
                    text = exercise,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.headlineMedium,

                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Some $exercise Workout Exercises For You",
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .size(260.dp, 70.dp)
                        .padding(top = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_play_circle_icon),
                    contentDescription = "player button"
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_yoga_woman),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(150.dp)
                .fillMaxHeight()
        )


    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewExerciseSelectionScreen() {
    HealthyFitnessTheme {

    }
}
