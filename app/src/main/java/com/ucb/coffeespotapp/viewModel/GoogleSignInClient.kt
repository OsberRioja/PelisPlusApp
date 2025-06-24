package com.ucb.coffeespotapp.viewModel

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.ucb.coffeespotapp.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClient(
    private val context: Context
) {
    private val tag = "GoogleAuthClient: "
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            Log.d(tag, "isSignedIn: true")
            return true
        }
        return false
    }
    suspend fun signIn(): Boolean {
        if(isSignedIn()){
            return true
        }

        try{
            val result = buildCredentialRequest()
            return handleSignIn(result)
        } catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw  e
            Log.d(tag, "signIn error: ${e.message}")
            return false
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean{
        val credential = result.credential
        if(
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ){
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                Log.d(tag, "name: ${tokenCredential.displayName}")
                Log.d(tag, "email: ${tokenCredential.id}")
                Log.d(tag, "image: ${tokenCredential.profilePictureUri}")
                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null
            } catch (e: GoogleIdTokenParsingException) {
                Log.d(tag, "GoogleIdTokenParsingException: ${e.message}")
                return false
            }
        } else {
            Log.d(tag, "credential is not GoogleIDTokenCredential")
            return false
        }
    }
    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(context.getString(R.string.client_id))
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        return credentialManager.getCredential(
            request = request, context = context
        )
    }
    suspend fun signOut() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
    }
}