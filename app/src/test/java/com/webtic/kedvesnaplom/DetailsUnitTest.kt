package com.webtic.kedvesnaplom

import android.util.Log
import com.webtic.kedvesnaplom.model.Bejegyzes
import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.network.dto.GetBejegyzesekListaElemDto
import com.webtic.kedvesnaplom.network.dto.PutBejegyzesDto
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import com.webtic.kedvesnaplom.ui.details.DetailsRepository
import com.webtic.kedvesnaplom.ui.main.MainRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class DetailsUnitTest {
    @Test
    fun getBejegyzes() = runTest {
        val mockBejegyzesService = mockk<BejegyzesService>()
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzes(any()) } returns Bejegyzes(5, "hal", "2022-05-07", "amet")
        val mockMainRepository = mockk<MainRepository>()
        val repository = DetailsRepository(mockBejegyzesService, mockBejegyzesDao, mockMainRepository)

        val bejegyzes = repository.getBejegyzes(5)

        Assert.assertNotNull(bejegyzes)
        Assert.assertEquals(5, bejegyzes?.azonosito)
        Assert.assertEquals("amet", bejegyzes?.tartalom)
    }

    @Test
    fun getBejegyzesNonexistent() = runTest {
        val mockBejegyzesService = mockk<BejegyzesService>()
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        coEvery { mockBejegyzesDao.getBejegyzes(any()) } returns null
        val mockMainRepository = mockk<MainRepository>()
        val repository = DetailsRepository(mockBejegyzesService, mockBejegyzesDao, mockMainRepository)

        val bejegyzes = repository.getBejegyzes(5)

        Assert.assertNull(bejegyzes)
    }

    @Test
    fun putBejegyzes() = runTest {
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.putBejegyzes(any()) } returns Unit
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        val mockMainRepository = mockk<MainRepository>()
        coEvery { mockMainRepository.loadBejegyzesek(true) } returns listOf(
            Bejegyzes(5, "hal", "2022-05-07", "amet"),
            Bejegyzes(4, "hal", "2022-05-06", "sit")
        )
        val repository = DetailsRepository(mockBejegyzesService, mockBejegyzesDao, mockMainRepository)

        repository.putBejegyzes(PutBejegyzesDto("hal", "amet"))

        coVerify { mockMainRepository.loadBejegyzesek(true) }
    }

    @Test
    fun putBejegyzesNetworkError() = runTest {
        val mockBejegyzesService = mockk<BejegyzesService>()
        coEvery { mockBejegyzesService.putBejegyzes(any()) } throws Exception()
        val mockBejegyzesDao = mockk<BejegyzesDao>()
        val mockMainRepository = mockk<MainRepository>()
        coEvery { mockMainRepository.loadBejegyzesek(true) } returns listOf(
            Bejegyzes(5, "hal", "2022-05-07", "amet"),
            Bejegyzes(4, "hal", "2022-05-06", "sit")
        )
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        val repository = DetailsRepository(mockBejegyzesService, mockBejegyzesDao, mockMainRepository)

        repository.putBejegyzes(PutBejegyzesDto("hal", "amet"))

        coVerify(inverse = true) { mockMainRepository.loadBejegyzesek(true) }
        verify { Log.d(any(), any()) }
    }
}