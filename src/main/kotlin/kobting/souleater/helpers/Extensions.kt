package kobting.souleater.helpers

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

fun AbstractMonster.willDie() : Boolean {
    return (isDying || currentHealth <= 0) && !halfDead
}

fun AbstractMonster.canConsume() = (
    type != AbstractMonster.EnemyType.ELITE &&
    type != AbstractMonster.EnemyType.BOSS
)

fun <T : AbstractGameAction> GameActionManager.addToBottomAndReturn(abstractGameAction: T): T {
    this.addToBottom(abstractGameAction)
    return abstractGameAction
}