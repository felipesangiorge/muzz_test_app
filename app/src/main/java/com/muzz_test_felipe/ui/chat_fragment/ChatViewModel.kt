package com.muzz_test_felipe.ui.chat_fragment

import androidx.lifecycle.*
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.data.UserRepository
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel
import com.muzz_test_felipe.domain.model_domain.UserModel

class ChatViewModel(
    private val user: UserModel,
    private val selectedUserId: String,
    private val userRepository: UserRepository
) : ViewModel(), ChatContract.ViewModel {

    private val _error = MediatorLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error

    private val _navigation = MutableLiveData<ChatContract.ViewInstructions>()
    override val navigation: LiveData<ChatContract.ViewInstructions> = _navigation

    private val _selectedUser = MutableLiveData(user)
    override val selectedUser: LiveData<UserModel> = _selectedUser

    private val _text = MutableLiveData<String>()
    override val text: LiveData<String> = _text

    override val isSendButtonClickable: LiveData<Boolean> = _text.map {
        it.isNotBlank()
    }


    private val _messagesList = MutableLiveData<List<ChatMessageModel>>()
    override val messagesList: LiveData<List<ChatMessageModel>> = _messagesList

    private val sendMessageAction = MutableLiveData<String>()
    private val sendMessageResult = sendMessageAction.map {
        userRepository.sendMessageAsync(
            it,
            selectedUserId,
            user.id
        )
    }

    private val setMessageVisibilityAction = MutableLiveData(Unit)
    private val sendMessageVisibilityResult = setMessageVisibilityAction.map {
        userRepository.updateMessageVisibility(
            selectedUserId,
            user.id
        )
    }

    private val getUserChatMessagesAction = MutableLiveData(Unit)
    private val getUserChatMessagesResult = getUserChatMessagesAction.switchMap {
        userRepository.getUserChatMessages(
            selectedUserId,
            user.id
        )
    }

    init {
        _error.addSource(sendMessageVisibilityResult){}

        _error.addSource(sendMessageResult) {}

        _error.addSource(getUserChatMessagesResult) {
            when (it) {
                is Resource.Failure -> _error.value = it.error
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {
                        _messagesList.value = it.sortedBy {
                            it.date
                        }
                    }
                }
            }
        }
    }

    override fun backClicked() {
        _navigation.value = ChatContract.ViewInstructions.NavigateBack
    }

    override fun sendMessage() {
        if (_text.value.isNullOrBlank()) return
        sendMessageAction.value = _text.value
        getUserChatMessagesAction.value = Unit
        _text.value = ""
    }

    override fun setTextChanged(text: String) {
        if (_text.value == text) return
        _text.value = text
    }

    class Factory(
        private val user: UserModel,
        private val selectedUserId: String,
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatViewModel(
                user,
                selectedUserId,
                userRepository
            ) as T
        }
    }
}