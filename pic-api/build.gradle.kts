import com.google.cloud.tools.jib.gradle.JibExtension

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}

apply(plugin = "com.google.cloud.tools.jib")

val jjwtVersion: String by project.extra
val springDocOpenApiVersion: String by project.extra

dependencies {
    implementation(project(":pic-common"))
    implementation(project(":pic-domain"))
    implementation(project(":pic-external"))

    // thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion")
}

configure<JibExtension> {
    val registryUsername = System.getenv("DOCKERHUB_USERNAME")
    val jasyptPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD")
    val (activeProfile, containerImageName) = getProfileAndImageName(registryUsername)

    from {
        image = "eclipse-temurin:21-jre"
    }

    to {
        image = containerImageName
        tags = setOf("$version", "latest")
        auth {
            username = registryUsername
            password = System.getenv("DOCKERHUB_PASSWORD")
        }
    }

    container {
        // TODO: 서버 스펙에 따라 Xmx/Xms, Initial/Min/MaxRAMFraction 설정
        jvmFlags =
            listOf(
                "-server",
                "-XX:+UseContainerSupport",
                "-XX:+UseStringDeduplication",
                "-Dserver.port=8080",
                "-Dfile.encoding=UTF-8",
                "-Djava.awt.headless=true",
                "-Dspring.profiles.active=$activeProfile",
                "-Djasypt.encryptor.password=$jasyptPassword"
            )
        ports = listOf("8080")
        environment =
            mapOf(
                "TZ" to "Asia/Seoul"
            )
    }
}

fun getProfileAndImageName(registryUsername: String?): Pair<String, String> {
    val containerImageName = "$registryUsername/${project.name}"
    return "develop" to "$containerImageName-develop"
}
