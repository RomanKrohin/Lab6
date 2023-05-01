package Commands

import WorkModuls.Answer
import WorkModuls.WorkWithAnswer
import java.lang.RuntimeException

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
        } catch (e: RuntimeException) {
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