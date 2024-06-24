val jacksonAnnotationVersion: String by project.extra

dependencies {
    implementation(project(":pic-common"))

    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationVersion")
}
