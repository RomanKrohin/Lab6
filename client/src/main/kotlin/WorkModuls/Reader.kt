package WorkModuls

import Client.Client

/**
 * Класс для чтения, выборки и вывода результатов команд
 */
class Reader : WorkWithAsker, WorkWithPrinter, WorkWithTokenizator, CreateClient, CreateTask, WorkWithSriptReader {

    val listOfCommands= mutableListOf("show", "info", "help", "history", "save", "clear", "exit")

    /**
     * Класс для чтения, выборки и вывода результатов команд
     * @param collection
     * @param path
     */
    fun reader() {
        val asker = createAsker()
        val tokens = createTokenizator()
        val client = createClient()
        val readerOfScripts = createScriptReader()
        while (true) {
            val command = asker.askCommand()
            val commandComponents = tokens.tokenizateCommand(command)
            if (commandComponents[0].equals("execute_script")) {
                val listOfTasks =
                    readerOfScripts.readScript(commandComponents[1], createPrinter(), tokens, mutableListOf())
                for (i in listOfTasks) {
                    client.handlerOfOutputStream(i)
                }
            } else {
                if (listOfCommands.contains(commandComponents[0])){
                    client.handlerOfOutputStream(createTask(commandComponents))
                    setNewCommands(client.returnNewCommands())
                    client.resetNewCommands()
                }
                else{
                    println("Command ${commandComponents[0]} does not exist")
                }
            }
        }
    }

    fun setNewCommands(list: List<String>){
        listOfCommands.addAll(list)
    }

    override fun createAsker(): Asker {
        return Asker()
    }

    override fun createPrinter(): Printer {
        return Printer()
    }

    override fun createTokenizator(): Tokenizator {
        return Tokenizator()
    }

    override fun createClient(): Client {
        return Client()
    }

    override fun createTask(describe: MutableList<String>): Task {
        return Task(describe, listOfCommands = listOfCommands)
    }

    override fun createScriptReader(): ReaderOfScripts {
        return ReaderOfScripts()
    }
}