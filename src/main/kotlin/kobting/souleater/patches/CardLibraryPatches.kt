package kobting.souleater.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.CardLibrary
import kobting.souleater.cards.SoulCard
import kobting.souleater.souls.SoulMapper

class CardLibraryPatches {

    @SpirePatch(
            clz = CardLibrary::class,
            method = "getCopy",
            paramtypez = [String::class, Int::class, Int::class]
    )
    class GetCopyPatch {

        companion object {

            @JvmStatic
            @SpirePrefixPatch
            fun getCopyOfSoulCard(key: String, updgradeTime: Int, misc: Int): SpireReturn<AbstractCard> {
                if(key.contains(SoulMapper.soulCardPrefix)) {
                    return SpireReturn.Return(SoulCard.createSoulCard(key) ?: return SpireReturn.Continue())
                } else {
                    return SpireReturn.Continue()
                }
            }
        }
    }

}