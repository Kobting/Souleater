package kobting.souleater.souls

import com.megacrit.cardcrawl.cards.CardSave
import kobting.souleater.souls.Soul

class SoulCardSave(val soul: Soul, cardId: String, timesUpgraded: Int, misc: Int): CardSave(cardId, timesUpgraded, misc)