package no.demo.learnkotlin.controller

import no.demo.learnkotlin.services.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchFileController(private val searchService: SearchService) {

    @GetMapping("/searchInFile")
    fun searchKeywordInExcel(@RequestParam("keyword") keyword: String): List<List<List<String>>> {

        return searchService.searchFromExcel(keyword)
    }
}