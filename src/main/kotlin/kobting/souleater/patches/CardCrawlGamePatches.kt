package kobting.souleater.patches

import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.cards.CardSave
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.CardLibrary
import javassist.CtBehavior
import kobting.souleater.cards.SoulCard
import kobting.souleater.souls.Soul
import kobting.souleater.souls.SoulCardSave

class CardCrawlGamePatches {

    @SpirePatch(
            clz = CardCrawlGame::class,
            method = "loadPlayerSave"
    )
    class LoadPlayerSavePatch {

        companion object {

            @JvmStatic
            @SpireInsertPatch(
                    locator = LoadSoulCardSaveLocator::class,
                    localvars = arrayOf("s")
            )
            fun loadSoulCardSaves(instance: CardCrawlGame, player: AbstractPlayer, s: CardSave) {
                if(s.id.contains("souleater")) {
                    with(s as SoulCardSave) {
                        println("Found SoulCardSave: $this")
                        AbstractDungeon.player.masterDeck.addToTop(SoulCard(this.soul))
                    }
                }
            }

            open class LoadSoulCardSaveLocator: SpireInsertLocator() {
                override fun Locate(methodToPatch: CtBehavior?): IntArray {
                    val matcher = Matcher.MethodCallMatcher(CardLibrary::class.java, "getCopy")
                    val ret = LineFinder.findAllInOrder(methodToPatch, matcher)
                    ret[0] = ret[0]
                    return ret
                }

            }
        }

    }

}