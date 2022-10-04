package com.example.composefromscratch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composefromscratch.ui.theme.ComposeFromScratchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFromScratchTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun onBoardingScreen(onContinueClicked: () -> Unit) {
    var shouldShowOnBoarding by remember {
        mutableStateOf(true)
    }
    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = {
                    shouldShowOnBoarding = false
                    onContinueClicked()
                }) {
                Text(text = "Continue")
            }

        }
    }

}


@Preview(showBackground = true, widthDp = 320)
@Composable
private fun MyApp(name: List<String> = List(1000) { "$it" }) {
    var shouldShowOnBoarding by rememberSaveable {
        mutableStateOf(true)
    }
    if (shouldShowOnBoarding) {
        onBoardingScreen() {
            shouldShowOnBoarding = false
        }
    } else {
        Surface(color = MaterialTheme.colors.secondary) {
            LazyColumn(
                modifier = Modifier.padding(
                    4.dp
                )
            ) {
                items(name) {
                    Greeting(name = it)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded = rememberSaveable {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded.value) 48.dp else 0.dp, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(color = MaterialTheme.colors.primary, modifier = Modifier.padding(4.dp, 8.dp)) {

        Row(Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(
                    text = "$name!", style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded.value) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }

            }
            IconButton(
                onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.ExpandLess else
                        Icons.Filled.ExpandMore,
                    contentDescription = if (expanded.value) "Show less" else "Show more",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var shouldShowOnBoarding by remember {
        mutableStateOf(true)
    }
    ComposeFromScratchTheme {
        if (shouldShowOnBoarding) {
            onBoardingScreen() {
                shouldShowOnBoarding = false
            }
        } else {
            Greeting("Android")
        }
    }
}