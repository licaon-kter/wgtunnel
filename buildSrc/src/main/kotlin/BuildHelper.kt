import org.gradle.api.invocation.Gradle

object BuildHelper {
    private fun getCurrentFlavor(gradle : Gradle): String {
        val taskRequestsStr = gradle.startParameter.taskRequests.toString()
        val pattern: java.util.regex.Pattern = if (taskRequestsStr.contains("assemble")) {
            java.util.regex.Pattern.compile("assemble(\\w+)(Release|Debug)")
        } else {
            java.util.regex.Pattern.compile("bundle(\\w+)(Release|Debug)")
        }

        val matcher = pattern.matcher(taskRequestsStr)
        val flavor = if (matcher.find()) {
            matcher.group(1).lowercase()
        } else {
            print("NO FLAVOR FOUND")
            ""
        }
        return flavor
    }

    fun isGeneralFlavor(gradle : Gradle) : Boolean {
        return getCurrentFlavor(gradle) == "general"
    }
    fun isReleaseBuild(gradle: Gradle) : Boolean {
        return (gradle.startParameter.taskNames.size > 0 && gradle.startParameter.taskNames[0].contains(
                "Release"))
    }
}