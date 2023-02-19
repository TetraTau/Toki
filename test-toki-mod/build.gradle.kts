plugins {
    `java-library`
}

dependencies {
    compileOnly(project(":fabric-loader:minecraft"))
    compileOnly(project(":paper-server"))
}
