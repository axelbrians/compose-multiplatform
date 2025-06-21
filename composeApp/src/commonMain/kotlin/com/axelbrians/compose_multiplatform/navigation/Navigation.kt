package com.axelbrians.compose_multiplatform.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.axelbrians.compose_multiplatform.MultiplatformDefaultComposable
import com.eygraber.uri.UriCodec
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
object HomeRoute

@Serializable
object MultiplatformDefaultComposableRoute

@Serializable
data class DetailRoute(
    val item: CustomType
)

@Serializable
data class CustomType(
    val id: String,
    val name: String,
    val description: String
)


val CustomNavType = object : NavType<CustomType>(
    isNullableAllowed = false,
) {
    override fun get(bundle: SavedState, key: String): CustomType? {
        return bundle.read {
            val stringify = this.getStringOrNull(key)
            Json.decodeFromString(stringify ?: return null)
        }
    }

    override fun parseValue(value: String): CustomType {
        return Json.decodeFromString(UriCodec.decode(value))
    }

    override fun put(bundle: SavedState, key: String, value: CustomType) {
        bundle.write {
            putString(key, Json.encodeToString(value))
        }
    }

    override fun serializeAsValue(value: CustomType): String {
        return UriCodec.encode(Json.encodeToString(value))
    }
}
@Composable
fun createNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        homeRoute(navController)
        detailRoute(navController)
        multiplatformDefaultComposableRoute(navController)
    }
}


fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    composable<HomeRoute> {
        HomeView(navController = navController)
    }
}

fun NavGraphBuilder.detailRoute(navController: NavHostController) {
    composable<DetailRoute>(
        typeMap = mapOf(typeOf<CustomType>() to CustomNavType),
    ) {
        val item = it.toRoute<DetailRoute>().item
        DetailView(navController = navController, item = item)
    }
}

fun NavGraphBuilder.multiplatformDefaultComposableRoute(navController: NavHostController) {
    composable<MultiplatformDefaultComposableRoute> {
        MultiplatformDefaultComposable()
    }
}

@Composable
fun HomeView(navController: NavHostController) {
    // Home view
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Home view")
            Button(
                onClick = {
                    navController.navigate(DetailRoute(item = CustomType("1", "Item 1", "Description 1")))
                }
            ) {
                Text("Go to Item 1")
            }
            Button(
                onClick = {
                    navController.navigate(DetailRoute(item = CustomType("2", "Item 2", "Description 2")))
                }
            ) {
                Text("Go to Item 2")
            }

            Button(
                onClick = {
                    navController.navigate(MultiplatformDefaultComposableRoute)
                }
            ) {
                Text("Multiplatform Default Composable")
            }
        }
    }
}

@Composable
fun DetailView(navController: NavHostController, item: CustomType) {
    // Detail view
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Detail view")
            Text("Item id: ${item.id}")
            Text("Item name: ${item.name}")
            Text("Item description: ${item.description}")
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Go back")
            }
        }

    }
}