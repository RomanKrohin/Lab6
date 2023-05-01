import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.CreateCollection
import WorkModuls.ExecuteActionsWithRead
import WorkModuls.ReadFile
import java.lang.RuntimeException

/**
 * Точка вхождения в программу
 * @param args
 */
fun main() {
    try {
        val executer: Executer = Executer("/home/roman/test.yaml")
    } catch (e: RuntimeException) {
        throw e
    }

}

class Executer(path: String) : ExecuteActionsWithRead, CreateCollection {
    /**
     * Класс инициализатор работы
     * @param path
     */
    init {
        val test = createReader(path)
        executeRead(test, createCollection())
    }

    //Инициализация чтения
    override fun executeRead(readFile: ReadFile, collection: Collection<String, StudyGroup>) {
        readFile.readFile(collection)
    }

    //Создание экземпляра объекта, который считывает файл
    override fun createReader(path: String): ReadFile {
        return ReadFile(path)
    }

    //Создание экземпляра коллекции
    override fun createCollection(): Collection<String, StudyGroup> {
        return Collection()
    }
}