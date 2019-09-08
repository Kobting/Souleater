package kobting.souleater.souls

import com.megacrit.cardcrawl.saveAndContinue.SaveFile

data class SoulSaveFile(var cards: ArrayList<SoulCardSave>): SaveFile()