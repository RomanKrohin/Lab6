package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.encodeToString
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Класс команды, которая выводит объекты, сохраненные в коллекции, в текстовом формате
 */

class CommandShow(workCollection: Collection<String, StudyGroup>) : Command(), WorkWithAnswer {
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
            val listOfStudyGroup=collection.collection.values
            listOfStudyGroup.stream().sorted(Comparator.comparing ( StudyGroup::getName )).collect(Collectors.toList()).forEach {
                answer.setterResult(answer.getAnswer()+"\n----------\n"+Yaml.default.encodeToString(
                    it
                ))
            }
            return answer
        } catch (e: Exception) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Show")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }
}