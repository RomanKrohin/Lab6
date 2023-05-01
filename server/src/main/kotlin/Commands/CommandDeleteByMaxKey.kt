package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.lang.RuntimeException
import java.util.*
import java.util.stream.Collectors

/**
 * Класс команды, которая удаляет объекты, значение ключа которого больше чем у заданного
 */
class CommandDeleteByMaxKey(workCollection: Collection<String, StudyGroup>) : Command(),
    WorkWithAnswer {
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
            if (collection.collection.keys.contains(key.uppercase(Locale.getDefault()))) {
                collection.collection.keys.stream().collect(Collectors.toList())
                    .filter { it -> it.hashCode() > key.uppercase().hashCode() }.forEach(collection.collection::remove)
            }
            return answer
        } catch (e: RuntimeException) {
            return createAnswer()
        }
    }


    override fun createAnswer(): Answer {
        return Answer(nameError = "Delete by max key")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }
}