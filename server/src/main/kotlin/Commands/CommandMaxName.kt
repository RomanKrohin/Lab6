package Commands

import Collections.Collection
import Exceptions.CommandException
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.encodeToString
import java.util.stream.Collectors

/**
 * Класс команды, которая выводит объект, значение поля name которого наибольшее
 */
class CommandMaxName(workCollection: Collections.Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
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
            val studyGroup = collection.collection.values.stream().collect(Collectors.toList()).maxBy { it.getName() }
            answer.setterResult(Yaml.default.encodeToString(studyGroup))
            return answer
        } catch (e: CommandException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Max_by_name")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}