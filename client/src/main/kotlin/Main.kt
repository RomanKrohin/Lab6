import WorkModuls.Reader
import WorkModuls.WorkWithReader

fun main(){
    Executer().executeWork()
}

class Executer: WorkWithReader{

    fun executeWork(){
        createReader().reader()

    }

    override fun createReader(): Reader {
        return Reader()
    }
}