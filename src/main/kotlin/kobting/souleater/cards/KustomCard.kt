package kobting.souleater.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import kobting.souleater.helpers.CardHelper

abstract class KustomCard(
        ID: String,
        cost: Int,
        type: AbstractCard.CardType,
        color: AbstractCard.CardColor?,
        rarity: AbstractCard.CardRarity,
        target: AbstractCard.CardTarget) :
        CustomCard(
                ID,
                "",
                "kobting/images/souleater/cards/beta_purple.png",
                cost,
                "",
                type,
                color,
                rarity,
                target
        ) {

        private var cardStrings: CardStrings? = null

        companion object {

        }

        init {

                this.cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)

                this.name = cardStrings?.NAME ?: "Error: No Name: ${this::class.java.name}"
                this.initializeTitle()

                this.rawDescription = cardStrings?.DESCRIPTION ?: "Error: No Description. NL You didn't add this card to card strings."
                this.initializeDescription()

                this.textureImg = CardHelper.getImagePath(ID)
                this.loadCardImage(textureImg)

        }

        fun upgradeDescription() {
                val newDescription = this.cardStrings?.UPGRADE_DESCRIPTION ?: ""
                if (newDescription != "") {
                        this.rawDescription = newDescription
                        this.initializeDescription()
                }
        }

}