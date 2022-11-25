package com.on99.jet7thlpp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.on99.jet7thlpp.navigation.Screen
import com.on99.jet7thlpp.ui.theme.MielmaThree
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController:NavHostController){
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000//渐变？
        )
    )
    val betaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 4000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)
        navController.popBackStack()//这一步后按下返回键就不会回到开启动画了
        navController.navigate(Screen.Home.route)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha:Float){
//    val mielmaPainter = remember {
//        object :Painter(){
//            override val intrinsicSize: Size
//                get() = Size(Icons.Default.RamenDining.viewportWidth,Icons.Default.RamenDining.viewportHeight)
//
//            override fun DrawScope.onDraw() {
//                drawRoundRect(
//                    color = MielmaThree,
//                    topLeft = Offset.Zero,
////                    size = Size(
////                        Icons.Default.RamenDining.viewportWidth,
////                        Icons.Default.RamenDining.viewportHeight
////                    ),
//                    cornerRadius = CornerRadius(6.6f),
////                    style = Fill,
//                    alpha = beta,
//                    colorFilter = null,
//                    blendMode = DefaultBlendMode
//                )
//            }
//        }
//
//    }
    Box(modifier = Modifier
        .background(if (isSystemInDarkTheme()) Color.Black else Color.LightGray)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
//        Icon(
//            painter = mielmaPainter,
//            contentDescription = "on99 made",
//            modifier = Modifier
//                .size(120.dp),
//            tint = MielmaThree
//        )
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            imageVector = Icons.Default.RamenDining,
            contentDescription = "Logo Icon on99",
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }

}

@Composable
@Preview
fun SplashScreenPreview(){
    Splash(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenPreviewDark(){
    Splash(alpha = 0.5f)
}