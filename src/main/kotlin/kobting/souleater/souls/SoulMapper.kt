package kobting.souleater.souls

import basemod.BaseMod
import basemod.interfaces.PostDungeonInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStreamReader

object SoulMapper: PostDungeonInitializeSubscriber {

    init {
        BaseMod.subscribe(this)
    }

    private val monsterSet: MutableSet<Soul> = mutableSetOf()
    val currentSouls = mutableListOf<Soul>()

    fun addSouls(soulJsonPath: String) {
        val gson = GsonBuilder()
                .registerTypeAdapter(SoulInfo.type, SoulInfoDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create()
        val jsonString = Gdx.files.internal(soulJsonPath).readString()
        println("------- Souls Json String -------")
        println(jsonString)
        val soulInfo = gson.fromJson<SoulInfo>(jsonString, SoulInfo.type)
        println("------- Souls Found: ${soulInfo?.souls?.size ?: 0}-------")
        soulInfo?.souls?.forEach {
            println(it.toString())
            monsterSet.add(it)
        }
        println("------- Added Souls --------")
        monsterSet.map(::println)
    }

    fun getSoulFromId(id: String) = monsterSet.find { it.monsterId == id }

    override fun receivePostDungeonInitialize() {
        currentSouls.clear()
    }
}