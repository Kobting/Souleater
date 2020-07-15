package kobting.souleater.souls

import com.badlogic.gdx.graphics.Texture
import kobting.friendlyminions.monsters.AbstractFriendlyMonster

interface CustomSoulMove {
    fun onRequestMoveImage(): Texture?
    fun onRequestMoveDescription(soulMove: SoulMove): String
    fun onRequestMoveActions(minion: AbstractFriendlyMonster, soulMove: SoulMove): Runnable
}