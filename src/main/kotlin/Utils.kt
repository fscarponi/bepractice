fun String.prependIfMissing(prep: String) = if (startsWith(prep)) this else prep + this


fun environmentString(
    tag: String,
    name: String = tag,
    default: String? = null,
    transform: ((String) -> String)? = null
) =
    (System.getenv(name) ?: default)?.let { if (transform != null) transform(it) else it }
        ?: error("Cannot find $name in environment")


fun environmentBoolean(
    tag: String, name: String = tag,
    default: Boolean? = null,
    transform: ((Boolean) -> Boolean)? = null
) = (System.getenv(name)?.toBoolean() ?: default)?.let { if (transform != null) transform(it) else it }
    ?: error("Cannot find $name in environment")

