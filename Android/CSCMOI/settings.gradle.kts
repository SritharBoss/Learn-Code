pluginManagement {
    repositories {
        gradlePluginPortal() // ✅ Required for most Gradle plugins
        google()             // ✅ Required for KSP
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url="https://jitpack.io")

    }
}

rootProject.name = "CSC MOI"
include(":app")
 