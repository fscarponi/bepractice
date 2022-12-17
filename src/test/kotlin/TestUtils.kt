import java.util.*

object Samples {

    val users = listOf( //size 6
        User(
            UUID.randomUUID().toString(),
            "jhondoe",
            "Jhon",
            lastName = "Doe",
            mail = "jhondoe@gmail.com"
        ),
        User(
            UUID.randomUUID().toString(),
            "username1",
            "firstName1",
            lastName = "lastName1",
            mail = "mail@username1.com"
        ),
        User(
            UUID.randomUUID().toString(),
            "username2",
            "firstName2",
            lastName = "lastName2",
            mail = "mail@username2.com"
        ),
        User(
            UUID.randomUUID().toString(),
            "username3",
            "firstName3",
            lastName = "lastName3",
            mail = "mail@username3.com"
        ),
        User(
            UUID.randomUUID().toString(),
            "username4",
            "firstName4",
            lastName = "lastName4",
            mail = "mail@username4.com"
        ),
        User(
            UUID.randomUUID().toString(),
            "username5",
            "firstName5",
            lastName = "lastName5",
            mail = "mail@username5.com"
        )
    )
}
