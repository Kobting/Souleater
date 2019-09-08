package kobting.souleater.patches

import com.evacipated.cardcrawl.modthespire.lib.*
import com.google.gson.Gson
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue
import com.megacrit.cardcrawl.saveAndContinue.SaveFile
import com.sun.xml.internal.fastinfoset.util.StringArray
import javassist.CtBehavior
import kobting.souleater.souls.SoulSaveFile

class SaveAndContinuePatches {

    @SpirePatch(
            clz = SaveAndContinue::class,
            method = "loadSaveFile",
            paramtypez = arrayOf(String::class)
    )
    class LoadSaveFilePatch {

        companion object {

            @SpireInsertPatch(
                    locator = GsonUseLocator::class,
                    localvars = arrayOf("saveFile", "gson", "savestr")
            )
            fun changeGsonConversion(instance: SaveAndContinue, filePath: String, @ByRef saveFile: Array<SaveFile>, gson: Gson, savestr: String) {
                saveFile[0] = gson.fromJson<SoulSaveFile>(savestr, SoulSaveFile::class.java)
            }

            open class GsonUseLocator: SpireInsertLocator() {
                override fun Locate(methodToPatch: CtBehavior?): IntArray {
                    val matcher = Matcher.MethodCallMatcher(Gson::class.java, "fromJson")
                    val ret = LineFinder.findInOrder(methodToPatch, matcher)
                    ret[0] = ret[0] + 1
                    return ret
                }

            }

        }

    }

}