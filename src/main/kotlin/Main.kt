import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info

fun main() {
    Javalin.create { config ->
        val applicationInfo = Info().version("1.0").title("Example Api").description("Example Api")
        val swaggerOptions = SwaggerOptions("/").title("Example Api")
        val options = OpenApiOptions(applicationInfo).path("/docs").swagger(swaggerOptions)
        config.registerPlugin(OpenApiPlugin(options))

        config.defaultContentType = "application/json"
        config.showJavalinBanner = false
        config.enableCorsForAllOrigins()
        config.enableDevLogging()
    }.routes {
        ApiBuilder.path("api/v1/users") {
            ApiBuilder.post(UserController::register)
        }
    }.start("0.0.0.0", 9000)
}
