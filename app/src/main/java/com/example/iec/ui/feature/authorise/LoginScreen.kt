package com.example.iec.ui.feature.authorise

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.iec.R
import com.example.iec.ui.CustomTextField
import com.example.iec.ui.LoadingDialog
import com.example.iec.ui.SimpleButton
import com.example.iec.ui.theme.ButtonBackground


enum class LoginType{
    FACEBOOK, GOOGLE, APPLE
}

@Composable
fun LoginScreen(
    uiState: LoginUIState,
    doLogin: (String, String) -> Unit  = { _, _ -> },
    @SuppressLint("ShowToast") onRegisterAction: Toast = Toast.makeText(LocalContext.current, "On Develop", Toast.LENGTH_SHORT),
    doOtherLogin: (LoginType) -> Unit,
    onExitApp: () -> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



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
            value = email,
            isBordered = true,
            onValueChange = {
                email = it
            },
            placeholder = "Email || Phone number"
        )
        Spacer(Modifier.height(30.dp))
        CustomTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = "Password"
        )
        Spacer(Modifier.height(30.dp))
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
        Card(
            modifier = Modifier.padding(horizontal = 32.dp),
            elevation = CardDefaults.cardElevation()
        ){
            SimpleButton(
                onClick = { doLogin(email, password)},
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
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Create new account",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable {
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
                    modifier = Modifier.size(20.dp).clickable {
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
                    modifier = Modifier.size(20.dp).clickable {
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
                    modifier = Modifier.size(20.dp).clickable {
                        doOtherLogin(LoginType.APPLE)
                    },
                    painter = painterResource(R.drawable.apple),
                    contentDescription = ""
                )
            }
        }
    }
}
//
//
//@Preview(
//    showBackground = true,
//    backgroundColor = android.graphics.Color.WHITE.toLong()
//)
//@Composable
//
//fun LoginScreenPreview() {
//    LoginScreen({}, {})
//}