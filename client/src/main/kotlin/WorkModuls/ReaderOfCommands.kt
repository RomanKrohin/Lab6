package WorkModuls

import Client.Client

/**
 * Класс для чтения, выборки и вывода результатов команд
 */
class ReaderOfCommands {

    val listOfCommands= mutableListOf("show", "info", "help", "history", "save", "clear", "exit")

    /**
     * Класс для чтения, выборки и вывода результатов команд
     * @param collection
     * @param path
     */
    fun read() {
        val asker = Asker()
        val tokens = Tokenizator()
        val client = Client()
        val readerOfScripts = ReaderOfScripts()
        while (true) {
            val command = asker.askCommand()
            val commandComponents = tokens.tokenizateCommand(command)
            if (commandComponents[0] == "execute_script") {
                val listOfTasks =
                    readerOfScripts.readScript(commandComponents[1], tokens, mutableListOf())
                for (i in listOfTasks) {
                    client.outputStreamHandler(i)
                }
            } else {
                if (listOfCommands.contains(commandComponents[0])){
                    client.outputStreamHandler(Task(commandComponents, listOfCommands = listOfCommands))
                    listOfCommands.addAll(client.returnNewCommands())
                    client.resetNewCommands()
                }
                else{
                    println("Command ${commandComponents[0]} does not exist")
                }
            }
        }
    }
}