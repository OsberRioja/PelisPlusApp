package com.ucb.coffeespotapp.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.coffeespotapp.R
import com.ucb.coffeespotapp.ui.theme.onPrimaryContainerLight
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.ui.theme.onWhiteContainerDarkMediumContrast
import com.ucb.coffeespotapp.viewModel.GoogleSignInUtils

@Composable
fun WelcomeScreen(onClick: () -> Unit, onLoginClick: () -> Unit, onSkip: () -> Unit) {
    Scaffold(
        content = {paddingValues ->
            WelcomeScreenContent(
                modifier = Modifier.padding(paddingValues),
                onClick = onClick,
                onLoginClick = onLoginClick,
                onSkip = onSkip
            )
        }
    )
}

@Composable
fun WelcomeScreenContent(modifier: Modifier = Modifier, onClick: () -> Unit, onLoginClick: () -> Unit, onSkip: () -> Unit) {
    val localContext= LocalContext.current
    val coroutineScope = rememberCoroutineScope()
//    val activity = localContext as? Activity ?: throw IllegalStateException("Activity not found")
//
//    // Aquí usas la actividad para configurar Google Sign-In
//    val googleAuthClient = GoogleAuthClient(activity)//aqui era applicationContext
//
//    var isSignIn by rememberSaveable { mutableStateOf(googleAuthClient.isSignedIn()) }
//
//    // Registrar el ActivityResultLauncher para el inicio de sesión
//    val signInLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult(),
//        onResult = { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                coroutineScope.launch {
//                    val signInSuccess = googleAuthClient.signIn()
//                    isSignIn = signInSuccess
//                }
//            }
//        }
//    )
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        GoogleSignInUtils.doGoogleSignIn(
            context = localContext,
            scope = coroutineScope,
            launcher = null,
            login = {
                Toast.makeText(localContext,"Login successful", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        onWhiteContainerDarkMediumContrast,
                        onPrimaryContainerLight
                    )
                )
            )
            .padding(16.dp)
    ) {
//        if(isSignIn) {
//            OutlinedButton(onClick = {
//                isSignIn = false
//            }) {
//                Text(text = "ya funciona :D", fontSize = 16.sp,
//                    modifier = Modifier.padding(16.dp))
//            }
//        }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name_1),
                style = MaterialTheme.typography.headlineMedium,
                color = onPrimaryLight,
                fontSize = 52.sp
            )

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = stringResource(id = R.string.description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp,
                color = onPrimaryLight,
                lineHeight = 25.sp
            )

            Spacer(modifier = Modifier.height(70.dp))

            OutlinedButton(
                onClick = {onClick()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                border = BorderStroke(1.dp, onPrimaryLight),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_crear_cuenta),
                    color = onPrimaryLight,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
//                    coroutineScope.launch {
//                        val result = googleAuthClient.signIn()
//                        if (result) {
//                            isSignIn = true
//                        } else {
//                            Log.e("WelcomeScreenContent", "SignIn failed")
//                        }
//                    }
                    GoogleSignInUtils.doGoogleSignIn(
                        context = localContext,
                        scope = coroutineScope,
                        launcher = launcher,
                        login = {
                            Toast.makeText(localContext,"Login successful", Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(onPrimaryLight),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.btn_google),
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onLoginClick() },
                modifier = Modifier
                    .border(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
            ) {
                Text(
                    text = stringResource(id = R.string.link_sign_in),
                    color = onPrimaryLight,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Omitir",
                color = onPrimaryLight,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable(onClick = onSkip)
            )
        }
    }
}