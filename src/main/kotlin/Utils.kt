import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


fun String.prependIfMissing(prep: String) = if (startsWith(prep)) this else prep + this


fun DI.Builder.environmentString(
    tag: String,
    name: String = tag,
    default: String? = null,
    transform: ((String) -> String)? = null
) =
    bind<String>(tag) with singleton {
        (System.getenv(name) ?: default)?.let { if (transform != null) transform(it) else it }
            ?: error("Cannot find $name in environment")
    }

fun DI.Builder.environmentBoolean(
    tag: String, name: String = tag,
    default: Boolean? = null,
    transform: ((Boolean) -> Boolean)? = null
) =
    bind<Boolean>(tag) with singleton {
        (System.getenv(name)?.toBoolean() ?: default)?.let { if (transform != null) transform(it) else it }
            ?: error("Cannot find $name in environment")
    }
