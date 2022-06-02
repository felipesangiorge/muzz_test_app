package com.muzz_test_felipe.data

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.AppExecutors
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.database.AppDatabase
import com.muzz_test_felipe.database.dao.UserDao
import com.muzz_test_felipe.database.mapper.MapperUserDomainToUserEntity
import com.muzz_test_felipe.domain.model_domain.UserModel
import com.muzz_test_felipe.extensions.NonnullMediatorLiveData

class UserRepository(
    private val db: AppDatabase,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {

    fun addUserIntoDatabase(
        users: List<UserModel>
    ): LiveData<Resource<Unit>> {

        val result = NonnullMediatorLiveData<Resource<Unit>>(Resource.Loading(null))

        appExecutors.diskIo().execute {
            db.runInTransaction {
                users.map {
                    userDao.upsert(MapperUserDomainToUserEntity.mapFromDomainToEntity(it))
                }
            }
            result.postValue(Resource.Success(Unit))
        }

        return result
    }
}