package com.webtic.kedvesnaplom

import android.util.Log
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.network.dto.GetBejegyzesekListaElemDto
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import com.webtic.kedvesnaplom.ui.main.MainRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class MainUnitTest {
    @Test
    fun fetchFromService() = runTest{
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } returns listOf(
            GetBejegyzesekListaElemDto(3, "hal", "2022-05-11", "dolor"),
            GetBejegyzesekListaElemDto(2, "hal", "2022-05-10", "ipsum"),
            GetBejegyzesekListaElemDto(1, "hal", "2022-05-09", "Lorem"),
        )
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzesList() } returns listOf()
        coEvery { mockBejegyzesDao.clearBejegyzesList() } returns Unit
        coEvery { mockBejegyzesDao.insertBejegyzesList(any()) } returns Unit
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        val bejegyzesek = repository.loadBejegyzesek()

        coVerify { mockBejegyzesDao.clearBejegyzesList() }
        coVerify { mockBejegyzesDao.insertBejegyzesList(any()) }
        Assert.assertEquals(3, bejegyzesek.size)
        Assert.assertEquals(3, bejegyzesek[0].azonosito)
        Assert.assertEquals("dolor", bejegyzesek[0].tartalom)
    }

    @Test
    fun fetchFromServiceForced() = runTest{
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } returns listOf(
            GetBejegyzesekListaElemDto(3, "hal", "2022-05-11", "dolor"),
            GetBejegyzesekListaElemDto(2, "hal", "2022-05-10", "ipsum"),
            GetBejegyzesekListaElemDto(1, "hal", "2022-05-09", "Lorem"),
        )
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzesList() } returns listOf(
            Bejegyzes(5, "hal", "2022-05-07", "amet"),
            Bejegyzes(4, "hal", "2022-05-06", "sit"),
        )
        coEvery { mockBejegyzesDao.clearBejegyzesList() } returns Unit
        coEvery { mockBejegyzesDao.insertBejegyzesList(any()) } returns Unit
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        val bejegyzesek = repository.loadBejegyzesek(true)

        coVerify { mockBejegyzesDao.clearBejegyzesList() }
        coVerify { mockBejegyzesDao.insertBejegyzesList(any()) }
        Assert.assertEquals(3, bejegyzesek.size)
        Assert.assertEquals(3, bejegyzesek[0].azonosito)
        Assert.assertEquals("dolor", bejegyzesek[0].tartalom)
    }

    @Test
    fun fetchFromDao() = runTest{
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } returns listOf(
            GetBejegyzesekListaElemDto(3, "hal", "2022-05-11", "dolor"),
            GetBejegyzesekListaElemDto(2, "hal", "2022-05-10", "ipsum"),
            GetBejegyzesekListaElemDto(1, "hal", "2022-05-09", "Lorem"),
        )
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzesList() } returns listOf(
            Bejegyzes(5, "hal", "2022-05-07", "amet"),
            Bejegyzes(4, "hal", "2022-05-06", "sit"),
        )
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        val bejegyzesek = repository.loadBejegyzesek()

        Assert.assertEquals(2, bejegyzesek.size)
        Assert.assertEquals(5, bejegyzesek[0].azonosito)
        Assert.assertEquals("amet", bejegyzesek[0].tartalom)
    }

    @Test
    fun fetchFromServiceNetworkError() = runTest{
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } throws Exception()
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzesList() } returns listOf(
            Bejegyzes(5, "hal", "2022-05-07", "amet"),
            Bejegyzes(4, "hal", "2022-05-06", "sit"),
        )
        coEvery { mockBejegyzesDao.clearBejegyzesList() } returns Unit
        coEvery { mockBejegyzesDao.insertBejegyzesList(any()) } returns Unit
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        val bejegyzesek = repository.loadBejegyzesek(true)

        Assert.assertEquals(2, bejegyzesek.size)
        Assert.assertEquals(5, bejegyzesek[0].azonosito)
        Assert.assertEquals("amet", bejegyzesek[0].tartalom)
        verify { Log.d(any(), any()) }
    }

    @Test
    fun deleteNetworkError() = runTest{
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.deleteBejegyzes(any()) } throws Exception()
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } returns listOf(
            GetBejegyzesekListaElemDto(3, "hal", "2022-05-11", "dolor"),
            GetBejegyzesekListaElemDto(2, "hal", "2022-05-10", "ipsum"),
            GetBejegyzesekListaElemDto(1, "hal", "2022-05-09", "Lorem"),
        )
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.clearBejegyzesList() } returns Unit
        coEvery { mockBejegyzesDao.insertBejegyzesList(any()) } returns Unit
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        repository.deleteBejegyzes()

        coVerify(inverse = true) { mockBejegyzesDao.clearBejegyzesList() }
        coVerify(inverse = true) { mockBejegyzesDao.insertBejegyzesList(any()) }
        verify { Log.d(any(), any()) }
    }
    @Test
    fun delete() = runTest{
        val mockResponse = mockk<Response<Unit>>()
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.deleteBejegyzes(any()) } returns mockResponse
        coEvery { mockBejegyzesService.fetchBejegyzesList(any()) } returns listOf(
            GetBejegyzesekListaElemDto(3, "hal", "2022-05-11", "dolor"),
            GetBejegyzesekListaElemDto(2, "hal", "2022-05-10", "ipsum"),
            GetBejegyzesekListaElemDto(1, "hal", "2022-05-09", "Lorem"),
        )
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.clearBejegyzesList() } returns Unit
        coEvery { mockBejegyzesDao.insertBejegyzesList(any()) } returns Unit
        val repository = MainRepository(mockBejegyzesService, mockBejegyzesDao)

        repository.deleteBejegyzes()

        coVerify { mockBejegyzesDao.clearBejegyzesList() }
        coVerify { mockBejegyzesDao.insertBejegyzesList(any()) }
    }
}