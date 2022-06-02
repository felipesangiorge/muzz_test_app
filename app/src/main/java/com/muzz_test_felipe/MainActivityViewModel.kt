package com.muzz_test_felipe

import androidx.lifecycle.*
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.data.UserRepository
import com.muzz_test_felipe.domain.model_domain.UserModel

class MainActivityViewModel(
    private val userRepository: UserRepository
) : ViewModel(), MainActivityContract.ViewModel {
    private val userMockToBeAdded = listOf(
        UserModel(
            "01",
            "Felipe Sangiorge",
            "@mipmap/first_user"
        ),
        UserModel(
            "02",
            "Muzz User",
            "@mipmap/second_user"
        ),
        UserModel(
            "03",
            "Barbara",
            "@mipmap/second_user"
        )    )

    private val _error = MediatorLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error

    private val addUserIntoTheDatabaseAction = MutableLiveData(Unit)
    private val addUserIntoTheDatabaseResult = addUserIntoTheDatabaseAction.switchMap {
        userRepository.addUserIntoDatabase(userMockToBeAdded)
    }

    init {
        _error.addSource(addUserIntoTheDatabaseResult) {
            if (it is Resource.Error)
                _error.value = it
        }
    }

    class Factory(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(
                userRepository
            ) as T
        }
    }
}