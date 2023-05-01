package Commands

import WorkModuls.Answer
import java.lang.RuntimeException

/**
 * Класс команды, которая заканчивает работу приложения
 */
class CommandExit : Command() {
    /**
     *  Метод работы команды
     *  @param collection
     *  @param key
     */
    override fun commandDo(key: String): Answer {
        val answer= Answer()
        try {
            answer.result="/exit/"
            return answer
        } catch (e: RuntimeException) {
            answer.result="Command exception"
            return answer
        }
    }


}