//package kobting.souleater.characters
//
//import basemod.abstracts.CustomMonster
//import basemod.abstracts.CustomPlayer
//import com.badlogic.gdx.graphics.Color
//import com.badlogic.gdx.graphics.g2d.BitmapFont
//import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
//import com.megacrit.cardcrawl.actions.AbstractGameAction
//import com.megacrit.cardcrawl.cards.AbstractCard
//import com.megacrit.cardcrawl.characters.AbstractPlayer
//import com.megacrit.cardcrawl.screens.CharSelectInfo
//import java.util.ArrayList
//
//class SoulEater(name: String) : CustomPlayer(
//        name,
//        THE_SOUL_EATER,
//        null,
//        null,
//        null as String?,
//        null as String?
//        ) {
//
//    companion object {
//
//        @JvmStatic
//        @SpireEnum
//        val THE_SOUL_EATER: AbstractPlayer.PlayerClass? = null
//
//        @JvmStatic
//        @SpireEnum
//        val CYAN_SOUL_EATER: AbstractCard.CardColor? = null
//
//        val monster = CustomMonster
//
//    }
//
//    override fun getCardRenderColor(): Color = Color.CYAN
//
//    override fun getStartingRelics(): ArrayList<String> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getLoadout(): CharSelectInfo {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getSlashAttackColor(): Color {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCardColor(): AbstractCard.CardColor {
//        return CYAN_SOUL_EATER!!
//    }
//
//    override fun getEnergyNumFont(): BitmapFont {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getAscensionMaxHPLoss(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCustomModeCharacterButtonSoundKey(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getStartingDeck(): ArrayList<String> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun doCharSelectScreenSelectEffect() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getSpireHeartText(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getStartCardForEvent(): AbstractCard {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getTitle(p0: PlayerClass?): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun newInstance(): AbstractPlayer {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getSpireHeartSlashEffect(): Array<AbstractGameAction.AttackEffect> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getLocalizedCharacterName(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getVampireText(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCardTrailColor(): Color {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}