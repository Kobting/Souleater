package kobting.souleater.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator

//Disables obfuscation of save files. Will corrupt any already obfuscated save
class SaveFileObfuscatorPatches {

    @SpirePatch(
            clz = SaveFileObfuscator::class,
            method = "encode"
    )
    class EncodePatch {


        companion object {

            @JvmStatic
            @SpirePrefixPatch
            fun Prefix(s: String, key: String): SpireReturn<String> {
                return SpireReturn.Return(s)
            }
        }
    }

    @SpirePatch(
            clz = SaveFileObfuscator::class,
            method = "decode"
    )
    class DecodePatch {

        companion object {

            @JvmStatic
            @SpirePrefixPatch
            fun Prefix(s: String, key: String): SpireReturn<String> {
                return SpireReturn.Return(s)
            }
        }
    }
}