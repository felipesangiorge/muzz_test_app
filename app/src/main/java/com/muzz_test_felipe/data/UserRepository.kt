package com.muzz_test_felipe.data

import androidx.lifecycle.LiveData
import com.muzz_test_felipe.core.AppExecutors
import com.muzz_test_felipe.core.Resource
import com.muzz_test_felipe.database.AppDatabase
import com.muzz_test_felipe.database.dao.ChatMessageDao
import com.muzz_test_felipe.database.dao.UserDao
import com.muzz_test_felipe.database.mapper.MapperUserDomainToUserEntity
import com.muzz_test_felipe.database.model_entity.ChatMessageEntity
import com.muzz_test_felipe.domain.mapper.MapperChatMessageEntityToChatMessageModel
import com.muzz_test_felipe.domain.mapper.MapperUserEntityToUserDomain
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel
import com.muzz_test_felipe.domain.model_domain.UserModel
import com.muzz_test_felipe.extensions.NonnullMediatorLiveData

class UserRepository(
    private val db: AppDatabase,
    private val userDao: UserDao,
    private val chatMessageDao: ChatMessageDao,
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

    fun getUsersFromDatabase(
    ): LiveData<Resource<List<UserModel>>> {

        val result = NonnullMediatorLiveData<Resource<List<UserModel>>>(Resource.Loading(null))

        appExecutors.diskIo().execute {
            db.runInTransaction {
                result.postValue(Resource.Success(userDao.getUsers().map {
                    MapperUserEntityToUserDomain.mapFromEntityToDomain(it)
                }))
            }
        }

        return result
    }

    fun getUserChatMessages(
        selectedUserId: String,
        toUser: String
    ): LiveData<Resource<List<ChatMessageModel>?>> {

        val result = NonnullMediatorLiveData<Resource<List<ChatMessageModel>?>>(Resource.Loading(null))

        appExecutors.diskIo().execute {
            db.runInTransaction {
                result.postValue(
                    Resource.Success(chatMessageDao.getChatMessage(
                        selectedUserId,
                        toUser
                    ).mapNotNull {
                        it?.let {
                            MapperChatMessageEntityToChatMessageModel.mapFromEntityToDomain(it)
                        }
                    })
                )
            }
        }

        return result
    }

    fun sendMessageAsync(
        message: String,
        fromUser: String,
        toUser: String
    ) {
        appExecutors.diskIo().execute {
            db.runInTransaction {
                chatMessageDao.insertOrIgnore(
                    ChatMessageEntity(
                        (System.currentTimeMillis() + (0..1000).random().toLong()).toString(),
                        fromUser,
                        toUser,
                        message,
                        false,
                        System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun updateMessageVisibility(
        fromUser: String,
        toUser: String
    ) {
        appExecutors.diskIo().execute {
            db.runInTransaction {
                chatMessageDao.updateMessageVisibility(
                    true,
                    fromUser,
                    toUser
                    )
            }
        }
    }
}