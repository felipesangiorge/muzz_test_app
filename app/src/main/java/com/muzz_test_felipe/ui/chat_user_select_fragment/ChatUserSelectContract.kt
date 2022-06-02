package com.muzz_test_felipe.ui.chat_user_select_fragment

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.domain.model_domain.UserModel

interface ChatUserSelectContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val error: LiveData<Resource.Error>
        val selectedUser: LiveData<UserModel>
        val usersToSelect: LiveData<List<UserModel>>
    }

    interface ViewActions {
    }
}