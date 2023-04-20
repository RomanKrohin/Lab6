package WorkModuls

import Commands.Command
import StudyGroupInformation.StudyGroup
import java.nio.channels.SocketChannel

/**
 * Интерфейс для создания массива с командами
 */

interface CreateCommand {
    /**
     * Метод для создания массива команд
     * @return Map<String, Command>
     */
    fun createCommands(
        collection: Collections.Collection<String, StudyGroup>,
        history: MutableList<String>,
        pathsForExecuteScripts: MutableList<String>,
        pathOfFile: String,
        task: Task
    ): Map<String, Command>
}