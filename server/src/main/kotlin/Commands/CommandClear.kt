package Commands

import Collections.ActionsWithCollection
import Collections.Collection
import Exceptions.CommandException
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.util.stream.Collectors

/**
 * Класс команды очищающая коллекцию
 */
class CommandClear(workCollection: Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
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
            collection.collection.keys.stream().collect(Collectors.toList()).forEach(collection.collection::remove)
            return answer
        } catch (e: CommandException) {
            throw e
        }
    }


    override fun createAnswer(): Answer {
        return Answer(nameError = "Clear")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }
}