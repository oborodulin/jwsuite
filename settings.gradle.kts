pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // for com.github.tfaki:ComposableSweetToast
        maven("https://jitpack.io")
    }
}
rootProject.name = "JWSuite"
include("app")
include("common")
include("data")
include("domain")
include("presentation")
include(":data_geo")
