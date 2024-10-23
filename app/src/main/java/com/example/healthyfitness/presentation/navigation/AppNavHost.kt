package com.example.healthyfitness.presentation.navigation

import BackExerciseListScreen
import BackExerciseViewModel
import ExerciseSelectionScreen
import SignUpViewModelFactory
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthyfitness.data.data_source.remote.retrofit.RetrofitInstance
import com.example.healthyfitness.data.data_source.repository.LoginRepository
import com.example.healthyfitness.data.data_source.repository.SignUpRepository
import com.example.healthyfitness.presentation.screens.WelcomeScreen
import com.example.healthyfitness.presentation.screens.notification.Notifications
import com.example.healthyfitness.presentation.screens.common_components.Exercise
import com.example.healthyfitness.presentation.screens.common_components.customs.CustomAppBar
import com.example.healthyfitness.presentation.screens.common_components.customs.CustomBottomAppBar
import com.example.healthyfitness.presentation.screens.sign_up.SignUpScreen
import com.example.healthyfitness.presentation.screens.all_back_work.BackExerciseDetailsScreen
import com.example.healthyfitness.presentation.screens.all_cardio_work.CardioExerciseDetailsScreen
import com.example.healthyfitness.presentation.screens.all_cardio_work.CardioExerciseListScreen
import com.example.healthyfitness.presentation.screens.all_cardio_work.CardioExerciseViewModel
import com.example.healthyfitness.presentation.screens.all_neck_work.NeckExerciseDetailsScreen
import com.example.healthyfitness.presentation.screens.all_neck_work.NeckExerciseListScreen
import com.example.healthyfitness.presentation.screens.all_neck_work.NeckExerciseViewModel
import com.example.healthyfitness.presentation.screens.all_shoulders_work.ShouldersExerciseDetailsScreen
import com.example.healthyfitness.presentation.screens.all_shoulders_work.ShouldersExerciseListScreen
import com.example.healthyfitness.presentation.screens.all_shoulders_work.ShouldersExerciseViewModel
import com.example.healthyfitness.presentation.screens.foods.FoodDetailsScreen
import com.example.healthyfitness.presentation.screens.foods.FoodListScreen
import com.example.healthyfitness.presentation.screens.foods.FoodViewModel
import com.example.healthyfitness.presentation.screens.home_page1_screen.HomePage1Screen
import com.example.healthyfitness.presentation.screens.login_screen.LoginScreen
import com.example.healthyfitness.presentation.screens.login_screen.LoginViewModel
import com.example.healthyfitness.presentation.screens.viewmodels.SignUpViewModel
import com.example.healthyfitness.presentation.utils.LogInViewModelFactory
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context


//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            HealthyFitnessTheme {
//                MainScreen()
//            }
//        }
//    }
//}


@RequiresApi(Build.VERSION_CODES.P)

