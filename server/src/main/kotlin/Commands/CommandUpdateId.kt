package Commands

import Collections.ActionsWithCollection
import Collections.Collection
import Exceptions.CommandException
import StudyGroupInformation.StudyGroup
import WorkModuls.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.channels.SocketChannel
import java.util.stream.Collectors

/**
 * Класс команды, которая обновляет id объекта коллекции по его ключу
 */
class CommandUpdateId(workCollection: Collection<String, StudyGroup>) : Command(),
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
            val components = key.split(" ")
            if (components.size == 2) {
                val answer = createReversedAnswer()
                collection.collection.values.stream().collect(Collectors.toList())
                    .filter { it -> it.getId() == components[0].toLong() }.forEach { it.setId(components[1].toLong()) }
                return answer
            }
            else {
                val answer = createReversedAnswer()
                answer.setterResult("/id/")
                return answer
            }
        } catch (e: CommandException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Update id")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }
}