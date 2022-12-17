import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val dateFormat = DateTimeFormatter
        .ofPattern("dd-MM-yyyy HH:mm:ss z")
        .withLocale(Locale.ITALY)
        .withZone(ZoneId.of("CET"))!!

    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val httpDateString = decoder.decodeString()
        return LocalDateTime.parse(httpDateString, dateFormat)
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(dateFormat.format(value))
    }

}
