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
import com.megacrit.cardcrawl.monsters.city.Byrd
import kobting.friendlyminions.monsters.AbstractFriendlyMonster
import kobting.souleater.cards.SoulStealer
import kobting.souleater.helpers.CardHelper
import kobting.souleater.souls.CustomSoulMove
import kobting.souleater.souls.SoulMapper
import kobting.souleater.souls.SoulMove
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

            override fun onRequestMoveDescription(soulMove: SoulMove): String = "Gain 5 Block. NL Deal 6 Damage."

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(GainBlockAction(minion, minion, 5))
                    val target = AbstractDungeon.getRandomMonster()
                    val info = DamageInfo(minion, 6, DamageInfo.DamageType.NORMAL)
                    info.applyPowers(minion, target)
                    AbstractDungeon.actionManager.addToBottom(DamageAction(target, info))
                }
            }
        })

        addCustomSoulMoves()

        SoulMapper.addSouls("kobting/localization/eng/souleater-souls.json")

        BaseMod.loadCustomStrings(CardStrings::class.java, Gdx.files.internal("kobting/localization/eng/souleater-cards.json").readString(StandardCharsets.UTF_8.toString()))
    }

    private fun addCustomSoulMoves() {
        SoulMoveHelper.addCustomMove("multi_attack", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null
            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Deal ${soulMove.amount} damage 3 times."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    val target = AbstractDungeon.getRandomMonster()
                    for (i in 0 until 3) {
                        AbstractDungeon.actionManager.addToBottom(DamageAction(target, DamageInfo(minion, soulMove.amount, DamageInfo.DamageType.NORMAL).apply {
                            applyPowers(minion, target)
                        }))
                    }
                }
            }


        })
    }
}