package com.on99.jet7thlpp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on99.jet7thlpp.ui.theme.My1stAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController
import com.on99.jet7thlpp.navigation.SetupNavGraph
import com.on99.jet7thlpp.ui.theme.MielmaFour
import com.on99.jet7thlpp.ui.theme.MielmaThree
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My1stAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Myapp1(modifier = Modifier.fillMaxSize())
//                }
                SetupNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier,onContinueClicked: () -> Unit){
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the jungle")
        Button(onClick = onContinueClicked, modifier = Modifier.padding(vertical = 15.dp)) {
            Text(text = "Continue")
        }
    }

}

@Composable
fun Myapp1(
    modifier: Modifier = Modifier
) {
//    Surface(
//        modifier = modifier,
//        color = MaterialTheme.colorScheme.background
//    ){
//        Greeting(name = "fuck u")
//    }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 1000
                )
            ),
        color = MaterialTheme.colorScheme.background
    ) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = {

                shouldShowOnboarding = false
            })
        } else {
            Greetings()
        }
    }
}
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
//    names: List<String> = listOf("bull shit", "shit on", "on me")
    names:List<String> = List(1000){"$it"}
){
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)){
        items(items = names){ name ->
            Greeting(name = name)
        }
    }
}


@Composable
fun Greeting(name: String) {
//    val expanded = remember{ mutableStateOf(false) }
//    val expandedPadding by animateDpAsState(
//        if (expanded.value) 100.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioHighBouncy,
//            stiffness = Spring.StiffnessLow,
//
//        )
////        animationSpec = tween(
////            durationMillis = 300,
////            delayMillis = 50,
////            easing = FastOutSlowInEasing
////        )
////        animationSpec = repeatable(
////            iterations = 3,
////            animation = tween(durationMillis = 300),
////            repeatMode = RepeatMode.Reverse
////        )
//    )
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
//    ) {
////        Text(text = "Hello $name!" , modifier = Modifier
////            .padding(26.dp)
////            .border(1.dp, Color.Yellow, RoundedCornerShape(5.dp))
////            .rotate(25.0f))
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(24.dp)
////                .padding(10.dp)
////                .border(1.dp, Color.Blue, RoundedCornerShape(2.dp))
////        ) {
////            Text(text = "Hellomf ,")
////            Text(text = name)
////        }
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = expandedPadding)) {
//                Text(text = "who r u")
//                Text(text = name , style = MaterialTheme.typography.headlineLarge.copy(
//                    fontWeight = FontWeight.ExtraBold
//                ))
//            }
//            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
//                Text(text = if (expanded.value) "show 呃呃" else "show true")
//            }
//        }
//    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp , horizontal = 8.dp)
    ) {
        CardContent(name = name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    background = if (expanded) MielmaThree else MielmaFour
                ),
                modifier = Modifier
                    .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
//                color = if (expanded) MielmaThree else Color.White

            )
            if (expanded) {
                Text(
//                    text = ("Composem ipsum color sit lazy, " +
//                            "padding theme elit, sed do bouncy. ").repeat(4),
                    text = ("嗨，我只是想测试下分行罢了"+
                            "至于结果我也不知道呢。\n").repeat(3),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.ExtraLight
                    )
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "i wanna preview fuck", widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    My1stAppTheme {
//        Myapp1()
        OnboardingScreen(onContinueClicked = {})
    }
}
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    My1stAppTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    My1stAppTheme {
        Myapp1(Modifier.fillMaxSize())
    }
}