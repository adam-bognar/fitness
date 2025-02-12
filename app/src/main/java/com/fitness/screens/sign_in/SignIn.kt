package com.fitness.screens.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.R

@Composable
fun SignIn(
    open: (String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
)

{
    val uiState = viewModel.uiState.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sign in to gymapp",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(8.dp),
                color = colorResource(id = R.color.text_color),
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = {
                    viewModel.updateEmail(it)
                                },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    colorResource(id = R.color.text_color),
                    unfocusedTextColor = colorResource(id = R.color.text_color),
                    focusedBorderColor = colorResource(id = R.color.button_color),
                    unfocusedBorderColor = colorResource(id = R.color.text_color),
                    focusedLabelColor = colorResource(id = R.color.text_color),
                    unfocusedLabelColor = colorResource(id = R.color.text_color),
                    cursorColor = colorResource(id = R.color.text_color),
                )
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = {
                   viewModel.updatePassword(it)
                                },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    colorResource(id = R.color.text_color),
                    unfocusedTextColor = colorResource(id = R.color.text_color),
                    focusedBorderColor = colorResource(id = R.color.button_color),
                    unfocusedBorderColor = colorResource(id = R.color.text_color),
                    focusedLabelColor = colorResource(id = R.color.text_color),
                    unfocusedLabelColor = colorResource(id = R.color.text_color),
                    cursorColor = colorResource(id = R.color.text_color),
                )
            )

            Button(
                onClick = {
                    viewModel.onSignInClick(open)
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp, vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_color)),
            ) {
                Text("Login", color = colorResource(id = R.color.button_text_color))
            }
        }

        TextButton(
            onClick = {
                viewModel.onSignUpClick(open)
                      },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        ) {
            Text("Don't have an account? Sign up here.", color = colorResource(id = R.color.button_color))
        }
    }
}

@Composable
fun SocialLoginButton(platform: String, colorRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(colorResource(id = colorRes)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(platform, color = colorResource(id = R.color.button_text_color))

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPagePreview() {

    SignIn(
        open = { _ -> },
    )
}