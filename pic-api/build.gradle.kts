tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":pic-common"))
    implementation(project(":pic-domain"))
    implementation(project(":pic-external"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}