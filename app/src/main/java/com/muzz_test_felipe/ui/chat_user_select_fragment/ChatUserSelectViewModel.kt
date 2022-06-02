package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.*
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.data.UserRepository
import com.muzz_test_felipe.domain.model_domain.UserModel

class ChatUserSelectViewModel(
    private val userRepository: UserRepository
) : ViewModel(), ChatUserSelectContract.ViewModel {

    private val userMockToBeAdded = listOf(
        UserModel(
            "01",
            "Felipe Sangiorge",
            "first_user"
        ),
        UserModel(
            "02",
            "Muzz User",
            "second_user"
        ),
        UserModel(
            "03",
            "Barbara",
            "third_user"
        )
    )

    private val _error = MediatorLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error

    private val _navigation = MutableLiveData<ChatUserSelectContract.ViewInstructions>()
    override val navigation: LiveData<ChatUserSelectContract.ViewInstructions> = _navigation

    private val _selectedUser = MutableLiveData<UserModel>()
    override val selectedUser: LiveData<UserModel> = _selectedUser

    private val _usersToSelect = MutableLiveData<List<UserModel>>()
    override val usersToSelect: LiveData<List<UserModel>> = _usersToSelect

    private val addUserIntoTheDatabaseAction = MutableLiveData(Unit)
    private val addUserIntoTheDatabaseResult = addUserIntoTheDatabaseAction.switchMap {
        userRepository.addUserIntoDatabase(userMockToBeAdded)
    }

    private val getUsersFromDbAction = MutableLiveData<Unit>()
    private val getUsersFromDbResult = getUsersFromDbAction.switchMap {
        userRepository.getUsersFromDatabase()
    }

    private val _isSelectUsersClicked = MutableLiveData(false)
    override val isSelectUsersClicked: LiveData<Boolean> = _isSelectUsersClicked

    init {
        _error.addSource(addUserIntoTheDatabaseResult){
            when(it){
                is Resource.Success -> getUsersFromDbAction.value = Unit
            }
        }

        _error.addSource(getUsersFromDbResult) {
            when (it) {
                is Resource.Failure -> _error.value = it.error
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {
                        _selectedUser.value = it.first()
                        _usersToSelect.value = it.filter {
                            it.id != _selectedUser.value?.id.orEmpty()
                        }
                    }
                }
            }
        }
    }

    override fun updateSelectedUser(position: Int) {
        val tempSelectedUser = _selectedUser.value

        when (position) {
            0 -> {
                _selectedUser.value = _usersToSelect.value?.first()
            }
            1 -> {
                _selectedUser.value = _usersToSelect.value?.last()
            }
        }
        _usersToSelect.value = _usersToSelect.value?.minus(_selectedUser.value!!)
        _usersToSelect.value = _usersToSelect.value?.plus(tempSelectedUser!!)
        _isSelectUsersClicked.value = false
    }

    override fun selectUsersClicked() {
        _isSelectUsersClicked.value = _isSelectUsersClicked.value == false
    }

    override fun userToChatClicked(user: UserModel) {
        _navigation.value = ChatUserSelectContract.ViewInstructions.NavigateToChat(user, _selectedUser.value!!.id)
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