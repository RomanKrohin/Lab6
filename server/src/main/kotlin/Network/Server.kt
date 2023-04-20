import Collections.Collection
import StudyGroupInformation.StudyGroup
import WorkModuls.Answer
import WorkModuls.Reader
import WorkModuls.Task
import WorkModuls.WorkWithReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.logging.Level
import java.util.logging.Logger

class Server() : WorkWithReader {

    val reader = createReader()
    val logger= Logger.getLogger("logger")

    fun startSever(collection: Collection<String, StudyGroup>, path: String) {
        logger.log(Level.INFO, "Ожидание подключения")
        try {
            val serverSocketChannel = ServerSocketChannel.open()
            serverSocketChannel.bind(InetSocketAddress("localhost", 1234))
            while (serverSocketChannel != null) {
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
            handlerOfOutput(clientSocketChannel, reader.reader(collection, path, task.describe, task))
            objectInputStream.close()
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Ошибка получения информации")
        }
    }

    fun handlerOfOutput(clientSocketChannel: SocketChannel, answer: Answer) {
        logger.log(Level.INFO, "Передача информации")
        try {
            val objectOutputStream = ObjectOutputStream(clientSocketChannel.socket().getOutputStream())
            objectOutputStream.writeObject(answer)
            objectOutputStream.close()
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Ошибка передачи информации")
        }
    }

    override fun createReader(): Reader {
        return Reader()
    }
}