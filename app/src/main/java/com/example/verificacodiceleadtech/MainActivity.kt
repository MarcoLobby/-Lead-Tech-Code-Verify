package com.example.verificacodiceleadtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verificacodiceleadtech.components.CustomTextField
import com.example.verificacodiceleadtech.ui.theme.VerificaCodiceLeadTechTheme
import com.example.verificacodiceleadtech.utils.isValidLeadTechCode
import com.example.verificacodiceleadtech.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VerificaCodiceLeadTechTheme {

                HomeScreen(viewModel = hiltViewModel())
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val list = viewModel.getList().observeAsState().value.orEmpty()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(vertical = 8.dp)

        ) {

            val text = remember {
                mutableStateOf("")
            }

            val isValid = remember {
                mutableStateOf(false)
            }

            Text(
                text = "Lead Tech code's Verify",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomTextField(
                    placeholderText = stringResource(R.string.label_insertcode),
                    textFieldValue = text.value,
                    onValueChange = {
                        text.value = it
                        isValid.value = isValidLeadTechCode(text.value)
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    validateCondition = { !isValidLeadTechCode(it) }
                )

                Spacer(
                    modifier = Modifier
                        .width(18.dp)
                )
                Button(onClick = { viewModel.saveList(text.value) }, enabled = isValid.value) {
                    Text(text = stringResource(R.string.label_save), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.Label_Valid_Codes_List),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(list) { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.fillMaxWidth(0.5f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Button(
                                onClick = { viewModel.removeCode(item) },
                                modifier = Modifier
                            ) {
                                Text(text = stringResource(R.string.Label_button_remove))
                            }
                        }
                    }
                }
            }
        }
    }
}