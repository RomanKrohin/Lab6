package WorkModuls

import Collections.Collection
import Commands.*
import StudyGroupInformation.StudyGroup
import java.nio.channels.SocketChannel
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors

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
    task: Task,
) : CreateCommand {
    private val listOfPaths = pathsForExecuteScripts
    private var listOfCommand = createCommands(collection, history, listOfPaths, pathOfFile, task)
    private val workHistory = history
    private val workCollection = collection
    private val workPath = pathOfFile
    private val workTask = task
    private val logger = Logger.getLogger("logger")


    /**
     * Метод выборки команд
     * @param collection
     * @param history
     * @param pathsForExecuteScripts
     * @param pathOfFile
     */
    fun chooseCoomand(commandComponent: MutableList<String>, listOfOldCommands: MutableList<String>): Answer {
        logger.log(Level.INFO, "Выборка команды")
        commandComponent[0].lowercase()
        if (commandComponent[0] == "execute_script") {
            listOfPaths.add(commandComponent[1])
            listOfCommand = createCommands(workCollection, workHistory, listOfPaths, workPath, workTask)
        }
        val command = listOfCommand[commandComponent[0]]?.let {
            val answer = it.commandDo(commandComponent[1])
            answer.setNewCommands(
                listOfCommand.keys.stream().collect(Collectors.toList()).filter { !listOfOldCommands.contains(it) })
            return answer
        }
        return Answer(commandComponent[0])
    }

    override fun createCommands(
        collection: Collection<String, StudyGroup>,
        history: MutableList<String>,
        pathsForExecuteScripts: MutableList<String>,
        pathOfFile: String,
        task: Task,
    ): Map<String, Command> {
        return mapOf<String, Command>(
            "show" to CommandShow(collection),
            "save" to CommandSave(collection),
            "history" to CommandHistory(collection),
            "help" to CommandHelp(),
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

}