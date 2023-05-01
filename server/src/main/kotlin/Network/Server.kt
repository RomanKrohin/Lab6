import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.ExecuterOfCommands
import WorkModuls.Task
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

class Server(){

    val reader = ExecuterOfCommands()
    val logger= Logger.getLogger("logger")

    fun startSever(collection: Collection<String, StudyGroup>, path: String) {
        logger.log(Level.INFO, "Ожидание подключения")
        try {
            val serverSocketChannel = ServerSocketChannel.open()
            serverSocketChannel.bind(InetSocketAddress("localhost", 8000))
            val br= BufferedReader(InputStreamReader(System.`in`))
            while (serverSocketChannel != null) {
                    if (br.ready()) {
                        val serverCommand = br.readLine()
                        if (serverCommand.equals("exitAdmin")) {
                            exitProcess(0)
                        }
                        if (serverCommand.equals("saveAdmin")){
                            Commands.CommandSave(collection).commandDo(" ")
                        }
                    }
                val clientSocketChannel = serverSocketChannel.accept()
                handlerOfInput(clientSocketChannel, collection, path)
            }
            serverSocketChannel?.close()
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Ошибка подключения")
        }
    }

    fun handlerOfInput(clientSocketChannel: SocketChannel, collection: Collection<String, StudyGroup>, path: String) {
        logger.log(Level.INFO, "Получение информации")
        try {
            val objectInputStream = ObjectInputStream(clientSocketChannel.socket().getInputStream())
            val task = objectInputStream.readObject() as Task
            handlerOfOutput(clientSocketChannel, reader.reader(collection, path, task.describe, task, task.listOfCommands))
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Ошибка получения информации")
        }
    }

    fun handlerOfOutput(clientSocketChannel: SocketChannel, answer: Answer) {
        logger.log(Level.INFO, "Передача информации")
        try {
            val objectOutputStream = ObjectOutputStream(clientSocketChannel.socket().getOutputStream())
            objectOutputStream.writeObject(answer)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Ошибка передачи информации")
        }
    }

}