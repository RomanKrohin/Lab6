package WorkModuls

import Collections.Collection
import Commands.*
import StudyGroupInformation.StudyGroup
import java.nio.channels.SocketChannel
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Класс управления командами
 * @param collection
 * @param history
 * @param pathsForExecuteScripts
 * @param pathOfFile
 */
class ChooseCommand(
    collection: Collection<String, StudyGroup>,
    history: MutableList<String>,
    pathsForExecuteScripts: MutableList<String>,
    pathOfFile: String,
    task: Task
) : CreateCommand, WorkWithAnswer, WorkWithCommandExceptionAnswer {
    private val listOfPaths = pathsForExecuteScripts
    private var listOfCommand = createCommands(collection, history, listOfPaths, pathOfFile, task)
    private val workHistory = history
    private val workCollection = collection
    private val workPath = pathOfFile
    private val workTask= task
    private val logger= Logger.getLogger("logger")


    /**
     * Метод выборки команд
     * @param collection
     * @param history
     * @param pathsForExecuteScripts
     * @param pathOfFile
     */
    fun chooseCoomand(commandComponent: MutableList<String>): Answer {
        logger.log(Level.INFO, "Выборка команды")
        commandComponent[0].lowercase()
        if (commandComponent[0] == "execute_script") {
            listOfPaths.add(commandComponent[1])
            listOfCommand = createCommands(workCollection, workHistory, listOfPaths, workPath, workTask)
        }
        val command = listOfCommand[commandComponent[0]]?.let {
            val answer = it.commandDo(commandComponent[1])
            return answer
        }
        return createCommandExceptionAnswer(commandComponent[0])
    }

    override fun createCommands(
        collection: Collection<String, StudyGroup>,
        history: MutableList<String>,
        pathsForExecuteScripts: MutableList<String>,
        pathOfFile: String,
        task: Task
    ): Map<String, Command> {
        return mapOf<String, Command>(
            "show" to CommandShow(collection),
            "save" to CommandSave(collection),
            "history" to CommandHistory(collection),
            "help" to CommandHelp(),
            "exit" to CommandExit(),
            "info" to CommandInfo(collection),
            "clear" to CommandClear(collection),
            "max_by_name" to CommandMaxName(collection),
            "print_field_descending_average_mark" to CommandPrintFieldDescendingAverageMark(collection),
            "remove_greater_key" to CommandDeleteByMaxKey(collection),
            "remove_lower_key" to CommandDeleteByMinKey(collection),
            "count_less_than_group_admin" to CommandCountLessThanAdmin(collection),
            "remove" to CommandRemove(collection),
            "update id" to CommandUpdateId(collection),
            "insert" to CommandInsert(collection, task)
        )
    }

    override fun createAnswer(): Answer {
        return Answer()
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

    override fun createCommandExceptionAnswer(nameCommand: String): Answer {
        val answer = createAnswer()
        answer.nameError += ": ${nameCommand}"
        return answer
    }
}