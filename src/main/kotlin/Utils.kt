import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.litote.kmongo.util.KMongoUtil
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

fun String.prependIfMissing(prep: String) = if (startsWith(prep)) this else prep + this


fun environmentString(
    tag: String, name: String = tag, default: String? = null, transform: ((String) -> String)? = null
) = (System.getenv(name) ?: default)?.let { if (transform != null) transform(it) else it }
    ?: error("Cannot find $name in environment")


fun environmentBoolean(
    tag: String, name: String = tag, default: Boolean? = null, transform: ((Boolean) -> Boolean)? = null
) = (System.getenv(name)?.toBoolean() ?: default)?.let { if (transform != null) transform(it) else it }
    ?: error("Cannot find $name in environment")


fun String.isAValidMail() = Pattern.compile(
    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
).matcher(this).matches()


fun String.digestMD5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16).padStart(32, '0')


inline fun <reified T> Json.encodeToBson(element: T) = KMongoUtil.toBson(this.encodeToString(element))
