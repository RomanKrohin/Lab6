package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.encodeToString
import java.util.*
import java.util.stream.Collectors

/**
 * Класс команды, которая выводит объекты значение поля group admin меньше чем у заданного
 */
class CommandCountLessThanAdmin(workCollection: Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
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
            collection.collection.values.stream().collect(Collectors.toList())
                .filter { it -> it.getAdmin().hashCode() < collection.collection.get(key).hashCode() }
                .forEach { answer.setterResult(answer.getAnswer() + Yaml.default.encodeToString(it)) }
            return answer
        } catch (e: CommandException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Count less then admin")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}