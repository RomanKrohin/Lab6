package Commands

import Collections.Collection
import Exceptions.CommandException
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.util.stream.Collector
import java.util.stream.Collectors

/**
 * Класс команды, которая выводит в порядке убывания значение поля average mark всех объектов
 */
class CommandPrintFieldDescendingAverageMark(workCollection: Collections.Collection<String, StudyGroup>) : Command(),
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
            answer.setterResult(collection.collection.values.stream().map { it -> it.getAverageMark() }
                .collect(Collectors.toList()).sorted().reversed().toString())
            return answer
        } catch (e: CommandException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "print_field_descending_average_mark")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}