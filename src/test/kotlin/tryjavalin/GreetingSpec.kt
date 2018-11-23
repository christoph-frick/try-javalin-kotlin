package tryjavalin

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.mapper
import com.github.kittinunf.fuel.jackson.responseObject
import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class GreetingSpec : StringSpec() {

    val server = create().port(0).start()
    val url = "http://localhost:${server.port()}"

    override fun afterSpec(description: Description, spec: Spec) {
        super.afterSpec(description, spec)
        server.stop()
    }

    init {
        "default is world" {
            url.httpGet().responseObject<Greeting>().third.get().greeting shouldBe "Hello, World!"
        }
        "name is in result" {
            url.httpGet(listOf("name" to "Belphegor")).responseObject<Greeting>().third.get().greeting shouldBe "Hello, Belphegor!"
        }
        "post works with Jackson mapper" {
            url.httpPost().jsonBody(mapper.writeValueAsString(GreetingRequest("Baphomet"))).responseObject<GreetingResponse>().third.get().greeting shouldBe "Hello, Baphomet!"
        }
    }
}