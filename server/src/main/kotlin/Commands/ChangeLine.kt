package Commands

/**
 * Интерфейс для разделения строки введенной пользователем на компоненты
 */

interface ChangeLine {
    /**
     * Метод для возвращения компоненты команд
     * @param command
     * @param path
     * @return MutableList<String>
     */
    fun returnCommandComponents(command: MutableList<String>, path: String, history: MutableList<String>): MutableList<String>
}