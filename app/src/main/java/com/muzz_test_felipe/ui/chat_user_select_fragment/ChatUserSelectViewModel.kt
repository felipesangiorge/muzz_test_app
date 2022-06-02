package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.domain.model_domain.UserModel

class ChatUserSelectViewModel() : ViewModel(), ChatUserSelectContract.ViewModel {

    private val _error = MutableLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error

    private val _selectedUser = MutableLiveData<UserModel>()
    override val selectedUser: LiveData<UserModel> = _selectedUser

    private val _usersToSelect = MutableLiveData<List<UserModel>>()
    override val usersToSelect: LiveData<List<UserModel>> = _usersToSelect

    init {

    }

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatUserSelectViewModel() as T
        }
    }
}