package Commands

import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.encodeToString
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.Writer

/**
 * Класс команды, которая очищает файл и пишет, переводит объекты, сохраненные в коллекции, в строчный формат и записывает их в файл
 */
class CommandSave(workCollection: Collection<String, StudyGroup>): Command(){
    var collection: Collection<String, StudyGroup>
    init {
        collection=workCollection
    }

    /**
     *  Метод работы команды
     *  @param collection
     *  @param key
     */
    override fun commandDo(key: String): Answer {
        val answer= Answer()
        return try {
            val outputStream = FileOutputStream(key)
            val writer: Writer = OutputStreamWriter(outputStream)
            writer.write(Yaml.default.encodeToString(collection.collection.toMap()))
            writer.close()
            answer
        } catch (e: Exception){
            answer.result="Command exception"
            answer
        }
    }


}