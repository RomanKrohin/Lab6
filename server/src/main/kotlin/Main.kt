import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.ReadFile
import java.lang.RuntimeException

/**
 * Точка вхождения в программу
 * @param args
 */
fun main() {
    try {
        val executer: ExecuterReadFile = ExecuterReadFile("/home/roman/test.yaml")
    } catch (e: RuntimeException) {
        throw e
    }

}

class ExecuterReadFile(path: String){
    /**
     * Класс инициализатор работы
     * @param path
     */
    init {
        val readFile = ReadFile(path).readFile(collection = Collection())
    }
}