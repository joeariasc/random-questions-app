package com.spotapp.mobile.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotapp.mobile.data.repository.GameRepository
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import com.spotapp.mobile.data.sources.remote.firestore.model.Option
import com.spotapp.mobile.domain.usecases.QuizMaster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(
    private val gameRepository: GameRepository,
    private val quizMaster: QuizMaster,
    private val userRanking: Flow<List<UserData>>,
) : ViewModel() {
    private val viewModelState: MutableStateFlow<HomeViewModelState> =
        MutableStateFlow(HomeViewModelState())

    val uiState = viewModelState.asStateFlow()

    init {
        getQuestions()
        startUserRankingListening()
        getRanking()
    }

    private fun startUserRankingListening() {
        viewModelScope.launch(Dispatchers.IO) {
            userRanking.collect { userList ->
                Log.d("startUserRankingListening", "List of users => $userList")
                viewModelState.update {
                    it.copy(
                        userList = userList,
                    )
                }
            }
        }
    }

    private fun getRanking() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                gameRepository.getRanking()
            }.onSuccess { userList ->
                viewModelState.update {
                    it.copy(
                        userList = userList,
                    )
                }
            }.onFailure {
                Log.d("getRanking", "error! => ${it.message}")
            }
        }

    }

    private fun getQuestions() {
        viewModelState.update {
            it.copy(loading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                gameRepository.getQuestionList()
            }.onSuccess { questionList ->
                Log.d("FirestoreService", "questions, list: $questionList")
                viewModelState.update {
                    it.copy(
                        questionList = questionList,
                        loading = false
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message,
                    )
                }
            }
        }
    }

    fun playGame() {
        val questionList = viewModelState.value.questionList
        if (questionList.isNotEmpty()) {
            viewModelState.update {
                it.copy(
                    questionToPlay = questionList[Random.nextInt(questionList.size)]
                )
            }
        }
    }

    fun stopGame() {
        viewModelState.update {
            it.copy(
                questionToPlay = null
            )
        }
    }

    fun onSendAnswer(option: Option) {
        viewModelState.update {
            it.copy(loading = true)
        }
        val question = viewModelState.value.questionToPlay
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                question?.let {
                    quizMaster(option, it)
                }
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        questionToPlay = null,
                        showResultDialog = true,
                        result = option.id == question?.idCorrectOption,
                        loading = false,
                    )
                }
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message
                    )
                }
            }
        }

    }

    fun onAddQuestions() {
        viewModelState.update {
            it.copy(loading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                gameRepository.addQuestions(generateQuestions())
            }.onSuccess {
                viewModelState.update {
                    it.copy(
                        showAddQuestionDialog = true,
                        loading = false,
                    )
                }
                getQuestions()
            }.onFailure { throwable ->
                viewModelState.update {
                    it.copy(
                        errorMessage = throwable.message
                    )
                }
            }
        }
    }

    fun cleanError() {
        viewModelState.update {
            it.copy(
                errorMessage = null,
                showResultDialog = false,
                showAddQuestionDialog = false
            )
        }
    }

    fun hideQuestionDialog() {
        viewModelState.update { it.copy(showAddQuestionDialog = false) }
    }

    fun hideResultDialog() {
        viewModelState.update {
            it.copy(
                showResultDialog = false,
                result = null,
                showAddQuestionDialog = false,
                questionToPlay = null
            )
        }
        getQuestions()
    }
}
