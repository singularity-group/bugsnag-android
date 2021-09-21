package com.bugsnag.android

import android.util.JsonReader
import com.bugsnag.android.internal.JournalKeys
import com.bugsnag.android.internal.Journalable
import java.io.IOException

/**
 * Information about the current user of your application.
 */
class User @JvmOverloads internal constructor(
    /**
     * @return the user ID, by default a UUID generated on installation
     */
    val id: String? = null,

    /**
     * @return the user's email, if available
     */
    val email: String? = null,

    /**
     * @return the user's name, if available
     */
    val name: String? = null
) : JsonStream.Streamable, Journalable {

    @Throws(IOException::class)
    override fun toStream(writer: JsonStream) = writer.value(toJournalSection())

    override fun toJournalSection(): Map<String, Any?> = mapOf(
        JournalKeys.keyId to id,
        JournalKeys.keyEmail to email,
        JournalKeys.keyName to name
    )

    internal companion object : JsonReadable<User> {
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"

        override fun fromReader(reader: JsonReader): User {
            var user: User
            with(reader) {
                beginObject()
                var id: String? = null
                var email: String? = null
                var name: String? = null

                while (hasNext()) {
                    val key = nextName()
                    val value = nextString()
                    when (key) {
                        KEY_ID -> id = value
                        KEY_EMAIL -> email = value
                        KEY_NAME -> name = value
                    }
                }
                user = User(id, email, name)
                endObject()
            }
            return user
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (email != other.email) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}
