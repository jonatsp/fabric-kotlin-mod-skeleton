plugins{
    id("fabric-loom")
    kotlin("jvm")
}


val modVersion: String by project
val mavenGroup: String by project

base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}

group = mavenGroup
version = modVersion


repositories{

}

dependencies {
    val minecraftVersion: String by project
    val yarnMappings: String by project
    val loaderVersion: String by project
    val fabricVersion: String by project
    val fabricKotlinVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
    include("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
}

tasks {
    val javaVersion = JavaVersion.VERSION_16
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    jar {
        from("LICENSE") {
            rename {
                "$it.${base.archivesName}"
            }
        }
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions{
            jvmTarget = javaVersion.toString()
        }
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }
}


java {
    withSourcesJar()
}