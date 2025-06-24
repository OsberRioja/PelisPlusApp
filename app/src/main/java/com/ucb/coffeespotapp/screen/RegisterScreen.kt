package com.ucb.coffeespotapp.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ucb.model.User
import com.ucb.coffeespotapp.FormErrorState
import com.ucb.coffeespotapp.FormState
import com.ucb.coffeespotapp.GenreChip
import com.ucb.coffeespotapp.R
import com.ucb.coffeespotapp.isFormValid
import com.ucb.coffeespotapp.ui.theme.errorContainerDarkMediumContrast
import com.ucb.coffeespotapp.ui.theme.onPrimaryContainerLight
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.ui.theme.onWhiteContainerDarkMediumContrast
import com.ucb.coffeespotapp.ui.theme.outlineLight
import com.ucb.coffeespotapp.ui.theme.tertiaryCommon
import com.ucb.coffeespotapp.validateForm
import com.ucb.coffeespotapp.viewModel.UserViewModel
import com.ucb.repository.UserRepository

@Composable
fun RegisterScreen(onClick: () -> Unit) {
    Scaffold(
        content = {paddingValues ->
            RegisterContentScreen(
                modifier = Modifier.padding(paddingValues),
                onClick = onClick
            )
        }
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterContentScreen(modifier: Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val repository = UserRepository(context = context)
    var viewModel = UserViewModel(repository, context)
    var formState by remember { mutableStateOf(FormState()) }
    var errors by remember { mutableStateOf(FormErrorState()) }
    var isValid by remember { mutableStateOf(false) }

    var selectedGenres = viewModel.selectedGenres

    val genres = listOf("Acción", "Aventura", "Catástrofe", "Ciencia Ficción", "Comedia", "Documental",
        "Drama", "Fantasia", "Familiar-Infantil", "Musical", "Suspenso", "Terror")
    val predetermined = TextFieldDefaults.colors(
        focusedTextColor = onPrimaryLight,
        unfocusedTextColor = onPrimaryLight,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        cursorColor = onPrimaryLight,
        focusedIndicatorColor = onPrimaryLight,
        unfocusedIndicatorColor = outlineLight
    )

    fun validate(){
        errors = validateForm(formState)
    }

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
            value = formState.username,
            onValueChange = {
                formState = formState.copy(username = it)
                validate()
            },
            supportingText = {
                if (errors.usernameError != null) {
                    Text(
                        text = errors.usernameError.toString(),
                        color = errorContainerDarkMediumContrast
                    )
                }},
            label = { Text(text = stringResource(id = R.string.label_name), color = onPrimaryLight) },
            isError = errors.usernameError != null,
            placeholder = { Text(stringResource(id = R.string.placeholder_name), color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = predetermined
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.birthDate,
            onValueChange = {
                formState = formState.copy(birthDate = it)
                validate()
            },
            isError = errors.birthDateError != null,
            supportingText = {
                if (errors.birthDateError != null){
                    Text(
                        text = errors.birthDateError.toString(),
                        color = errorContainerDarkMediumContrast)
                }
            },
            label = { Text(stringResource(id = R.string.label_date), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_date), color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = predetermined
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.email,
            onValueChange = {
                formState = formState.copy(email = it)
                validate()
            },
            isError = errors.emailError != null,
            supportingText = {
                if (errors.emailError != null) {
                    Text(
                        text = errors.emailError.toString(),
                        color = errorContainerDarkMediumContrast
                    )
                }
            },
            label = { Text(stringResource(id = R.string.label_email), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_email), color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = predetermined
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.password,
            onValueChange = {
                formState = formState.copy(password = it)
                validate()
            },
            isError = errors.passwordError != null,
            supportingText = {
                if (errors.passwordError != null) {
                    Text(
                        text = errors.passwordError.toString(),
                        color = errorContainerDarkMediumContrast
                    )
                }
            },
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
            value = formState.confirmPassword,
            onValueChange = {
                formState = formState.copy(confirmPassword = it)
                validate()
            },
            isError = errors.confirmPasswordError != null,
            supportingText = {
                if (errors.confirmPasswordError != null) {
                    Text(
                        text = errors.confirmPasswordError.toString(),
                        color = errorContainerDarkMediumContrast
                    )
                }
            },
            label = { Text(stringResource(id = R.string.label_password2), color = onPrimaryLight) },
            placeholder = { Text(stringResource(id = R.string.placeholder_password), color = Color.LightGray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = predetermined
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
            horizontalArrangement = Arrangement.spacedBy(5.dp),
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
                            selectedGenres.add(it);
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                validate()
                if(isFormValid(errors)){
                    viewModel.saveUser(
                        User(
                            username = formState.username,
                            birthDate = formState.birthDate,
                            email = formState.email,
                            password = formState.password
                        ));
                    onClick()
                }
            },
            enabled = isFormValid(errors),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 40.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(tertiaryCommon),
            border = BorderStroke(1.dp, onPrimaryLight),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_create_account),
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
        //.width(100.dp)
    ) {
        Row {
            if(isSelected){
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = genre,
                fontSize = 14.sp
            )
        }

    }
}
