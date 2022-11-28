package com.on99.jet7thlpp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.compose.rememberNavController
import com.on99.jet7thlpp.navigation.SetupNavGraph
import com.on99.jet7thlpp.ui.theme.MielmaThree
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(modifier: Modifier = Modifier,onContinueClicked: () -> Unit){
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(text = "Welcome to the jungle")
        var value by remember {
            mutableStateOf(0)
        }

        CustomComponent(
            indicatorValue = value,
            maxIndicatorValue = 1000,
            backgroundIndicatorColor = Color.Black.copy(alpha = 0.1f),
            bigTextColor = Color.Black,
            smallTextColor = Color.Black.copy(alpha = 0.7f)
//            indicatorStrokeCap = StrokeCap.Square
        )

        TextField(
            value = value.toString(),
            onValueChange = {
                value = if (it.isNotEmpty()){
//                    it.toInt()
                    if(isInteger(it)){
                        it.toInt()
                    }else{
                        0
                    }
                }else{
                    0
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )


        Button(onClick = onContinueClicked, modifier = Modifier.padding(vertical = 15.dp)) {
            Text(text = "Continue")
        }
    }

}

fun isInteger(s: String): Boolean {
    return try {
        s.toInt()
        true
    } catch (e: NumberFormatException) {
        false
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
    val state = rememberLazyListState()
    val showToTheTopButton by remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 0
        }
    }
    val coroutineScope = rememberCoroutineScope()
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
    val boxAlpha = animateFloatAsState(
        targetValue = if (showToTheTopButton) 1.0f else 0.0f,
        animationSpec = tween(
            durationMillis = 1500
        )
    )
    val FABColorAlpha by animateColorAsState(
        targetValue = if (showToTheTopButton) MielmaThree.copy(
            alpha = 1.0f
        ) else MielmaThree.copy(alpha = 0.0f),
        animationSpec = tween(
            durationMillis = 1500
        )
    )
    LazyColumn(
        state = state,
        modifier = modifier.padding(vertical = 4.dp)
    ){
        items(items = names){ name ->
            Greeting(name = name)
        }
    }
//    if (showToTheTopButton){
        Box(//针对消失时不会有动画而是直接消失，我个人的建议是将Boolean变量调整到与颜色相关，即false为透明，true显现，同时令该按钮在false时不可用
            modifier = Modifier
                .height(47.dp)
                .width(47.dp)
                .padding(bottom = 20.dp)
                .alpha(alpha = boxAlpha.value),
            contentAlignment = Alignment.BottomCenter,
        ){
            FloatingActionButton(
                modifier = Modifier
                    .height(45.dp)
                    .width(45.dp)
                    .padding(1.dp)
                    .heightIn(max = 45.dp)
                    .widthIn(max = 45.dp)
                ,
                onClick = {
                    if (showToTheTopButton){
                        coroutineScope.launch {
                            state.animateScrollToItem(
                                index = 0
                            )
                        }
                    }
                    },
                containerColor = FABColorAlpha,
                shape = FloatingActionButtonDefaults.shape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                )
            ) {
                Icon(
                    Icons.Default.Flight,
                    contentDescription = "TOP",
                    tint = LocalContentColor.current.copy(boxAlpha.value)
                )
            }
        }
//    }
}
//    FloatingActionButton(onClick = {
//        coroutineScope.launch {
//            state.animateScrollToItem(
//                index = 0
//            )
//        }
//    }) {
//
//    }

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
    val textNumberColor by animateColorAsState(
        targetValue = if (expanded) Color.Yellow else Color.White,
        animationSpec = tween(
            durationMillis = 750
        )
    )
    val firstlineString= "Hello!"
    val firstlineStringInChina= "你好!"
//    val animatedFirstlineString by animateValueAsState(
//        targetValue =if (expanded) firstlineStringInChina else firstlineString,
//        typeConverter =
//    )
    var textFirstline by remember{
        mutableStateOf(firstlineString)
    }
    val toIntName:Int = if(isInteger(name)) name.toInt() else 0
    val haxName:String = if(toIntName!=0 && toIntName%3==0) "三的${toIntName/3}个整数倍" else name
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
            Text(text = textFirstline)
            Text(
                text = haxName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                color = textNumberColor
//                modifier = Modifier
//                    .animateContentSize(
//                    animationSpec = tween(
//                        durationMillis = 500
//                    )
//                )
//                color = if (expanded) MielmaThree else Color.White

            )
//            Text(text = "test!", style = MaterialTheme.typography.titleLarge)//引起变化的并非padding的改变而是Text组件的现身
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
        IconButton(onClick = {
            expanded = !expanded
            if (textFirstline.equals(firstlineString)){
                textFirstline = firstlineStringInChina
            }
            else{
                textFirstline = firstlineString
            }
        }) {
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