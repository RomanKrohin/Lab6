package Commands

import Exceptions.CommandException
import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import kotlin.system.exitProcess

/**
 * Класс команды, которая заканчивает работу приложения
 */
class CommandExit : Command(), WorkWithAnswer {
    /**
     *  Метод работы команды
     *  @param collection
     *  @param key
     */
    override fun commandDo(key: String): Answer {
        try {
            val answer= createAnswer()
            answer.setterResult("/exit/")
            return answer
        } catch (e: CommandException) {
            return createAnswer()
        }
    }

    override fun createAnswer(): Answer {
        return Answer(nameError = "Exit")
    }

    override fun createReversedAnswer(): Answer {
        return Answer(false)
    }

}