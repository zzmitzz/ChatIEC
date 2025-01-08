package com.example.iec.ui.feature.main.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Gender(val text: String){
    Male("Male"),
    Female("Female"),
    Other("Other")
}


data class ProfileData(
    val name: String,
    val alias: String,
    val hometown: String,
    val gender: Gender,
    val age: String,
    val jobTitle: String,
    val company: String,
    val aboutMe: String
)


@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    onSave: (ProfileData) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var alias by remember { mutableStateOf("") }
    var hometown by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.Male) }
    var age by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
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
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = alias,
                        onValueChange = { alias = it },
                        label = { Text("Alias") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Profile Details
        OutlinedTextField(
            value = hometown,
            onValueChange = { hometown = it },
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
                            selected = gender == Gender.Male,
                            onClick = { gender =  Gender.Male },
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
                            selected = gender ==  Gender.Female,
                            onClick = { gender = Gender.Female },
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
                            selected = gender ==  Gender.Other,
                            onClick = { gender = Gender.Other },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }


            }
        }
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jobTitle,
            onValueChange = { jobTitle = it },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text("Company") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = aboutMe,
            onValueChange = { aboutMe = it },
            label = { Text("About Me") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            minLines = 4
        )

        Button(
            onClick = {
                onSave(
                    ProfileData(
                        name = name,
                        alias = alias,
                        hometown = hometown,
                        gender = gender,
                        age = age,
                        jobTitle = jobTitle,
                        company = company,
                        aboutMe = aboutMe
                    )
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Save Profile")
        }
    }
}

@Preview
@Composable
fun EditProfileCardPreview() {
    EditProfileScreen {  }
}