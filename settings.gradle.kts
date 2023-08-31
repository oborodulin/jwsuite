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
include(":data_congregation")
include(":presentation_geo")
include(":presentation_congregation")
include(":presentation_dashboard")
include(":presentation_territory")
include(":data_territory")
include(":data_appsetting")
