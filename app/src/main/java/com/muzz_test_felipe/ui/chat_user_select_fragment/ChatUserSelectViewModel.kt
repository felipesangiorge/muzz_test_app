package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatUserSelectViewModel() : ViewModel(), ChatUserSelectContract.ViewModel {


    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatUserSelectViewModel() as T
        }
    }
}