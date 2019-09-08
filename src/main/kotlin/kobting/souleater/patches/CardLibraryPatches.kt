//package kobting.souleater.patches
//
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
//import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
//import com.megacrit.cardcrawl.helpers.CardLibrary
//import kobting.souleater.cards.SoulCard
//
//class CardLibraryPatches {
//
//    @SpirePatch(
//            clz = CardLibrary::class,
//            method = "getCopy"
//    )
//    class GetCopyPatch {
//
//        companion object {
//
//            @JvmStatic
//            @SpirePrefixPatch
//            fun getCopyOfSoulCard(instance: CardLibrary, key: String, updgradeTime: Int, misc: Int) {
//                if(key.contains("souleater")) {
//
//                }
//            }
//        }
//    }
//
//}