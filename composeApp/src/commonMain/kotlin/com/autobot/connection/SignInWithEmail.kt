package com.autobot.connection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.autobot.connection.models.User
import com.autobot.connection.theme.MyAppThemeComposable
import connection.composeapp.generated.resources.Res
import connection.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SignInWithEmail(
        modifier: Modifier = Modifier,
        uiState: LoginUiState,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onSignInClick: () -> Unit,
        isProcessing: Boolean,
        currentUser: User?,
        isError: Boolean,
        onSignOut: () -> Unit )
{
        MyAppThemeComposable {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            val width = this.maxWidth
            val finalModifier = if (width >= 780.dp) modifier.width(400.dp) else modifier.fillMaxWidth()
            Column(
                modifier = finalModifier.padding(16.dp).fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.width(150.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(), value = uiState.email, label = {
                        Text("Email")
                    }, onValueChange = onEmailChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text("Password")
                    },
                    onValueChange = onPasswordChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isProcessing) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier.fillMaxWidth().height(48.dp), onClick = onSignInClick
                    ) {
                        Text("SIGN IN")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //This is just for example, Ideally user will go to some other screen after login
                AnimatedVisibility(currentUser != null && !currentUser.isAnonymous!!) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                        Text("Login Successful", color = Color.Green.copy(alpha = 0.5f))
                        Text("Logged In auth.User ID:")
                        Text("${currentUser?.uid}")
                        TextButton(
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onSignOut()
                            }) {
                            Text("Log Out")
                        }
                    }
                }

                AnimatedVisibility(isError) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                        Text("Error in email or password!", color = MaterialTheme.colorScheme.error)
                    }
                }

            }
        }
    }
}
