package no.demo.learnkotlin.services

import kotlinx.coroutines.*
import no.demo.learnkotlin.helper.PropertiesReader
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.stereotype.Service
import java.io.File

@Service
class SearchService {

    val folderPath = PropertiesReader.getProperty("search.folder")

    fun searchFromExcel(keyword: String): List<List<List<String>>> {
        var result = mutableListOf<List<List<String>>>()
        val folder = File(folderPath)
        runBlocking {
            if (folder.exists() && folder.isDirectory) {
                val excelFiles = folder.listFiles { file -> file.extension in listOf("xls", "xlsx") } ?: emptyArray()
                val tasks = excelFiles.map { file ->
                    CoroutineScope(Dispatchers.Default).async {
                        println("Searching in file :" + file.name)
                        readFromFile(WorkbookFactory.create(file), keyword)

                    }
                }
                result = tasks.awaitAll().filterNot { it.isEmpty() } as MutableList<List<List<String>>>
            } else {
                println("Folder not found or is not a directory")
            }
        }
        return result
    }

    private fun readFromFile(file: Workbook, keyword: String): MutableList<List<String>> {
        var entry = mutableListOf<String>()
        var results = mutableListOf<List<String>>()

        try {
            for (sheet in file) {
                for (row in sheet) {
                    for (cell in row) {
                        if (cell.toString().contains(keyword, ignoreCase = true)) {
                            entry.clear()
                            (row.cellIterator().forEach { entry.add(it.toString()) })
                            results.add(entry)
                            break
                        }
                    }
                }
            }
        } finally {
            file.close()
        }
         return results

    }

}