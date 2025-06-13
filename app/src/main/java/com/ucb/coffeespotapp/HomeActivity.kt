package com.ucb.coffeespotapp

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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //SETUP
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email?:"",provider?:"")

        enableEdgeToEdge()
        setContent {
            CoffeeSpotAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun setup(email:String, provider:String){
        title = "INICIO"
        val emailTextView= findViewById<TextView>(R.id.emailTextView)
        val passTextView= findViewById<TextView>(R.id.passTextView)
        val btnLogout = findViewById<Button>(R.id.logoutbutton)
        emailTextView.text = email
        passTextView.text = provider
        btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressedDispatcher
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CoffeeSpotAppTheme {
        Greeting2("Android")
    }
}