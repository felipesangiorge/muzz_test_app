package com.muzz_test_felipe.ui.chat_fragment

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel
import com.muzz_test_felipe.domain.model_domain.UserModel

interface ChatContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val error: LiveData<Resource.Error>
        val navigation: LiveData<ViewInstructions>
        val selectedUser: LiveData<UserModel>
        val messagesList: LiveData<List<ChatMessageModel>>
        val text: LiveData<String>
        val isSendButtonClickable: LiveData<Boolean>
    }

    interface ViewActions {
        fun backClicked()

        fun setTextChanged(text: String)

        fun sendMessage()
    }

    sealed class ViewInstructions {
        object NavigateBack : ViewInstructions()
    }
}