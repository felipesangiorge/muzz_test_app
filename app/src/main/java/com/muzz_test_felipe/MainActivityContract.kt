package com.muzz_test_felipe

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.Resource

interface MainActivityContract {

    interface ViewModel: ViewState, ViewActions

    interface ViewState{
        val error: LiveData<Resource.Error>
    }

    interface ViewActions{

    }
}