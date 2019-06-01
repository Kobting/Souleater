package kobting.souleater.helpers

import basemod.BaseMod
import basemod.interfaces.EditCardsSubscriber
import kobting.souleater.cards.SoulStealer

object CardHelper: EditCardsSubscriber {

    private var imagePaths = ImageMap("kobting/images/souleater/cards/")
    private const val DEFAULT_IMAGE_NAME = "beta_purple.png"

    init {
        BaseMod.subscribe(this)
    }

    override fun receiveEditCards() {
        initImagePaths()

        BaseMod.addCard(SoulStealer())
    }

    private fun initImagePaths() {
        println("Soul Eater: Creating Card Images")

    }

    fun getImagePath(id: String): String {
        return imagePaths.getOrDefault(id, DEFAULT_IMAGE_NAME)
    }
}