package kobting.souleater.souls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.vfx.TintEffect
import kobting.friendlyminions.monsters.AbstractFriendlyMonster

open class SoulMonster(val soul: Soul) : AbstractFriendlyMonster(
    soul.monsterName,
    soul.monsterId,
    40,
    -8F,
    10F,
    soul.width,
    soul.height,
    soul.monsterImage,
    -700F,
    245F
) {

    init {
        this.isPlayer = true
        setUpHp()
        setUpAnimations()
        setUpMoves()
    }

    override fun loadAnimation(atlasUrl: String?, skeletonUrl: String?, scale: Float) {
        super.loadAnimation(atlasUrl, skeletonUrl, scale)
        this.flipHorizontal = true
        this.tint = TintEffect()
        this.tint.changeColor(Color.CYAN.copy().apply { a = 0.3F })
    }

    //TODO randomize this more
    protected fun setUpHp() {
        setHp(15, 28)
    }

    protected fun setUpAnimations() {
        if (soul.monsterImage != null) {
            val texture = Texture(Gdx.files.internal(soul.monsterImage))
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            this.flipHorizontal = true
            this.tint = TintEffect()
            this.tint.changeColor(Color.CYAN.copy().apply { a = 0.3F })
            this.img = texture
        } else {
            if (soul.monsterAtlasUrl != null && soul.monsterSkeletonJson != null) {
                loadAnimation(soul.monsterAtlasUrl, soul.monsterSkeletonJson, 1.0F)
                try {
                    println("First Animation Name: ${state.data.skeletonData.animations[0]}")
                    val entry = state.setAnimation(0, "idle", true)
                    entry.time = entry.endTime * MathUtils.random()
                } catch (ex: Exception) {
                    println("Error: ${soul.monsterName} has no idle animation :(")
                    println("${soul.monsterName} animations: ")
                    state.data.skeletonData.animations.forEach {
                        println(it.name)
                    }
                }
            }
        }

    }

    protected fun setUpMoves() {

    }

    private fun Color.copy(): Color {
        return Color(r, g, b, a)
    }
}