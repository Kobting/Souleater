package kobting.souleater.patches

import com.evacipated.cardcrawl.modthespire.lib.*
import com.evacipated.cardcrawl.modthespire.patcher.Expectation
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardSave
import javassist.CtBehavior
import javassist.bytecode.Bytecode
import javassist.expr.Expr
import javassist.expr.ExprEditor
import kobting.souleater.cards.SoulCard
import kobting.souleater.souls.SoulCardSave

class CardGroupPatches {


    @SpirePatch(
        clz = CardGroup::class,
        method = "getCardDeck"
    )
    class GetCardDeckPatch {

        companion object {

            @SpireInsertPatch(
                    locator = CreateCardSaveLocator::class,
                    localvars = arrayOf("retVal", "card")
            )
            @JvmStatic
            fun patchInSoulSaving(instance: CardGroup, retVal: java.util.ArrayList<CardSave>, card: AbstractCard) {
                if(card is SoulCard) {
                    retVal.removeAt(retVal.lastIndex)
                    retVal.add(SoulCardSave(card.soul, card.cardID, card.timesUpgraded, card.misc))
                }
            }

            open class CreateCardSaveLocator : SpireInsertLocator() {
                override fun Locate(ctMethodToPatch: CtBehavior): IntArray {
//                    ctMethodToPatch.instrument(object : ExprEditor() {
//
//                    })
                    val matcher = Matcher.MethodCallMatcher(java.util.ArrayList::class.java, "add")
                    val ret = LineFinder.findInOrder(ctMethodToPatch, arrayListOf(), matcher)
                    ret[0] = ret[0]
                    return ret
                }
            }
        }
    }
}