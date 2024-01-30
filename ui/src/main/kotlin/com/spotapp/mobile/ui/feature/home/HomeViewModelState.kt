package com.spotapp.mobile.ui.feature.home

import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import com.spotapp.mobile.data.sources.remote.firestore.model.Question

data class HomeViewModelState(
    val errorMessage: String? = null,
    val questionList: List<Question> = emptyList(),
    val showAddQuestionDialog: Boolean = false,
    val questionToPlay: Question? = null,
    val showResultDialog: Boolean = false,
    val result: Boolean? = null,
    val loading: Boolean = false,
    val userList: List<UserData> = emptyList()
)