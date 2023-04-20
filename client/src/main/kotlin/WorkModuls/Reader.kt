package WorkModuls

import Client.Client

/**
 * Класс для чтения, выборки и вывода результатов команд
 */
class Reader : WorkWithAsker, WorkWithPrinter, WorkWithTokenizator, CreateClient, CreateTask, WorkWithSriptReader {

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
                    println(i.describe)
                    client.handlerOfOutputStream(i)
                }
            } else {
                client.handlerOfOutputStream(createTask(commandComponents))
            }
        }
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
        return Task(describe)
    }

    override fun createScriptReader(): ReaderOfScripts {
        return ReaderOfScripts()
    }
}