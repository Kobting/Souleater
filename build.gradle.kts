plugins {
    kotlin("jvm") version "1.7.20"
    id("sts-dependencies.dependencies")
}

group = "com.github.kobting"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(SlayTheSpire)
    implementation(ModTheSpire)
    implementation(BaseMod)
    implementation(FriendlyMinions)
    implementation(STSLib)
}

kotlin {
    jvmToolchain(8)
}

tasks.findByName("build")!!.doLast {
    val libs = "${projectDir.path}/build/libs"
    copy {
        from(libs) {
            include("*.jar")
        }
        into(SlayTheSpireModsFolderPath)
    }
}