package no.demo.learnkotlin.services

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SearchServiceTest(@Autowired val searchService: SearchService) {


    @Test
    fun `test search with multiple matching rows`() = runBlocking {
        val result = searchService.searchFromExcel("keyword")
        assertEquals(1, result.size)
        assertTrue(result.toString().contains("data"))

    }

    @Test
    fun `test search with no matching rows`() = runBlocking {
        val result = searchService.searchFromExcel("dummykeyword")
        assertTrue(result.isEmpty())
    }

}
