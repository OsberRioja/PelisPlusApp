package com.ucb.coffeespotapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.viewpager2.widget.ViewPager2
import com.ucb.coffeespotapp.ui.theme.AppTheme
import com.ucb.coffeespotapp.ui.theme.onPrimaryContainerLight
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.ui.theme.onWhiteContainerDarkMediumContrast
import com.ucb.coffeespotapp.ui.theme.onWhiteLightHighContrast
import com.ucb.coffeespotapp.ui.theme.outlineLight
import com.ucb.coffeespotapp.ui.theme.tertiaryCommon
import com.ucb.coffeespotapp.ui.theme.tertiaryContainerDark
import com.ucb.coffeespotapp.ui.theme.whiteDark

class LoginFirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun FirstScreen() {
    val localContext= LocalContext.current
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
                onClick = {
//                    val intent = Intent(Aca)
//                    startActitiy(localContext, )
                },
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
                onClick = { /* Acción de Google Sign-In */ },
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
                        painter = painterResource(id = R.drawable.ic_google_logo), // Asegúrate de tener el ícono de Google en tu proyecto
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
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedGenres = remember { mutableStateListOf<String>() }

    val genres = listOf("Acción", "Aventura", "Catástrofe", "Ciencia Ficción", "Comedia", "Documental",
        "Drama", "Fantasia", "Familiar-Infantil", "Musical", "Suspenso", "Terror")

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name_1),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 36.sp,
            color = onPrimaryLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp, top = 20.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.label_name), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_name), color = Color.LightGray) },
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
            value = birthDate,
            onValueChange = { birthDate = it },
            label = { Text(stringResource(id = R.string.label_date), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_date), color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.label_email), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_email), color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(id = R.string.label_password2), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_password), color = Color.LightGray) },
            visualTransformation = PasswordVisualTransformation(),
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.subtitle),
            textAlign = TextAlign.Start,
            color = onPrimaryLight,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            genres.forEach { genre ->
                GenreChip(
                    genre = genre,
                    isSelected = selectedGenres.contains(genre),
                    onGenreSelected = {
                        if (selectedGenres.contains(it)) {
                            selectedGenres.remove(it)
                        } else {
                            selectedGenres.add(it)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(tertiaryCommon),
            border = BorderStroke(1.dp, onPrimaryLight),
            shape = RoundedCornerShape(20.dp)) {
            Text(
                text = stringResource(id = R.string.btn_login_account),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun GenreChip(genre: String, isSelected: Boolean, onGenreSelected: (String) -> Unit) {
    Button(
        onClick = { onGenreSelected(genre) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) onPrimaryLight else Color.Transparent,
            contentColor = if (isSelected) Color.Black else onPrimaryLight
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, onPrimaryLight),
        modifier = Modifier
            .height(40.dp)
            .width(160.dp)
    ) {
        Row {
            if(isSelected){
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp))
            }
            Text(
                text = genre,
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun LoginScreen(){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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

        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(tertiaryCommon),
            border = BorderStroke(1.dp, onPrimaryLight),
            shape = RoundedCornerShape(20.dp)) {
            Text(
                text = stringResource(id = R.string.btn_create_account),
                fontSize = 18.sp
            )
        }
    }
}