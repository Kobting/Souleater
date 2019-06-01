package kobting.souleater.cards

import basemod.abstracts.CustomCard
import basemod.abstracts.CustomSavable
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import kobting.friendlyminions.helpers.BasePlayerMinionHelper
import kobting.souleater.souls.Soul
import kobting.souleater.souls.SoulMonster


class SoulCard(var soul: Soul) : CustomCard (
        "souleater:${soul.monsterId}",
        "Summon ${soul.monsterName}",
        "kobting/images/souleater/cards/beta_purple.png",
        0,
        "Summon the captured soul of a ${soul.monsterName}",
        CardType.SKILL,
        CardColor.COLORLESS,
        CardRarity.SPECIAL,
        CardTarget.NONE
), CustomSavable<SoulCard.SoulCardState> {

    override fun onLoad(state: SoulCardState) {
        soul = state.soul
    }

    override fun onSave(): SoulCardState {
        return SoulCardState(
                soul,
                AbstractDungeon.player.masterDeck.group.indexOf(this)
        )
    }

    override fun use(player: AbstractPlayer?, monster: AbstractMonster?) {
        BasePlayerMinionHelper.addMinion(AbstractDungeon.player, SoulMonster(soul))
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean{
        val canUse = BasePlayerMinionHelper.getMaxMinions(p) >= BasePlayerMinionHelper.getMinions(p).monsters.size
        if(!canUse) {
            this.cantUseMessage = "You can only summon ${BasePlayerMinionHelper.getMaxMinions(p)} minions."
            return !canUse
        }
        return super.canUse(p, m)
    }

    override fun makeCopy(): AbstractCard {
        return SoulCard(soul)
    }

    override fun upgrade() {}
    override fun canUpgrade(): Boolean {
        return false
    }

    data class SoulCardState(val soul: Soul, val deckPosition: Int)
}