@Composable
fun MainScreen() {


    val sharedPreferences = LocalContext.current.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)



    val navController = rememberNavController()

    // Initialize ViewModels
    val foodVM: FoodViewModel = viewModel()
    val neckVM: NeckExerciseViewModel = viewModel()
    val cardioVM: CardioExerciseViewModel = viewModel()
    val shouldersVM: ShouldersExerciseViewModel = viewModel()
    val backVM: BackExerciseViewModel = viewModel()

    val apiService = RetrofitInstance.create()
    val repository = SignUpRepository(apiService,sharedPreferences)
    val logrepository = LoginRepository(apiService)

    val signViewModel: SignUpViewModel = viewModel(
        factory = SignUpViewModelFactory(repository)
    )

    val logViewModel: LoginViewModel = viewModel(
        factory = LogInViewModelFactory(logrepository)
    )

    // State for food
    val state = foodVM.food.collectAsState().value

    var showBackButton by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("Healthy Fitness") }

    var currentIndex by remember { mutableStateOf(0) }

    var showBars by remember { mutableStateOf(true) }

    // Update title based on the current route
    LaunchedEffect(navController) {
        if (isLoggedIn) {
            navController.navigate(NavRoutes.ExerciseSelection.route) // Navigate to the main screen for logged-in users
        } else {
            navController.navigate(NavRoutes.Welcome.route) // Navigate to the welcome screen for new users
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = when (destination.route) {
                NavRoutes.ExerciseSelection.route -> "Exercises"
                NavRoutes.FoodList.route -> "Foods"
                NavRoutes.FoodDetail.route -> "Food Details"
                NavRoutes.ShouldersExerciseList.route -> "Shoulders Exercises"
                NavRoutes.BackExerciseList.route -> "Back Exercises"
                NavRoutes.CardioExerciseList.route -> "Cardio Exercises"
                NavRoutes.NeckExerciseList.route -> "Neck Exercises"
                NavRoutes.NotificationList.route -> "Notifications"
                NavRoutes.HomeScreen1.route -> "Activities"
                else -> "Exercise Details" // Default title
            }
            showBackButton = when (destination.route) {
                NavRoutes.ExerciseSelection.route -> false
                NavRoutes.FoodList.route -> false
                NavRoutes.HomeScreen1.route -> false
                NavRoutes.NotificationList.route -> false
                else -> {
                    true
                }

            }

            currentIndex = when (destination.route) {
                NavRoutes.ExerciseSelection.route, NavRoutes.ShouldersExerciseList.route, NavRoutes.ShouldersExerciseDetails.route, NavRoutes.CardioExerciseList.route, NavRoutes.CardioExerciseDetails.route, NavRoutes.NeckExerciseList.route, NavRoutes.NeckExerciseDetails.route, NavRoutes.BackExerciseList.route, NavRoutes.BackExerciseDetails.route -> 0
                NavRoutes.FoodList.route, NavRoutes.FoodDetail.route -> 1
                NavRoutes.HomeScreen1.route -> 2
                NavRoutes.NotificationList.route -> 3
                else -> -1
            }
            showBars = when (destination.route) {
                NavRoutes.SignIn.route,NavRoutes.SignUp.route, NavRoutes.Welcome.route -> false
                else -> true
            }

        }


    }


    Scaffold(
        topBar = {
            if(showBars) CustomAppBar(
                title = title,
                navController = navController,
                showBackButton = showBackButton
            )
        },
        bottomBar = { if(showBars) CustomBottomAppBar(navController = navController, currentIndex) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavRoutes.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.Welcome.route) {
                WelcomeScreen(
                    onSignUp = { navController.navigate(NavRoutes.SignUp.route) },
                    onSignIn = { navController.navigate(NavRoutes.SignIn.route) }
                )
            }
            composable(NavRoutes.FoodList.route) {

                if (state != null) {
                    FoodListScreen(foodItems = state) { selectedItem ->
                        navController.navigate(NavRoutes.FoodDetail.createRoute(selectedItem.id))
                    }
                }
            }

            composable(NavRoutes.FoodDetail.route) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")?.toInt()
                val selectedItem = state?.first { it!!.id == itemId }
                selectedItem?.let {
                    FoodDetailsScreen(selectedItem)
                }
            }

            composable(NavRoutes.ExerciseSelection.route) {
                ExerciseSelectionScreen(signUpRepository = repository) { selectedExercise ->
                    when (selectedExercise) {
                        "Shoulder" -> navController.navigate(NavRoutes.ShouldersExerciseList.route)
                        "Back" -> navController.navigate(NavRoutes.BackExerciseList.route)
                        "Cardio" -> navController.navigate(NavRoutes.CardioExerciseList.route)
                        "Neck" -> navController.navigate(NavRoutes.NeckExerciseList.route)
                    }
                }
            }

            composable(NavRoutes.ShouldersExerciseList.route) {
                ShouldersExerciseListScreen() {
                    navController.navigate(NavRoutes.ShouldersExerciseDetails.createRoute(it))
                }
            }

            composable(NavRoutes.BackExerciseList.route) {
                BackExerciseListScreen() {
                    navController.navigate(NavRoutes.BackExerciseDetails.createRoute(it))
                }
            }

            composable(NavRoutes.CardioExerciseList.route) {
                CardioExerciseListScreen() {
                    navController.navigate(NavRoutes.CardioExerciseDetails.createRoute(it))
                }
            }

            composable(NavRoutes.NeckExerciseList.route) {
                NeckExerciseListScreen() {
                    navController.navigate(NavRoutes.NeckExerciseDetails.createRoute(it))
                }
            }

            composable(NavRoutes.BackExerciseDetails.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                var exercise by remember { mutableStateOf<Exercise?>(null) }
                LaunchedEffect(exerciseId) {
                    exercise = exerciseId?.let { backVM.getBackExerciseById(it) }
                }
                exercise?.let {
                    BackExerciseDetailsScreen(exercise = it)
                }
            }

            composable(NavRoutes.NeckExerciseDetails.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                var exercise by remember { mutableStateOf<Exercise?>(null) }
                LaunchedEffect(exerciseId) {
                    exercise = exerciseId?.let { neckVM.getNeckExerciseById(it) }
                }
                exercise?.let {
                    NeckExerciseDetailsScreen(exercise = it)
                }
            }

            composable(NavRoutes.CardioExerciseDetails.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                var exercise by remember { mutableStateOf<Exercise?>(null) }
                LaunchedEffect(exerciseId) {
                    exercise = exerciseId?.let { cardioVM.getCardioExerciseById(it) }
                }
                exercise?.let {
                    CardioExerciseDetailsScreen(exercise = it)
                }
            }

            composable(NavRoutes.ShouldersExerciseDetails.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                var exercise by remember { mutableStateOf<Exercise?>(null) }
                LaunchedEffect(exerciseId) {
                    exercise = exerciseId?.let { shouldersVM.getShouldersExerciseById(it) }
                }
                exercise?.let {
                    ShouldersExerciseDetailsScreen(exercise = it)
                }
            }
            composable(NavRoutes.NotificationList.route) {
                Notifications()
            }
            composable(NavRoutes.HomeScreen1.route) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    HomePage1Screen()
                }
            }

            composable(NavRoutes.SignUp.route) {
                SignUpScreen(
                    viewModel = signViewModel,
                    onSignUpSuccess = {
                        navController.navigate(NavRoutes.SignIn.route)
                    },
                    onNavigateToLogin = {
                        navController.navigate(NavRoutes.SignIn.route)
                    }
                )
            }

            composable(NavRoutes.SignIn.route) {
                LoginScreen(
                    viewModel = logViewModel,
                    onLoginSuccess = {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true) // Set to true on successful signup
                        editor.apply()

                        navController.navigate(NavRoutes.ExerciseSelection.route)
                    },
                    onNavigateToSignUp = {
                        navController.navigate(NavRoutes.SignUp.route) {
                            popUpTo(NavRoutes.SignUp.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}



