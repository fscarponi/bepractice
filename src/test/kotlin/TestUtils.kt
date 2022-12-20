import java.time.LocalDateTime

object Samples {

    val users = listOf( //size 6
        UserAuth(
            username = "jhondoe",
            firstName = "Jhon",
            lastName = "Doe",
            mail = "jhondoe@gmail.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()
        ),
        UserAuth(
            username = "username1",
            firstName = "firstName1",
            lastName = "lastName1",
            mail = "mail@username1.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()
        ),
        UserAuth(
            username = "username2",
            firstName = "firstName2",
            lastName = "lastName2",
            mail = "mail@username2.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()
        ),
        UserAuth(
            username = "username3",
            firstName = "firstName3",
            lastName = "lastName3",
            mail = "mail@username3.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()
        ),
        UserAuth(
            username = "username4",
            firstName = "firstName4",
            lastName = "lastName4",
            mail = "mail@username4.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()
        ),
        UserAuth(
            username = "username5",
            firstName = "Jhon",
            lastName = "lastName5",
            mail = "mail@username5.com",
            hashPassword = "test".digestMD5(),
            signupDate = LocalDateTime.now()

        )
    )

    val lvl4Tree = Node(
        value = "a",
        leftChild = Node(
            value = "b",
            leftChild = null,
            rightChild = Node(
                value = "c",
                leftChild = null,
                rightChild = Node(
                    value = "d",
                    leftChild = null,
                    rightChild = null
                )
            )
        ),
        rightChild = Node(
            value = "f",
            leftChild = null,
            rightChild = null
        )
    )

    val lvl1tree = Node(
        value = "a",
        null,
        null
    )

    val lvl2tree = Node(
        value = "a",
        Node(
            value = "b",
            null,
            null
        ),
        null
    )
}
