package StudyGroupInformation

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class StudyGroup(
    private val name: String = "",
    private val coordinates: Coordinates,
    private val studentCount: Long,
    private val shouldBeExpelled: Int,
    private val averageMark: Int,
    private val formOfEducation: FormOfEducation? = null,
    private val groupAdmin: Person,
): java.io.Serializable {

    private val localDateTime: LocalDateTime by lazy { java.time.LocalDateTime.now() }
    private var id: Long = 0
    init {
        localDateTime
    }
}