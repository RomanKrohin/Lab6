package Client

import WorkModuls.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import kotlin.system.exitProcess

class Client : WorkWithPrinter, WorkWithAsker {

    val listOfNewCommands= mutableListOf<String>()

    fun startConnection(): SocketChannel {

        val printer = createPrinter()
        return try {
            val clientSocket = SocketChannel.open()
            clientSocket.socket().connect(InetSocketAddress("localhost", 8000))
            clientSocket
        } catch (e: Exception) {
            printer.printHint("Bad connection")
            SocketChannel.open(InetSocketAddress("localhost", 8000))
            throw e
        }
    }

    fun handlerOfOutputStream(task: Task) {
        val printer = createPrinter()
        try {
            val clientSocket = startConnection()
            if (clientSocket.isConnected) {
                val objectOutputStream = ObjectOutputStream(clientSocket.socket().getOutputStream())
                objectOutputStream.writeObject(task)
                handlerOfInputSteam(clientSocket, task)
                objectOutputStream.close()
            }
        } catch (e: Exception) {
            printer.printHint("Bad connection")
        }
    }

    fun handlerOfInputSteam(clientSocket: SocketChannel, task: Task) {
        val asker = createAsker()
        val printer = createPrinter()
        val objectInputStream = ObjectInputStream(clientSocket.socket().getInputStream())
        val answer = objectInputStream.readObject() as Answer
        if (answer.getAnswer().equals("/exit/")) {
            exitProcess(0)
        }
        if (answer.getAnswer().equals("/id/")) {
            printer.printHint("Enter new ID")
            task.describe.add(asker.askLong().toString())
            handlerOfOutputStream(task)
        }
        if (answer.getAnswer().equals("/insert/")) {
            task.studyGroup = asker.askStudyGroup()
            handlerOfOutputStream(task)
        } else {
            listOfNewCommands.addAll(answer.listOfNewCommand)
            printer.print(answer)
        }
        objectInputStream.close()
        clientSocket.close()
    }

    fun returnNewCommands(): MutableList<String>{
        return listOfNewCommands
    }

    fun resetNewCommands(){
        listOfNewCommands.clear()
    }
    override fun createPrinter(): Printer {
        return Printer()
    }

    override fun createAsker(): Asker {
        return Asker()
    }


}