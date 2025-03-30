package code.yousef.presentation.resource

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class StyleResourceTest {

    @Test
    fun testGetStylesCss() {
        given()
            .`when`()
            .get("/css/styles.css")
            .then()
            .statusCode(200)
            .contentType(containsString("text/css"))
    }

    @Test
    fun testGetDynamicCss() {
        given()
            .`when`()
            .get("/css/dynamic.css")
            .then()
            .statusCode(200)
            .contentType(containsString("text/css"))
    }
}
