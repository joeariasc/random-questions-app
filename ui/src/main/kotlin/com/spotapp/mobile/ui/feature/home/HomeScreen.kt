package com.spotapp.mobile.ui.feature.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import com.spotapp.mobile.data.sources.remote.firestore.model.Option
import com.spotapp.mobile.data.sources.remote.firestore.model.Question
import com.spotapp.mobile.ui.R
import com.spotapp.mobile.ui.common.Loading
import com.spotapp.mobile.ui.common.SingleButton
import com.spotapp.mobile.ui.common.StandardAlertDialog

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.loading) {
        Loading()
    }

    if (uiState.errorMessage != null) {
        StandardAlertDialog(
            onDismissRequest = viewModel::cleanError,
            dialogText = uiState.errorMessage!!
        )
    }

    if (uiState.showAddQuestionDialog) {
        StandardAlertDialog(
            onDismissRequest = viewModel::hideQuestionDialog,
            dialogText = "Questions Added!"
        )
    }

    if (uiState.questionToPlay != null) {
        GameDialog(
            question = uiState.questionToPlay,
            onDismiss = viewModel::stopGame,
            onSendOption = viewModel::onSendAnswer
        )
    }

    if (uiState.showResultDialog) {
        StandardAlertDialog(
            icon = if (uiState.result == true) Icons.Default.Check else Icons.Default.Close,
            dialogTitle = "Result!",
            onDismissRequest = viewModel::hideResultDialog,
            dialogText = stringResource(id = if (uiState.result == true) R.string.good_answer else R.string.wrong_answer)
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Indicators(totalQuestions = uiState.questionList.size)

        Ranking(uiState.userList)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,

            ) {
            FloatingActionButton(
                onClick = viewModel::playGame,
                containerColor = colorResource(id = R.color.MidnightBlue)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = colorResource(id = R.color.snowWhite)
                )
            }

            FloatingActionButton(
                onClick = viewModel::onAddQuestions,
                containerColor = colorResource(id = R.color.MidnightBlue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = colorResource(id = R.color.snowWhite)
                )
            }
        }
    }
}

@Composable
fun GameDialog(
    question: Question? = null,
    onDismiss: () -> Unit,
    onSendOption: (Option) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .size(430.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = colorResource(id = R.color.mainColor)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.question_title),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = question?.questionText ?: "")

                var selectedOption by remember { mutableStateOf(question!!.options[0]) }

                Spacer(modifier = Modifier.height(4.dp))

                question?.options?.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.mainColor)
                            )
                        )
                        Text(
                            text = option.optionText,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                SingleButton(buttonText = "Send", onClick = {
                    onSendOption(selectedOption)
                })
            }
        }
    }
}

@Composable
@Preview
fun GameDialogPreview() {
    GameDialog(
        question = Question(
            id = "123",
            questionText = "What is the purpose of SQL in database management?",
            options = listOf(
                Option(1, "Secure file storage"),
                Option(2, "Structured Query Language"),
                Option(3, "Simple Text Language"),
                Option(4, "Software Quality Level")
            ),
            idCorrectOption = 3
        ),
        onDismiss = {},
        onSendOption = {}
    )
}

@Composable
private fun Indicators(totalQuestions: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Card(
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.snowWhite)
            ),
            modifier = Modifier
                .border(
                    border = BorderStroke(2.dp, colorResource(id = R.color.mainColor)),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Questions to answer: $totalQuestions"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Ranking(userList: List<UserData>?) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.6F)
            .fillMaxWidth(0.9F)
            .border(
                border = BorderStroke(2.dp, colorResource(id = R.color.snowWhite)),
                shape = RoundedCornerShape(10.dp)
            ),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.snowWhite)
        ),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                        .border(
                            border = BorderStroke(
                                2.dp,
                                colorResource(id = R.color.mainColor)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color = colorResource(id = R.color.snowWhite)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = "Ranking in Real Time 📈")
                }
            }

            userList?.forEach {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(
                                border = BorderStroke(
                                    2.dp,
                                    colorResource(id = R.color.MidnightBlue)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = it.email ?: "no email yet :("
                        )

                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${it.score}"
                        )
                    }
                }
            }
        }
    }
}
