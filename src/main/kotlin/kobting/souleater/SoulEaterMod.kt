package kobting.souleater

import basemod.BaseMod
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditKeywordsSubscriber
import basemod.interfaces.EditStringsSubscriber
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.CardStrings
import kobting.friendlyminions.monsters.AbstractFriendlyMonster
import kobting.souleater.cards.SoulStealer
import kobting.souleater.helpers.CardHelper
import kobting.souleater.souls.CustomSoulMove
import kobting.souleater.souls.SoulMapper
import kobting.souleater.souls.SoulMoveHelper
import java.nio.charset.StandardCharsets

@SpireInitializer
class SoulEaterMod private constructor(): EditKeywordsSubscriber, EditCardsSubscriber, EditStringsSubscriber {

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

    }

    override fun receiveEditCards() {
        BaseMod.addCard(SoulStealer())
    }

    override fun receiveEditStrings() {

        SoulMoveHelper.addCustomMove("bAndD", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(): String = "Gain 5 Block. NL Deal 6 Damage."

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(GainBlockAction(minion, minion, 5))
                    val target = AbstractDungeon.getRandomMonster()
                    val info = DamageInfo(minion, 6, DamageInfo.DamageType.NORMAL)
                    info.applyPowers(minion, target)
                    AbstractDungeon.actionManager.addToBottom(DamageAction(target, info))
                }
            }
        })

        SoulMapper.addSouls("kobting/localization/eng/souleater-souls.json")

        BaseMod.loadCustomStrings(CardStrings::class.java, Gdx.files.internal("kobting/localization/eng/souleater-cards.json").readString(StandardCharsets.UTF_8.toString()))
    }
}