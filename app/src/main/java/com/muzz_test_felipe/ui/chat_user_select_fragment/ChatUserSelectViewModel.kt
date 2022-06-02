package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.*
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.data.UserRepository
import com.muzz_test_felipe.domain.model_domain.UserModel

class ChatUserSelectViewModel(
    private val userRepository: UserRepository
) : ViewModel(), ChatUserSelectContract.ViewModel {

    private val _error = MediatorLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error

    private val _selectedUser = MutableLiveData<UserModel>()
    override val selectedUser: LiveData<UserModel> = _selectedUser

    private val _usersToSelect = MutableLiveData<List<UserModel>>()
    override val usersToSelect: LiveData<List<UserModel>> = _usersToSelect

    private val getUsersFromDbAction = MutableLiveData(Unit)
    private val getUsersFromDbResult = getUsersFromDbAction.switchMap {
        userRepository.getUsersFromDatabase()
    }

    init {
        _error.addSource(getUsersFromDbResult) {
            when (it) {
                is Resource.Failure -> _error.value = it.error
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {
                        _selectedUser.value = it.first()
                        _usersToSelect.value = it
                    }
                }
            }
        }
    }

    class Factory(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatUserSelectViewModel(
                userRepository
            ) as T
        }
    }
}