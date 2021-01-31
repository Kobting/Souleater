package kobting.souleater.souls

import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DiscardAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.ExhaustAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.GainGoldAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.actions.common.SuicideAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AngryPower
import com.megacrit.cardcrawl.powers.ConstrictedPower
import com.megacrit.cardcrawl.powers.CurlUpPower
import com.megacrit.cardcrawl.powers.FlightPower
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower
import com.megacrit.cardcrawl.powers.RitualPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.ThornsPower
import com.megacrit.cardcrawl.vfx.combat.BiteEffect
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect
import kobting.friendlyminions.monsters.AbstractFriendlyMonster
object CustomSoulMoves {

    fun initializeCustomSoulMoves() {
        byrdFlight()
        randomDefendCenturion()
        chosenGainStrength()
        cultistRitual()
        exploderExplode()
        fungiGainStrength()
        louseCurlUp()
        gremlinWarriorGainAngry()
        mysticHeal()
        jawWormBlockAttack()
        jawWormGainStrength()
        looterGainGold()
        muggerGainGold()
        ordWalkerGainOrbStrength()
        repulsorExhaust()
        serpentConstriction()
        shelledParasiteBiteAndHeal()
        snakePlantMultiAttack()
        sneckoRandomizeCardCost()
        spikedSlimeLDiscardAndDraw()
        spikedSlimeMDiscardAndDraw()
        spikedSlimeSDiscardAndDraw()
        spikerGainThorns()
        torchHeadGainStrength()
    }

