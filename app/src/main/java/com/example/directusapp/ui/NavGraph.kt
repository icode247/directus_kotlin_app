//package com.example.directusapp.ui
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.navigation.compose.rememberNavController
//import com.example.directusapp.ui.theme.SolvedTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            SolvedTheme {
//                Surface(color = MaterialTheme.colorScheme.background) {
//                    val navController = rememberNavController()
//                    NavGraph(navController = navController)
//                }
//            }
//        }
//    }
//}
package com.example.directusapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object BlogList : Screen("blogList")
    object BlogDetail : Screen("blogDetail/{blogId}") {
        fun createRoute(blogId: Int) = "blogDetail/$blogId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.BlogList.route) {
        composable(Screen.BlogList.route) {
            BlogHomeScreen(navController)
        }
        composable(Screen.BlogDetail.route) { backStackEntry ->
            val blogIdString = backStackEntry.arguments?.getString("blogId")
            val blogId = blogIdString?.toIntOrNull()
            if (blogId != null) {
                BlogDetailScreen(blogId, navController)
            }
        }
    }
}