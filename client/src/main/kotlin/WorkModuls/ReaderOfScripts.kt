package WorkModuls

import java.io.BufferedReader
import java.io.FileReader

class ReaderOfScripts : CreateTask {



    fun readScript(
        path: String,
        printer: Printer,
        tokenizator: Tokenizator,
        historyOfPaths: MutableList<String>,
    ): MutableList<Task> {
        try {
            if (!historyOfPaths.contains(path)) {
                historyOfPaths.add(path)
                val bufferedReader = BufferedReader(FileReader(path))
                val listOfTasks = mutableListOf<Task>()
                while (true) {
                    if (bufferedReader.ready()) {
                        val components = tokenizator.tokenizateCommand(bufferedReader.readLine())
                        if (components[0].equals("execute_script")) {
                            val extensionListOfTask = readScript(components[1], printer, tokenizator, historyOfPaths)
                            listOfTasks.addAll(extensionListOfTask)
                        } else {
                            listOfTasks.add(createTask(components))
                        }
                    } else {
                        break
                    }
                }
                return listOfTasks
            } else {
                printer.printHint("Данный файл был использован ${path}")
                return mutableListOf()
            }
        } catch (e: Exception) {
            printer.printHint("Problem with script")
            return mutableListOf()
        }
    }

    override fun createTask(describe: MutableList<String>): Task {
        return Task(describe)
    }

}