    private fun byrdFlight() {
        SoulMoveHelper.addCustomMove("byrd_flight", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} flight"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(minion, minion, FlightPower(minion, soulMove.amount))
                    )
                }
            }

        })
    }

    private fun randomDefendCenturion() {
        SoulMoveHelper.addCustomMove("random_defend_centurion", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain or Give ${soulMove.amount} block to the player"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    val targetPlayer = AbstractDungeon.miscRng.randomBoolean()
                    val target = if (targetPlayer) AbstractDungeon.player else minion
                    AbstractDungeon.actionManager.addToBottom(GainBlockAction(target, soulMove.amount))
                }
            }
        })
    }

    private fun chosenGainStrength() {
        SoulMoveHelper.addCustomMove("chosen_gain_strength", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Strength"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            StrengthPower(minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun cultistRitual() {
        SoulMoveHelper.addCustomMove("cultist_ritual", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Ritual"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            RitualPower(minion, soulMove.amount, true)
                        )
                    )
                }
            }
        })
    }

    private fun exploderExplode() {
        SoulMoveHelper.addCustomMove("exploder_explode", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Explode and deal ${soulMove.amount} to all enemies."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        VFXAction(
                            ExplosionSmallEffect(
                                minion.hb.cX,
                                minion.hb.cY
                            ), 0.1f
                        )
                    )
                    AbstractDungeon.actionManager.addToBottom(SuicideAction(minion))
                    val damageInfo = DamageInfo(minion, soulMove.amount, DamageType.THORNS)

                    AbstractDungeon.getMonsters()?.monsters?.forEach { monster ->
                        AbstractDungeon.actionManager.addToBottom(
                            VFXAction(
                                ExplosionSmallEffect(
                                    monster.hb.cX,
                                    monster.hb.cY
                                ), 0.1f
                            )
                        )
                        AbstractDungeon.actionManager.addToBottom(
                            DamageAction(
                                monster,
                                damageInfo,
                                AttackEffect.FIRE,
                                true
                            )
                        )
                        AbstractDungeon.actionManager.addToBottom(WaitAction(0.2f))
                    }

                }
            }
        })
    }

    private fun fungiGainStrength() {
        SoulMoveHelper.addCustomMove("fungi_gain_strength", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Strength."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            StrengthPower(minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun louseCurlUp() {
        SoulMoveHelper.addCustomMove("louse_curl_up", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Curl up for ${soulMove.amount}"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            CurlUpPower(minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun gremlinWarriorGainAngry() {
        SoulMoveHelper.addCustomMove("gremlin_warrior_gain_angry", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Angry"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            AngryPower(minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun mysticHeal() {
        SoulMoveHelper.addCustomMove("mystic_heal", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Heal yourself and the player for ${soulMove.amount}"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(HealAction(minion, minion, soulMove.amount))
                    AbstractDungeon.actionManager.addToBottom(WaitAction(.1f))
                    AbstractDungeon.actionManager.addToBottom(
                        HealAction(
                            AbstractDungeon.player,
                            minion,
                            soulMove.amount
                        )
                    )
                }
            }
        })
    }

    private fun jawWormBlockAttack() {
        SoulMoveHelper.addCustomMove("jaw_worm_block_attack", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Deal ${soulMove.amount} Damage. NL Gain ${soulMove.amount} Block."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    val damageInfo = DamageInfo(minion, soulMove.amount)
                    AbstractDungeon.actionManager.addToBottom(
                        DamageAction(
                            AbstractDungeon.getMonsters().randomMonster,
                            damageInfo
                        )
                    )
                    AbstractDungeon.actionManager.addToBottom(GainBlockAction(minion, soulMove.amount))
                }
            }
        })
    }

    private fun jawWormGainStrength() {
        SoulMoveHelper.addCustomMove("jaw_worm_gain_strength", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Strength."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            StrengthPower(minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun looterGainGold() {
        SoulMoveHelper.addCustomMove("looter_gain_gold", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Gold."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(GainGoldAction(soulMove.amount))
                }
            }
        })
    }

    private fun muggerGainGold() {
        SoulMoveHelper.addCustomMove("mugger_gain_gold", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} Gold."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(GainGoldAction(soulMove.amount))
                }
            }
        })
    }

    private fun ordWalkerGainOrbStrength() {
        SoulMoveHelper.addCustomMove("orb_walker_gain_orb_strength", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain orb strength of ${soulMove.amount}"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            minion,
                            minion,
                            GenericStrengthUpPower(minion, "Strength Up", soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun repulsorExhaust() {
        SoulMoveHelper.addCustomMove("repulsor_exhaust", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Exhaust ${soulMove.amount} Card."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(ExhaustAction(soulMove.amount, false))
                }
            }
        })
    }

    private fun serpentConstriction() {
        SoulMoveHelper.addCustomMove("serpent_constriction", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Give a random monster ${soulMove.amount} constriction"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    val target = AbstractDungeon.getMonsters().randomMonster
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            target,
                            minion,
                            ConstrictedPower(target, minion, soulMove.amount)
                        )
                    )
                }
            }
        })
    }

    private fun shelledParasiteBiteAndHeal() {
        SoulMoveHelper.addCustomMove("shelled_parasite_bite_and_heal", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Deal ${soulMove.amount} Damage. NL Heal ${soulMove.amount / 2}."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    val target = AbstractDungeon.getMonsters().randomMonster
                    AbstractDungeon.actionManager.addToBottom(VFXAction(BiteEffect(target.hb.cX, target.hb.cY)))
                    val damageInfo = DamageInfo(minion, soulMove.amount)
                    AbstractDungeon.actionManager.addToBottom(DamageAction(target, damageInfo))
                    AbstractDungeon.actionManager.addToBottom(HealAction(minion, minion, soulMove.amount / 2))
                }
            }
        })
    }

    private fun snakePlantMultiAttack() {
        SoulMoveHelper.addCustomMove("snake_plant_multi_attack", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Deal ${soulMove.amount} Damage ${soulMove.amount/2} times."
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    for (i in 0 until soulMove.amount / 2) {
                        val damageInfo = DamageInfo(minion, soulMove.amount)
                        val target = AbstractDungeon.getMonsters().randomMonster
                        AbstractDungeon.actionManager.addToBottom(DamageAction(target, damageInfo))
                    }
                }
            }
        })
    }

    private fun sneckoRandomizeCardCost() {
        SoulMoveHelper.addCustomMove("snecko_randomize_card_cost", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Randomize the cost of ${soulMove.amount} cards for this turn"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(
                        SelectCardsInHandAction(
                            soulMove.amount,
                            "Select cards to randomize cost"
                        ) { selectedCards ->
                            selectedCards.forEach { card ->
                                var cost = AbstractDungeon.cardRandomRng.random(3)
                                while (cost == card.cost) {
                                    cost = AbstractDungeon.cardRandomRng.random(3)
                                }
                                card.setCostForTurn(cost)
                            }
                        }
                    )
                }
            }
        })
    }

    private fun spikedSlimeLDiscardAndDraw() {
        SoulMoveHelper.addCustomMove("spiked_slime_l_discard_and_draw", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Discard ${soulMove.amount} cards NL Draw ${soulMove.amount} cards"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(DiscardAction(AbstractDungeon.player, minion, soulMove.amount, false))
                    AbstractDungeon.actionManager.addToBottom(DrawCardAction(minion, soulMove.amount))
                }
            }
        })
    }

    private fun spikedSlimeMDiscardAndDraw() {
        SoulMoveHelper.addCustomMove("spiked_slime_m_discard_and_draw", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Discard ${soulMove.amount} cards NL Draw ${soulMove.amount} cards"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(DiscardAction(AbstractDungeon.player, minion, soulMove.amount, false))
                    AbstractDungeon.actionManager.addToBottom(DrawCardAction(minion, soulMove.amount))
                }
            }
        })
    }

    private fun spikedSlimeSDiscardAndDraw() {
        SoulMoveHelper.addCustomMove("spiked_slime_s_discard_and_draw", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Discard ${soulMove.amount} card NL Draw ${soulMove.amount} card"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(DiscardAction(AbstractDungeon.player, minion, soulMove.amount, false))
                    AbstractDungeon.actionManager.addToBottom(DrawCardAction(minion, soulMove.amount))
                }
            }
        })
    }

    private fun spikerGainThorns() {
        SoulMoveHelper.addCustomMove("spiker_gain_thorns", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} thorns"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(minion, minion, ThornsPower(minion, soulMove.amount)))
                }
            }
        })
    }

    private fun torchHeadGainStrength() {
        SoulMoveHelper.addCustomMove("torch_head_gain_strength", object : CustomSoulMove {
            override fun onRequestMoveImage(): Texture? = null

            override fun onRequestMoveDescription(soulMove: SoulMove): String {
                return "Gain ${soulMove.amount} strength"
            }

            override fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable {
                return Runnable {
                    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(minion, minion, StrengthPower(minion, soulMove.amount)))
                }
            }
        })
    }

}