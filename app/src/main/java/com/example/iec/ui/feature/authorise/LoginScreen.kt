package com.example.iec.ui.feature.authorise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.iec.DataStoreHelperImpl
import com.example.iec.PreferenceKeys
import com.example.iec.R
import com.example.iec.ui.feature.CustomDialog
import com.example.iec.ui.feature.CustomTextField
import com.example.iec.ui.feature.LoadingDialog
import com.example.iec.ui.feature.SimpleButton
import com.example.iec.ui.theme.ButtonBackground
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


enum class LoginType{
    FACEBOOK, GOOGLE, APPLE
}



@Composable
fun LoginScreenStateful(
    viewModel: LoginViewModel,
    navigateToHome: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if(uiState.loginResponse != null){
        navigateToHome(uiState.loginResponse!!.username)
    }
    LoginScreen(
        uiState = uiState,
        doLogin = viewModel::doLogin,
        doOtherLogin = {},
        onExitApp = {
        }
    )
    if(uiState.isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            LoadingDialog()
        }
    }
}

fun onExitApp(){
    val timeInterval = 2000L
    val currentTime = System.currentTimeMillis()

}




@Composable
fun LoginScreen(
    uiState: LoginUIState,
    doLogin: (String, String) -> Unit  = { _, _ -> },
    @SuppressLint("ShowToast") onRegisterAction: Toast = Toast.makeText(LocalContext.current, "On Develop", Toast.LENGTH_SHORT),
    doOtherLogin: (LoginType) -> Unit,
    onExitApp: () -> Unit,
){
    var email by remember { mutableStateOf<String?>(null) }
    var password by remember {mutableStateOf<String?>(null) }


    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val context = LocalContext.current
    val dataStore = DataStoreHelperImpl(context)

    var showDialogAuthFail by remember { mutableStateOf<String?>(null) }
    var isBiometricVerified by remember { mutableStateOf(false) }

    var notifyEmailBlank by remember { mutableStateOf(false) }
    var notifyPasswordBlank by remember { mutableStateOf(false) }

    LaunchedEffect(isBiometricVerified) {
        if(isBiometricVerified){
            lifecycleScope.launch {
                dataStore.readData(PreferenceKeys.USER_NAME)
                    .zip(
                        dataStore.readData(PreferenceKeys.USER_PASSWORD)
                    ){ first, second ->
                        first to second
                    }.collect{
                        email = it.first ?: ""
                        password = it.second ?: ""
                    }

            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Login here",
            color = ButtonBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Join conference now, \n expand your network! ",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))
        CustomTextField(
            value = email ?: "",
            isBordered = true,
            onValueChange = {
                email = it
                notifyEmailBlank = false
            },
            placeholder = "Email || Phone number"
        )
        if(notifyEmailBlank){
            Text(
                text = "Ewww email",
                color = Color.Red
            )
        }
        Spacer(Modifier.height(20.dp))
        CustomTextField(
            value = password ?: "",
            onValueChange = {
                password = it
                notifyPasswordBlank = false
            },
            placeholder = "Password"
        )
        if(notifyPasswordBlank){
        Text(
            text = "Eww password",
            color = Color.Red
        )
    }
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Forgot your password?",
            modifier = Modifier
                .align(
                    Alignment.End
                )
                .padding(horizontal = 16.dp),
            color = ButtonBackground,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        handleBiometric(
                            context,
                            ContextCompat.getMainExecutor(context),
                            onSuccessVerified = {
                                isBiometricVerified = true
                            },
                            onFailAuthentication = {
                                showDialogAuthFail = it
                            },
                            onFailFingerprint = {
                                showDialogAuthFail = it
                            }
                        )
                    },
                painter = painterResource(R.drawable.fingerprint_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = ""
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Card(
                elevation = CardDefaults.cardElevation()
            ){
                SimpleButton(
                    onClick = {
                        if(email.isNullOrEmpty()){

                        }
                        else if(password.isNullOrEmpty()){

                        }
                        else{
                            doLogin(email ?: "", password ?: "")
                        }
                    },
                ) {
                    Text(
                        text = "Sign in",
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                        ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

        }

        Spacer(Modifier.height(10.dp))
        Text(
            text = "Create new account",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onRegisterAction.show()
                }
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Or continue with",
            color = ButtonBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(top = 8.dp)
        ){
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            doOtherLogin(LoginType.GOOGLE)
                        },
                    painter = painterResource(R.drawable.google),

                    contentDescription = ""
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            doOtherLogin(LoginType.FACEBOOK)
                        },
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = ""
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            doOtherLogin(LoginType.APPLE)
                        },
                    painter = painterResource(R.drawable.apple),
                    contentDescription = ""
                )
            }
        }
    }

    CustomDialog(
        showDialog = showDialogAuthFail != null,
        onDismissRequest = {
            showDialogAuthFail = null
        }
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = showDialogAuthFail ?: "Error",
            )
        }
    }
}

fun handleBiometric(
    context: Context,
    executor: Executor,
    onSuccessVerified: () -> Unit,
    onFailAuthentication: (String) -> Unit,
    onFailFingerprint: (String) -> Unit
    ){
    //Biometric authenticator
    val biometricManager = BiometricManager.from(context)
    var isAbleToAuth = false
    val authCallback = object :  BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onFailAuthentication("Authenticate fail by $errorCode")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onSuccessVerified()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onFailAuthentication("Authentication Failed")
        }
    }
    val biometricPrompt = BiometricPrompt((context) as FragmentActivity, executor, authCallback)
    when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            Log.d("LoginScreen", "App can authenticate using biometrics or device credentials.")
            isAbleToAuth = true
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Log.d("LoginScreen", "No biometric features available on this device.")
            onFailFingerprint("No biometric features available on this device.")
        }
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            Log.d("LoginScreen", "Biometric features are currently unavailable.")

            onFailFingerprint("Biometric features are currently unavailable.")
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Log.d("LoginScreen", "The user hasn't enrolled any biometrics or device credentials.")
            onFailFingerprint("The user hasn't enrolled any biometrics or device credentials.")
        }
    }

    if(isAbleToAuth){
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(
        uiState = LoginUIState(),
        doLogin = { _, _ -> },
        doOtherLogin = {},
        onExitApp = {
        },
    )
}