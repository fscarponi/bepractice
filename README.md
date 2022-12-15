# Let's Practice
## Personal challenge

what will see here
- A ktor based backend
- A mongo database driven by Kmongo
- Some Rock 'n' Roll on data structures ~ ! ~ (with coroutines!)
- ✨Magic ✨

## Features
here some endpoint for start the job
- Endpoint 1:
Endpoint that accepts a GET request and returns a simple message in the response. Example JSON request body could be:
``{
  "message": "Hello, world!"
}``
Example JSON response body could be:
``{   
  "response": "This is a simple message."
}``
- Endpoint 2: 
Endpoint that accepts a POST request with a JSON payload. The endpoint should parse the payload, save it into a database and return a response with the parsed data.
an example JSON request body could be:
``{
  "username": "johndoe",
  "password": "s3cr3t",
  "email": "johndoe@example.com",
  "first_name": "John",
  "last_name": "Doe"
}``
And an example JSON response body could be:
``{
  "success": true,
  "message": "Registration successful."
}``

-   Endpoint 3: 
Endpoint that accepts a GET request and retrieves data from the database using a provided query parameter. The endpoint should return the query result in the response.
the JSON request body for Endpoint 3 could be:
``{
 "query": "first_name=John"
}``
An example JSON response body could be:
``[
  {
    "username": "johndoe",
    "email": "johndoe@example.com",
    "first_name": "John",
    "last_name": "Doe"
  }
]``

- Endpoint 4: 
Endpoint that accepts a POST request with a JSON payload that represents a tree data structure. The endpoint should return the maximum depth of the tree in the response. To solve this challenge, you will need to implement a recursive function that traverses the tree and calculates its maximum depth.
an example JSON could be:
```{
  "root": {
    "value": "A",
    "left": {
      "value": "B",
      "left": {
        "value": "D"
      },
      "right": {
        "value": "E"
      }
    },
    "right": {
      "value": "C",
      "right": {
        "value": "F"
      }
    }
  }
}
````
This represents a tree with a root node "A" that has two child nodes, "B" and "C". "B" has two child nodes, "D" and "E", and "C" has one child node, "F". The maximum depth of this tree is 3, since the longest path from the root to a leaf node is through the nodes "A" -> "B" -> "E".
an example JSON response body for this request could be:
``{
   "max_depth": 3
}``


## Development

- Server will be a standalone application 
- Database will be implementented on MongoDB with Kmongo as copilot and hosted in a docker container (see guide below)
- if enought time some git-actions will be integrated!

## Docker GUIDE for backend deploy
TODO 
(docker is hosted on a rasperry-py 3b so not all containers image will work fine... )

## License
MIT
**Free Software, Have Fun!**
#### Questions/problems? contact me or open a issue!
