package com.example.iec.ui.feature.main.home.common

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.R
import com.example.iec.ui.feature.CustomDialog

enum class Gender(val text: String){
    Male("Male"),
    Female("Female"),
    Other("Other")
}


data class ProfileData(
    val name: String = "",
    val alias: String = "",
    val hometown: String = "",
    val gender: Gender = Gender.Male,
    val age: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val aboutMe: String = "",
)


@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    defaultProfileData: ProfileData = ProfileData(),
    onBackPress: () -> Unit = {},
    onSaveProfile: (ProfileData) -> Unit,
) {
    var userProfile by remember{
        mutableStateOf(defaultProfileData)
    }
    var showCancelDialog by remember { mutableStateOf(false) }
    var showOnSaveDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box {
            Icon(
                modifier = Modifier.clickable {
                    onBackPress()
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
            Image(
                painter = painterResource(R.drawable.ptit_iec),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)
            )
        }
        // Avatar and Basic Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedAvatar() // Your existing composable

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = userProfile.name,
                        onValueChange = { userProfile = userProfile.copy(name = it) },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = userProfile.alias,
                        onValueChange = { userProfile = userProfile.copy(alias = it) },
                        label = { Text("Alias") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Profile Details
        OutlinedTextField(
            value = userProfile.hometown,
            onValueChange = { userProfile = userProfile.copy(hometown = it) },
            label = { Text("Hometown") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            text = "Gender",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box() {
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(text = "Male", modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically), color = Color.DarkGray)
                        RadioButton(
                            selected = userProfile.gender == Gender.Male,
                            onClick = { userProfile = userProfile.copy(gender = Gender.Male) },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
                Box() {
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(text = "Female", modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically), color = Color.DarkGray)
                        RadioButton(
                            selected = userProfile.gender ==  Gender.Female,
                            onClick = { userProfile = userProfile.copy(gender = Gender.Female) },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
                Box() {
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(text = "Other", modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically), color = Color.DarkGray)
                        RadioButton(
                            selected = userProfile.gender == Gender.Other,
                            onClick = { userProfile = userProfile.copy(gender = Gender.Other) },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }


            }
        }
        OutlinedTextField(
            value = userProfile.age,
            onValueChange = { userProfile = userProfile.copy(age = it) },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = userProfile.jobTitle,
            onValueChange = { userProfile = userProfile.copy(jobTitle = it) },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = userProfile.company,
            onValueChange = { userProfile = userProfile.copy(company = it) },
            label = { Text("Company") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = userProfile.aboutMe,
            onValueChange = { userProfile = userProfile.copy(aboutMe = it) },
            label = { Text("About Me") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            minLines = 4
        )

        Button(
            onClick = {

                showOnSaveDialog = true
                onSaveProfile(
                    userProfile
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Save Profile")
        }
    }


    if(showCancelDialog){
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Edit") },
            text = { Text("All information will be lost?") },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("No")
                }
            },
            confirmButton = {
                TextButton(onClick = { onBackPress() }) {
                    Text("Yes")
                }
            }
        )
    }
    CustomDialog(
        showOnSaveDialog,
        onDismissRequest = {
            onBackPress()
        }
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ){
            Text("Profile saved successfully!")
        }
    }
}

fun checkInput(userProfile: ProfileData): Boolean {
    return userProfile.name.isNotEmpty() && userProfile.alias.isNotEmpty()}

@Preview(showBackground = true)
@Composable
fun EditProfileCardPreview() {
    EditProfileScreen() {  }
}