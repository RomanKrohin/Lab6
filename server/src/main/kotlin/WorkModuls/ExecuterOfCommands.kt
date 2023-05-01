package WorkModuls

import Collections.Collection
import Commands.WorkWithHistory
import StudyGroupInformation.StudyGroup
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Класс для чтения, выборки и вывода результатов команд
 */
class ExecuterOfCommands : WorkWithHistory {

    private val history = listOf<String>().toMutableList()
    private val pathsForExecuteScripts = listOf<String>().toMutableList()
    private val logger = Logger.getLogger("logger")


    /**
     * Класс для чтения, выборки и вывода результатов команд
     * @param collection
     * @param path
     */
    fun reader(
        collection: Collection<String, StudyGroup>,
        path: String,
        command: MutableList<String>,
        task: Task,
        listOfOldCommand: MutableList<String>
    ): Answer {
        logger.log(Level.INFO, "Чтение команды")
        val tokens = Tokenizator()
        val chooseCommand = ChooseCommand(collection, history, pathsForExecuteScripts, path, task)
        workWithArrayHistory(command)
        val commandComponents = tokens.tokenizateCommand(command, path, history)
        val answer = chooseCommand.chooseCoomand(commandComponents, listOfOldCommand)
        return answer
    }

    override fun workWithArrayHistory(coomand: MutableList<String>) {
        if (history.size > 12) {
            history.removeAt(0)
            history.add(coomand[0])
        } else {
            history.add(coomand[0])
        }
    }

}