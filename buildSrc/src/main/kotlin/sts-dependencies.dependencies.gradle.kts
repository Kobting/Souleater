val overridePath = properties["steamPath"] as? String?

//Modify to match your system
val steamPath = overridePath.takeIf { !it.isNullOrEmpty() } ?: when(Platform.currentPlatform()) {
    Platform.WINDOWS -> "D:/Steam/steamapps"
    Platform.LINUX -> "${System.getProperty("user.home")}/.steam/debian-installation/steamapps"
}

println("SteamPath: $steamPath")

val workshopLocation = "$steamPath/workshop/content/646570"
val SlayTheSpire = files("$steamPath/common/SlayTheSpire/desktop-1.0.jar")
val ModTheSpire = files("$workshopLocation/1605060445/ModTheSpire.jar")
val BaseMod = files("$workshopLocation/1605833019/BaseMod.jar")
val STSLib = files("$workshopLocation/1609158507/StSLib.jar")
//1612426481
//val FriendlyMinions = files("$workshopLocation/1612426481/FriendlyMinions.jar")//files("$steamPath/common/SlayTheSpire/mods/FriendlyMinions.jar")
//1612426481 files("$workshopLocation/1612426481/FriendlyMinions.jar")
val FriendlyMinions = files("$steamPath/common/SlayTheSpire/mods/FriendlyMinions.jar")

extensions.add("SlayTheSpire", SlayTheSpire)
extensions.add("ModTheSpire", ModTheSpire)
extensions.add("BaseMod", BaseMod)
extensions.add("FriendlyMinions", FriendlyMinions)
extensions.add("STSLib", STSLib)
extensions.add("SlayTheSpireModsFolderPath", "$steamPath/common/SlayTheSpire/mods")

enum class Platform {
    WINDOWS,
    LINUX;

    companion object {

        fun currentPlatform(): Platform {
            val os = System.getProperty("os.name").toLowerCase()
            return if (os.contains("win", true)) {
                WINDOWS
            } else if (os.contains("nux", true)) {
                LINUX
            } else {
                error("No platform configuration for: $os")
            }
        }

    }
}
