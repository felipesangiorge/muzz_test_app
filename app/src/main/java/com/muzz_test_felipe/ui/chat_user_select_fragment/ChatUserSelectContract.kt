package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.domain.model_domain.UserModel

interface ChatUserSelectContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val error: LiveData<Resource.Error>
        val navigation: LiveData<ViewInstructions>
        val selectedUser: LiveData<UserModel>
        val usersToSelect: LiveData<List<UserModel>>
        val isSelectUsersClicked: LiveData<Boolean>
    }

    interface ViewActions {
        fun updateSelectedUser(position: Int)

        fun selectUsersClicked()

        fun userToChatClicked(user: UserModel)
    }

    sealed class ViewInstructions{
        data class NavigateToChat(val user: UserModel, val selectedUserId: String): ViewInstructions()
    }
}