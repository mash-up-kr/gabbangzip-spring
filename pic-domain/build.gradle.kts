val mysqlConnectorVersion: String by project.extra

plugins {
    id("kotlin-jpa")
}

dependencies {
    implementation(project(":pic-common"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j:$mysqlConnectorVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
