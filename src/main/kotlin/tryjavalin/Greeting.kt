package tryjavalin

import io.javalin.Javalin

class Greeting(
        name: String = "World"
) {
    val greeting: String = "Hello, ${name}!"
}

data class GreetingRequest(
        val name: String
)

data class GreetingResponse(
        val greeting: String
)

fun create(): Javalin {
    return Javalin.create()
            .disableStartupBanner()
            .get("/") { ctx ->
                val name = ctx.queryParam("name", "World") // FIXME: how to deal with `String?`
                val greeting = Greeting(name ?: "World")
                ctx.json(greeting)
            }
            .post("/") { ctx ->
                val req = ctx.body<GreetingRequest>()
                ctx.json(GreetingResponse("Hello, ${req.name}!"))
            }
}
