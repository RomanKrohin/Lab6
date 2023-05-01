package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.lang.RuntimeException

/**
 * Класс команды, который выводит последние 12 введенных команд
 */
class CommandHistory(workCollection: Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
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
            val answer = createReversedAnswer()
            answer.setterResult(key)
            return answer
        } catch (e: RuntimeException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "History")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}