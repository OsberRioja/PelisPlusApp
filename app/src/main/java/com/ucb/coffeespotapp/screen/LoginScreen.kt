package com.ucb.coffeespotapp.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ucb.coffeespotapp.R
import com.ucb.coffeespotapp.ui.theme.onPrimaryContainerLight
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.ui.theme.onWhiteContainerDarkMediumContrast
import com.ucb.coffeespotapp.ui.theme.outlineLight
import com.ucb.coffeespotapp.ui.theme.tertiaryCommon
import com.ucb.coffeespotapp.viewModel.UserViewModel
import com.ucb.repository.UserRepository

@Composable
fun LoginScreen(onClick : () -> Unit){
    Scaffold(
        content = {paddingValues ->
            LoginContentScreen(
                modifier = Modifier.padding(paddingValues),
                onClick = onClick
            )
        }
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LoginContentScreen(modifier: Modifier, onClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val repository = UserRepository(context = context)
    //var userViewModel: UserViewModel = hiltViewModel()
    var userViewModel = UserViewModel(repository, context)
    var isEnabled by remember { mutableStateOf(false) }

    val loginState by userViewModel.state.collectAsState()

    when (loginState) {
        is UserViewModel.LoginState.Loading -> {
            isEnabled = false
        }
        is UserViewModel.LoginState.DoLogin -> {
            isEnabled = true
            // Enviar notificación de bienvenida
            sendWelcomeNotification(
                context = context,
                username = username
            )

            onClick()
        }
        is UserViewModel.LoginState.Error -> {
            isEnabled = true
            Toast.makeText(context, (loginState as UserViewModel.LoginState.Error).message, Toast.LENGTH_SHORT).show()
        }
        is UserViewModel.LoginState.LoggedOut -> {
            isEnabled = true
        }
    }

    val isButtonEnabled = loginState !is UserViewModel.LoginState.Loading
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        onWhiteContainerDarkMediumContrast,
                        onPrimaryContainerLight
                    )
                )
            )
            .padding(top = 40.dp, start = 30.dp, end = 30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name_1),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 36.sp,
            color = onPrimaryLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp, top = 20.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.label_name), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_name), color = Color.LightGray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = ""
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedTextColor = onPrimaryLight,
                unfocusedTextColor = onPrimaryLight,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = onPrimaryLight,
                focusedIndicatorColor = onPrimaryLight,
                unfocusedIndicatorColor = outlineLight
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.label_password), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_password), color = Color.LightGray) },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = ""
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedTextColor = onPrimaryLight,
                unfocusedTextColor = onPrimaryLight,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = onPrimaryLight,
                focusedIndicatorColor = onPrimaryLight,
                unfocusedIndicatorColor = outlineLight
            )
        )

        Spacer(modifier = Modifier.height(140.dp))

        Button(onClick = {
            userViewModel.doLogin(username, password) },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(tertiaryCommon),
            border = BorderStroke(1.dp, onPrimaryLight),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_sign_in),
                fontSize = 18.sp
            )
        }
    }
}