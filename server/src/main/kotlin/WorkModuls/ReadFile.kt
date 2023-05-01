package WorkModuls

import Collections.Collection
import Server
import StudyGroupInformation.StudyGroup
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import java.io.BufferedReader
import java.io.FileReader
import java.lang.RuntimeException
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Класс модуля чтения файла
 * @param path
 */
open class ReadFile(pathFile: String) : WorkWithServer{
    private var path: String = ""
    private val logger= Logger.getLogger("logger")

    init {
        path = pathFile
    }

    /**
     * Метод чтения файла
     * @param collection
     */
    fun readFile(collection: Collection<String, StudyGroup>) {
        logger.log(Level.INFO, "Чтение файла")
        val bufferedReader = BufferedReader(FileReader(path))
        try {
            var line= ""
            while (true) {
                if (bufferedReader.ready()) {
                    line += "\n" + bufferedReader.readLine()
                } else {
                    break
                }
            }
            val list = Yaml.default.decodeFromString<Map<String, StudyGroup>>(line)
            val checkModule = CheckModule()
            val listOfId = mutableListOf<Long>(0)
            for (i in list) {
                if (!(listOfId.contains(i.value.getId()))) {
                    listOfId.add(i.value.getId())
                    if (checkModule.check(i.value)) collection.add(i.value, i.key)
                } else {
                    i.value.setId(listOfId.max() + 1)
                    listOfId.add(listOfId.max() + 1)
                    if (checkModule.check(i.value)) collection.add(i.value, i.key)
                }
            }
            serverWork(collection, path)
        } catch (e: RuntimeException) {
            logger.log(Level.SEVERE, "Ошибка чтения")
        }
    }

    override fun serverWork(collection: Collection<String, StudyGroup>, path: String) {
        logger.log(Level.INFO, "Старт сервера")
        val server= Server()
        Thread{
            server.startSever(collection, path)
        }.start()
    }
}