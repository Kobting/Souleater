package kobting.souleater.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import kobting.friendlyminions.helpers.BasePlayerMinionHelper
import kobting.souleater.cards.SoulCard
import kobting.souleater.helpers.canConsume
import kobting.souleater.helpers.willDie
import kobting.souleater.souls.SoulMapper
import kobting.souleater.souls.SoulMonster

class ConsumeSoulAction(monster: AbstractMonster?, private val damageInfo: DamageInfo) : AbstractGameAction() {

    init {
        setValues(monster, damageInfo)
        actionType = ActionType.DAMAGE
        duration = 0.1F
    }

    override fun update() {
        val monster = target as? AbstractMonster?
        if (duration == 0.1F && monster != null) {
            AbstractDungeon.effectList.add(FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.NONE))
            monster.damage(damageInfo)
            if (monster.willDie()) {
                val soul = SoulMapper.getSoulFromId(monster.id)
                soul?.let {
                    println("------- Found Soul for ${soul.monsterName} -------")
                    it.height = monster.hb_h
                    it.width = monster.hb_w
                    it.monsterType = monster.type
                    SoulMapper.currentSouls.add(it)
                    AbstractDungeon.actionManager.addToBottom(AddCardToDeckAction(SoulCard(it)))
                }
            }
        }

        this.tickDuration()
    }

}