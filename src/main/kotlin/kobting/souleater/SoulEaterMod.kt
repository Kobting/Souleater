package kobting.souleater

import basemod.BaseMod
import basemod.interfaces.EditKeywordsSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import kobting.souleater.helpers.CardHelper
import kobting.souleater.souls.SoulMapper

@SpireInitializer
class SoulEaterMod private constructor(): EditKeywordsSubscriber {

    companion object {

        @JvmStatic
        lateinit var instance: SoulEaterMod
            private set

        @JvmStatic
        fun initialize() {
            instance = SoulEaterMod()
        }

    }

    init {
        BaseMod.subscribe(this)
        CardHelper.getImagePath("")
    }

    override fun receiveEditKeywords() {
        SoulMapper.addSouls("kobting/localization/eng/souleater-souls.json")
    }
}