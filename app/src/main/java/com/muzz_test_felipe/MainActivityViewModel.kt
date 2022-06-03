package com.muzz_test_felipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.data.UserRepository

class MainActivityViewModel(
    private val userRepository: UserRepository
) : ViewModel(), MainActivityContract.ViewModel {

    private val _error = MediatorLiveData<Resource.Error>()
    override val error: LiveData<Resource.Error> = _error


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