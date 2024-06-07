tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}

val jjwtVersion = "0.11.5"

dependencies {
    implementation(project(":pic-common"))
    implementation(project(":pic-domain"))
    implementation(project(":pic-external"))

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
