package com.deto.mediaexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.deto.mediaexplorer.ui.elements.ElementScreen
import com.deto.mediaexplorer.ui.categories.HomeScreen
import com.deto.mediaexplorer.ui.categories.NewCategoryScreen
import com.deto.mediaexplorer.ui.elements.NewElementScreen
import com.deto.mediaexplorer.ui.elements.SecondScreen
import kotlinx.serialization.Serializable

@Serializable
object HomePage

@Serializable
data class SecondScreenPage(val categoryId: Int)

@Serializable
object NewCategoryScreenPage

@Serializable
data class NewElementScreenPage(val categoryId: Int)

@Serializable
data class ElementScreenPage(val id: Int)


@Composable
fun Navigation(){



    val navController = rememberNavController()

    NavHost( navController = navController, startDestination = HomePage ){

        composable<HomePage> {
            HomeScreen(navController = navController)
        }

        composable<SecondScreenPage> { backStackEntry ->
            val args = backStackEntry.toRoute<SecondScreenPage>()
            SecondScreen(navController = navController, categoryId = args.categoryId )
        }

        composable<NewCategoryScreenPage> {
            NewCategoryScreen(navController = navController)
        }

        composable<NewElementScreenPage> { backStackEntry ->
            val args = backStackEntry.toRoute<NewElementScreenPage>()
            NewElementScreen(navController = navController, categoryId = args.categoryId)
        }

        composable<ElementScreenPage> { backStackEntry ->
            val args = backStackEntry.toRoute<ElementScreenPage>()
            ElementScreen(navController = navController, id = args.id)
        }



    }
}