package kobting.souleater.cards

import basemod.helpers.TooltipInfo
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import kobting.souleater.actions.ConsumeSoulAction
import kobting.souleater.souls.SoulMapper

class SoulStealer : KustomCard(
    ID,
    COST,
    CardType.ATTACK,
    CardColor.COLORLESS,
    AbstractCard.CardRarity.COMMON,
    CardTarget.ENEMY
) {

    companion object {

        @JvmStatic
        val ID = "souleater:SoulStealer"

        private const val COST = 0
        private const val DMG_AMT = 4

    }

    init {
        this.baseDamage = DMG_AMT
        this.damage = baseDamage
    }

    override fun use(player: AbstractPlayer?, monster: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            ConsumeSoulAction(
                monster,
                DamageInfo(monster, DMG_AMT, DamageInfo.DamageType.NORMAL)
            )
        )
    }

    override fun canUpgrade(): Boolean {
        //TODO: Allow upgrade for elites/bosses/special
        return false
    }

    override fun canUse(p: AbstractPlayer?, monster: AbstractMonster?): Boolean {
        if (monster == null || p == null) return super.canUse(p, monster)
        cantUseMessage = "${monster.name} is too strong to have its soul taken."
        return SoulMapper.getSoulFromId(monster.id) != null
    }

    override fun getCustomTooltips(): MutableList<TooltipInfo> {
        val currentlyCapturableMonsters = AbstractDungeon.getMonsters()?.monsters ?: return super.getCustomTooltips()

        val description = currentlyCapturableMonsters.filter(::soulFilter).joinToString(" NL ") { it.name }
        return mutableListOf(
            TooltipInfo(
                "Current Monster Souls Available",
                description.ifEmpty { "No Available Souls" }
            )
        )
    }

    private fun soulFilter(monster: AbstractMonster): Boolean {
        val soul = SoulMapper.getSoulFromId(monster.id) ?: return false
        return !soul.requiresUpgrade
    }

    override fun upgrade() {

    }
}