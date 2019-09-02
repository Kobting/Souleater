package kobting.souleater.souls

import com.badlogic.gdx.graphics.Texture
import kobting.friendlyminions.monsters.AbstractFriendlyMonster

interface CustomSoulMove {
    fun onRequestMoveImage(): Texture?
    fun onRequestMoveDescription(): String
    fun onRequestMoveActions(minion: AbstractFriendlyMonster): Runnable
}