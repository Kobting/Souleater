package kobting.souleater.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import kobting.souleater.actions.ConsumeSoulAction

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
        AbstractDungeon.actionManager.addToBottom(ConsumeSoulAction(
                monster,
                DamageInfo(monster, DMG_AMT, DamageInfo.DamageType.NORMAL)
        ))
    }

    override fun upgrade() {

    }
}