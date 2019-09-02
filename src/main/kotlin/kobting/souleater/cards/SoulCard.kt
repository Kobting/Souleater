package kobting.souleater.cards

import basemod.abstracts.CustomCard
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import kobting.friendlyminions.helpers.BasePlayerMinionHelper
import kobting.souleater.actions.PlaceSoulAction
import kobting.souleater.helpers.addToBottomAndReturn
import kobting.souleater.souls.Soul


open class SoulCard(var soul: Soul) : CustomCard(
        "souleater:${soul.monsterId}",
        "Summon ${soul.monsterName}",
        "kobting/images/souleater/cards/beta_purple.png",
        0,
        "Summon the captured soul of a ${soul.monsterName}. NL Exhaust.",
        CardType.SKILL,
        CardColor.COLORLESS,
        CardRarity.SPECIAL,
        CardTarget.NONE
), PlaceSoulAction.PlaceSoulComplete {

    private var didUse: Boolean = false
    var placeSoulAction: PlaceSoulAction? = null

    init {
        this.exhaust = true
    }

    override fun use(player: AbstractPlayer, monster: AbstractMonster?) {
        placeSoulAction = AbstractDungeon.actionManager.addToBottomAndReturn(PlaceSoulAction(player, soul, this))
        didUse = true
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        val canUse = BasePlayerMinionHelper.getMaxMinions(p) >= BasePlayerMinionHelper.getMinions(p).monsters.size
        if (!canUse) {
            this.cantUseMessage = "You can only summon ${BasePlayerMinionHelper.getMaxMinions(p)} minions."
            return !canUse && super.canUse(p, m)
        }
        return super.canUse(p, m)
    }

    override fun makeCopy(): AbstractCard {
        return SoulCard(soul)
    }

    override fun render(sb: SpriteBatch?) {
        super.render(sb)
        if (didUse && placeSoulAction?.isDone == false) {
            placeSoulAction?.render(sb ?: return)
        }
    }

    override fun upgrade() {}

    override fun canUpgrade(): Boolean {
        return false
    }

    override fun onPlaceSoulComplete(completed: Boolean) {
        if(completed) {
            didUse = false
        }
    }

    data class SoulCardState(val soul: Soul, val deckPosition: Int)
}