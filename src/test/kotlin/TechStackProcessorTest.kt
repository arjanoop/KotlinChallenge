import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TechStackProcessorTest {
    private val processor = TechStackProcessor()

    @Test
    fun `test processTechStack with nested URLs`() {
        val techMap = mutableMapOf(
            "https://example.com" to mutableSetOf("Java", "Kotlin"),
            "https://example.com/service" to mutableSetOf("Java", "Spring"),
            "https://example.com/service/api" to mutableSetOf("Spring", "Hibernate"),
            "https://example.com/service/api/v1" to mutableSetOf("Hibernate", "JPA"),
            "https://example.com/service/api/v2" to mutableSetOf("Spring", "Hibernate"),
        )
        processor.processTechStack(techMap)
        assertEquals(4, techMap.size)
        assertEquals(setOf("Java", "Kotlin"), techMap["https://example.com"])
        assertEquals(setOf("Spring"), techMap["https://example.com/service"])
        assertEquals(setOf("Hibernate"), techMap["https://example.com/service/api"])
        assertEquals(setOf("JPA"), techMap["https://example.com/service/api/v1"])
    }

    @Test
    fun `test processTechStack with empty tech stack`() {
        val techMap = mutableMapOf<String, MutableSet<String>>()
        processor.processTechStack(techMap)
        assertTrue(techMap.isEmpty())
    }

    @Test
    fun `test processTechStack with no nested URLs`() {
        val techMap = mutableMapOf(
            "https://example.com" to mutableSetOf("Java", "Kotlin"),
            "https://another.com" to mutableSetOf("Java", "Kotlin")
        )
        processor.processTechStack(techMap)
        assertEquals(setOf("Java", "Kotlin"), techMap["https://example.com"])
        assertEquals(setOf("Java", "Kotlin"), techMap["https://another.com"])
    }

    @Test
    fun `test processTechStack with double slash in nested URLs`() {
        val techMap = mutableMapOf(
            "https://example.com/api///v1/auth//user" to mutableSetOf("Java", "Kotlin"),
            "https://another.com" to mutableSetOf("Python", "Django")
        )
        processor.processTechStack(techMap)
        assertEquals(setOf("Java", "Kotlin"), techMap["https://example.com/api///v1/auth//user"])
        assertEquals(setOf("Python", "Django"), techMap["https://another.com"])
    }

    @Test
    fun `test processTechStack with full URLs`() {
        val techMap = mutableMapOf(
            "https://example.com/service/api/v1/data?name=jhon" to mutableSetOf("Java", "Kotlin"),
            "https://example.com/service/api/v1" to mutableSetOf("Python", "Django")
        )
        processor.processTechStack(techMap)
        assertEquals(setOf("Java", "Kotlin"), techMap["https://example.com/service/api/v1/data?name=jhon"])
        assertEquals(setOf("Python", "Django"), techMap["https://example.com/service/api/v1"])
    }

    @Test
    fun `test processTechStack with empty parent directory`() {
        val techMap = mutableMapOf(
            "https://example.com/" to mutableSetOf(),
            "https://example.com/sub" to mutableSetOf("Spring", "Hibernate")
        )
        processor.processTechStack(techMap)
        assertEquals(setOf("Spring", "Hibernate"), techMap["https://example.com/sub"])
    }

    @Test
    fun `test processTechStack with special characters in URLs`() {
        val techMap = mutableMapOf(
            "https://example.com/a-b" to mutableSetOf("Java", "Spring"),
            "https://example.com/a_b" to mutableSetOf("Spring", "Hibernate")
        )
        processor.processTechStack(techMap)
        assertEquals(setOf("Java", "Spring"), techMap["https://example.com/a-b"])
        assertEquals(setOf("Spring","Hibernate"), techMap["https://example.com/a_b"])
    }
}