package com.nampt.hectrechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.usecase.GetDetailRowUseCase
import com.nampt.hectrechallenge.domain.usecase.GetListJobUseCase
import com.nampt.hectrechallenge.presentation.adapters.RateVolumeItem
import com.nampt.hectrechallenge.presentation.ratevolumn.RateVolumeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    lateinit var viewModel: RateVolumeViewModel
    val getDetailRowUseCase = mockk<GetDetailRowUseCase>()
    val getListJobUseCase = mockk<GetListJobUseCase>()

    @Before
    fun setup() {
        viewModel = RateVolumeViewModel(getListJobUseCase, getDetailRowUseCase)
    }
    @Test
    fun testGetListJobAndRow_Success() {
        every { getDetailRowUseCase.getDetailRow() } returns flow {
            emit(DataResult.Success(mutableListOf(RowDetailJson())))
        }
        every { getListJobUseCase.getListJob() } returns flow {
            emit(DataResult.Success(mutableListOf(RateVolumeJson())))
        }
        val observer = mockk<Observer<List<RateVolumeItem>>>()
        val slot = slot<List<RateVolumeItem>>()
        val list = arrayListOf<List<RateVolumeItem>>()
        every { observer.onChanged(capture(slot)) } answers {
            list.add(slot.captured)
        }
        viewModel.rateVolumeItemLiveData.observeForever(observer)
        viewModel.getListJobAndRow()
        Assert.assertTrue(list[0].isNotEmpty())
    }
}