val jacksonAnnotationVersion: String by project.extra
val awsVersion: String by project.extra

dependencies {
    implementation(project(":pic-common"))

    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationVersion")

    // aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:$awsVersion")
}
