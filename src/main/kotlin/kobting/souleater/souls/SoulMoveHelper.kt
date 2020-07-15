package kobting.souleater.souls

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.VulnerablePower
import com.megacrit.cardcrawl.powers.WeakPower
import kobting.friendlyminions.monsters.AbstractFriendlyMonster
import kobting.friendlyminions.monsters.MinionMove

object SoulMoveHelper {

    private val customMoves = mutableMapOf<String, CustomSoulMove>()

    @JvmStatic
    fun addCustomMoves(moves: Map<String, CustomSoulMove>) {
        customMoves.putAll(moves)
    }

    @JvmStatic
    fun addCustomMove(id: String, move: CustomSoulMove) {
        customMoves.put(id, move)
    }

    @JvmStatic
    fun createMinionMoves(minion: AbstractFriendlyMonster, soulMoves: List<SoulMove>?): ArrayList<MinionMove> {
        //Default moves in the case that the minion has none defined
        if(soulMoves == null) {
            return arrayListOf(
                    createMinionMoveFromSoulMove(minion, SoulMove(SoulMove.Type.ATTACK, 3)),
                    createMinionMoveFromSoulMove(minion, SoulMove(SoulMove.Type.DEFEND, 5))
            )
        }

        val minionMoves = arrayListOf<MinionMove>()
        soulMoves.forEach {
            minionMoves.add(createMinionMoveFromSoulMove(minion, it))
        }
        return minionMoves
    }

    @JvmStatic
    fun createMinionMoveFromSoulMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return when (soulMove.type) {
            SoulMove.Type.ATTACK -> createDamageMove(minion, soulMove)
            SoulMove.Type.DEFEND -> createDefendMove(minion, soulMove)
            SoulMove.Type.HEAL -> createHealMove(minion, soulMove)
            SoulMove.Type.WEAKEN -> createWeakenMove(minion, soulMove)
            SoulMove.Type.VULNERABLE -> createVulnerableMove(minion, soulMove)
            SoulMove.Type.CUSTOM -> createCustomMove(minion, soulMove)
        }
    }

    @JvmStatic
    private fun createDamageMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return MinionMove(
                "ATTACK",
                minion,
                Texture("kobting/images/souleater/ui/atk_bubble.png"),
                "Deal ${soulMove.amount} Damage.")
        {
            val target = AbstractDungeon.getRandomMonster()
            val info = DamageInfo(minion, soulMove.amount, DamageInfo.DamageType.NORMAL)
            info.applyPowers(minion, target)
            AbstractDungeon.actionManager.addToBottom(DamageAction(target, info))
        }
    }

    @JvmStatic
    private fun createDefendMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return MinionMove(
                "DEFEND",
                minion,
                Texture("kobting/images/souleater/ui/atk_bubble.png"),
                "Gain ${soulMove.amount} Block.")
        {
            AbstractDungeon.actionManager.addToBottom(GainBlockAction(minion, minion, soulMove.amount))
        }
    }

    @JvmStatic
    private fun createHealMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return MinionMove(
                "HEAL",
                minion,
                Texture("kobting/images/souleater/ui/atk_bubble.png"),
                "Heal ${soulMove.amount} HP.")
        {
            AbstractDungeon.actionManager.addToBottom(HealAction(minion, minion, soulMove.amount))
        }
    }

    @JvmStatic
    private fun createWeakenMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return MinionMove(
                "WEAKEN",
                minion,
                Texture("kobting/images/souleater/ui/atk_bubble.png"),
                "Apply ${soulMove.amount} Weaken.")
        {
            val target = AbstractDungeon.getRandomMonster()
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(target, minion, WeakPower(target, soulMove.amount, false), soulMove.amount, true))
        }
    }

    @JvmStatic
    private fun createVulnerableMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        return MinionMove(
                "VULNERABLE",
                minion,
                Texture("kobting/images/souleater/ui/atk_bubble.png"),
                "Apply ${soulMove.amount} Vulnerable.")
        {
            val target = AbstractDungeon.getRandomMonster()
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(target, minion, VulnerablePower(target, soulMove.amount, false), soulMove.amount, true))
        }
    }

    @JvmStatic
    private fun createCustomMove(minion: AbstractFriendlyMonster, soulMove: SoulMove): MinionMove {
        //NOTE: This might cause null pointers if customMoves doesn't have a move match the id
        val customMove = customMoves[soulMove.id]
        return MinionMove(
                soulMove.id,
                minion,
                customMove?.onRequestMoveImage() ?: Texture("kobting/images/souleater/ui/atk_bubble.png"),
                customMove?.onRequestMoveDescription(soulMove) ?: "No Move Description!!!",
                customMove?.onRequestMoveActions(minion, soulMove))
    }

}