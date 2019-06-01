package kobting.souleater.helpers

import com.megacrit.cardcrawl.monsters.AbstractMonster

fun AbstractMonster.willDie() : Boolean {
    return (isDying || currentHealth <= 0) && !halfDead
}

fun AbstractMonster.canConsume() = (
    type != AbstractMonster.EnemyType.ELITE &&
    type != AbstractMonster.EnemyType.BOSS
)