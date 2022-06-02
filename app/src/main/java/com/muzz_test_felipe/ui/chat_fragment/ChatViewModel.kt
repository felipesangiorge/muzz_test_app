package com.muzz_test_felipe.ui.chat_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModel() : ViewModel(), ChatContract.ViewModel {


    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatViewModel() as T
        }
    }
}