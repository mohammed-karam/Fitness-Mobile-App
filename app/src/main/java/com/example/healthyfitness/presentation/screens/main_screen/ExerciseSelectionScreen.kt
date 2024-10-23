import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthyfitness.R
import com.example.healthyfitness.presentation.theme.HealthyFitnessTheme


@Composable
fun ExerciseSelectionScreen(onExerciseSelected: (String) -> Unit) {
    // List of exercises
    val exercises = listOf("Shoulder", "Back", "Cardio", "Neck")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseCard(exercise) { selectedExercise ->
                    onExerciseSelected(selectedExercise)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun ExerciseCard(exerciseName: String, onExerciseSelected: (String) -> Unit) {
    Box(modifier = Modifier.size(365.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(309.dp)

                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .clickable { onExerciseSelected(exerciseName) },

                ) {

                Text(
                    text = exerciseName,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.headlineMedium,

                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Some $exerciseName Workout Exercises For You",
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
                .size(150.dp, 262.dp)
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewExerciseSelectionScreen() {
    HealthyFitnessTheme {
        ExerciseSelectionScreen() {

        }
    }
}
