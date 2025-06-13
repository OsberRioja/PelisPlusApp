package com.ucb.coffeespotapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ucb.coffeespotapp.ui.theme.CoffeeSpotAppTheme
import android.widget.Button // Para los botones
import android.widget.EditText
import android.widget.TextView // Si necesitas textos
import androidx.compose.material3.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.inject.Provider

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //setup
        setup()

        enableEdgeToEdge()
        setContent {
            CoffeeSpotAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    private fun setup() {
        title = "Autenticación"
        // Referencia los botones usando findViewById
        val btnLogin = findViewById<Button>(R.id.loginButton)
        val btnRegister = findViewById<Button>(R.id.Registerbutton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        btnRegister.setOnClickListener{
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?:"",ProviderType.BASIC)
                    }else{
                        if (emailEditText.text.toString().isEmpty()) {
                            emailEditText.error = "El email es obligatorio"
                        }
                        if (passwordEditText.text.toString().isEmpty()) {
                            passwordEditText.error = "La contraseña es obligatoria"
                        }
                    }
                }
            }
        }
        btnLogin.setOnClickListener{
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?:"",ProviderType.BASIC)
                    }else{
                        if (emailEditText.text.toString().isEmpty()) {
                            emailEditText.error = "El email es obligatorio"
                        }
                        if (passwordEditText.text.toString().isEmpty()) {
                            passwordEditText.error = "La contraseña es obligatoria"
                        }
                    }
                }
            }
        }
    }
    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoffeeSpotAppTheme {
        Greeting("Android")
    }
}