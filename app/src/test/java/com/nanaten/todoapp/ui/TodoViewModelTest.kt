package com.nanaten.todoapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.nanaten.todoapp.CoroutinesTestRule
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.database.TodoEntity
import com.nanaten.todoapp.domain.TodoRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoViewModelTest {

    private lateinit var repository: TodoRepository
    private lateinit var viewModel: TodoViewModel

    @get:Rule
    val rule = CoroutinesTestRule()

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mockk {
            coEvery { getTodoList() } returns flowOf(
                mutableListOf(
                    Todo(1, "title1", "content1", false),
                    Todo(2, "title2", "content2", false)
                )
            )
            coEvery { addTodo(any()) } just Runs
            coEvery { deleteTodo(any()) } just Runs
            coEvery { clearCompleted() } just Runs
            coEvery { checkChanged(any()) } just Runs
        }

        viewModel = TodoViewModel(repository)
        val todo = Todo(TodoEntity(1, "title1", "content1", false))
        viewModel.editTodo.value = todo
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTodoList() {
        runBlocking {
            viewModel.getTodoList()
            coVerify { repository.getTodoList() }
            // todoListAll に値が取得出来ているか確認
            Truth.assertThat(viewModel.todoListAll.value).isNotEmpty()
            Truth.assertThat(viewModel.todoListAll.value?.size).isEqualTo(2)
        }
    }

    @Test
    fun addTodo() {
        runBlocking {
            viewModel.addTodo("add_title")
            coVerify { repository.addTodo(any()) }
            // todoListAll に値が追加されているか確認
            Truth.assertThat(viewModel.todoListAll.value?.last()?.title).isEqualTo("add_title")
        }
    }

    @Test
    fun deleteTodo() {
        runBlocking {
            viewModel.deleteTodo(1)
            coVerify { repository.deleteTodo(any()) }
        }
    }

    @Test
    fun clearCompleted() {
        runBlocking {
            viewModel.clearCompleted()
            coVerify { repository.clearCompleted() }
        }
    }

    @Test
    fun checkChanged() {
        // 下準備
        viewModel.todoListAll.value = mutableListOf(
            Todo(TodoEntity(1, "title1", "content1", false))
        )

        runBlocking {
            val todo = Todo(TodoEntity(1, "title1", "content1", true))
            viewModel.checkChanged(todo)
            coVerify { repository.checkChanged(any()) }
            // todoListAll の値が変わっているか確認
            Truth.assertThat(viewModel.todoListAll.value?.first()?.isCompleted).isTrue()
        }
    }

    @Test
    fun saveTodo() {
        runBlocking {
            viewModel.saveTodo()
            coVerify { repository.addTodo(any()) }
        }
    }

    @Test
    fun editTodo() {
        val todo = Todo(TodoEntity(1, "title_edited", "content_edited", true))
        viewModel.editTodo(todo)
        // editTodo の値が変わっているか確認
        Truth.assertThat(viewModel.editTodo.value?.id).isEqualTo(1)
        Truth.assertThat(viewModel.editTodo.value?.title).isEqualTo("title_edited")
        Truth.assertThat(viewModel.editTodo.value?.content).isEqualTo("content_edited")
        Truth.assertThat(viewModel.editTodo.value?.isCompleted).isTrue()
    }
}