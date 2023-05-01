package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.*
import java.lang.RuntimeException

/**
 * Класс команды, которая добавляет объект по его ключу
 */
class CommandInsert(workCollection: Collection<String, StudyGroup>, workTask: Task): Command(), WorkWithAnswer {
    var collection: Collection<String, StudyGroup>
    var task: Task
    init {
        collection=workCollection
        task=workTask
    }


    /**
     *  Метод работы команды
     *  @param collection
     *  @param key
     */
    override fun commandDo(key: String): Answer {
        try {
            if (task.studyGroup!=null){
                val answer= createReversedAnswer()
                val listOfId = mutableListOf<Long>(0)
                for (i in collection.collection.values){
                    listOfId.add(i.getId())
                }
                task.studyGroup?.let {
                    it.setId(listOfId.max()+1)
                    collection.add(it, key)
                }
                return answer
            }
            else{
                val answer= createReversedAnswer()
                answer.setterResult("/insert/")
                return answer
            }
        }
        catch (e: RuntimeException){
            return createAnswer()
        }
    }


    override fun createAnswer(): Answer {
        return Answer(nameError = "Insert")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}