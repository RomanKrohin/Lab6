package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.lang.RuntimeException
import java.util.*

/**
 * Класс команды, которая удаляет объект из коллекции по его ключу
 */

class CommandRemove(workCollection: Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
    var collection: Collection<String, StudyGroup>

    init {
        collection = workCollection
    }

    /**
     *  Метод работы команды
     *  @param collection
     *  @param key
     */
    override fun commandDo(key: String): Answer {
        try {
            var answer = createReversedAnswer()
            collection.remove(key.uppercase(Locale.getDefault()))
            return answer
        } catch (e: RuntimeException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Remove by key")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }
}