package com.example.salonapp.domain.use_cases.user

import com.example.salonapp.common.Constants
import com.example.salonapp.data.repositories.UserRepositoryFake
import com.example.salonapp.domain.models.User
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DeleteUserUseCaseTest {


    private lateinit var deleteUserUseCase: DeleteUserUseCase
    private lateinit var userRepositoryFake: UserRepositoryFake

    @Before
    fun setUp(){

        val usersToInsert = mutableListOf<User>()
        ('a'..'z').forEachIndexed { index, c ->
            usersToInsert.add(
                User(
                    id = index,
                    firstName = c.toString(),
                    lastName = c.toString(),
                    email = "$c@mail.com",
                    phone = index.toString().repeat(8),
                    role = Constants.ROLE_OWNER
                )
            )

            usersToInsert.shuffle()

            userRepositoryFake = UserRepositoryFake(0, usersToInsert)
            deleteUserUseCase = DeleteUserUseCase(userRepositoryFake)
        }
    }

    @After
    fun tearDown(){
        
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Testing Delete User UseCase`(){
        val indexToDelete = (0..20).random()

        runTest {
            val emits = deleteUserUseCase(indexToDelete).toList()

            assertThat(emits[1].data!!.id == indexToDelete)

            assertThat(userRepositoryFake.getUserById(indexToDelete).firstName).isEmpty()
        }
    }

}