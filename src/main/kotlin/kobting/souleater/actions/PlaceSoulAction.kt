package kobting.souleater.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Bezier
import com.badlogic.gdx.math.Vector2
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.*
import com.megacrit.cardcrawl.helpers.input.InputHelper
import kobting.friendlyminions.helpers.BasePlayerMinionHelper
import kobting.friendlyminions.monsters.MinionMoveGroup
import kobting.souleater.souls.Soul
import kobting.souleater.souls.SoulMonster
import kobting.souleater.souls.SoulMoveHelper

open class PlaceSoulAction(val player: AbstractPlayer, val soul: Soul, val placeSoulComplete: PlaceSoulComplete): AbstractGameAction() {

    private val initialMousePosition: Vector2
    private lateinit var controlPoint: Vector2
    var arrowColor = Color(1.0f, 0.2f, 0.3f, 1.0f)
    private val arrowPoints = Array(20) { Vector2() }

    init {
        isDone = false
        initialMousePosition = Vector2(InputHelper.mX.toFloat(), InputHelper.mY.toFloat())
    }

    override fun update() {
        if(InputHelper.justClickedLeft) {
            BasePlayerMinionHelper.addMinion(player, SoulMonster(soul).apply {
                this.drawX = InputHelper.mX.toFloat()
                this.drawY = InputHelper.mY.toFloat()
                this.moves = MinionMoveGroup(SoulMoveHelper.createMinionMoves(this, soul.soulMoveInfo), this.drawX, this.drawY)
            })
            placeSoulComplete.onPlaceSoulComplete(true)
            this.isDone = true
        }
    }

    fun render(sb: SpriteBatch) {
        renderArrow(sb)
    }


    private fun renderArrow(sb: SpriteBatch) {
        val x = InputHelper.mX.toFloat()
        val y = InputHelper.mY.toFloat()
        controlPoint = Vector2(initialMousePosition.x - (x - initialMousePosition.x) / 4f, y + (y - initialMousePosition.y - 40f * Settings.scale) / 2f)
        val angle = Vector2(initialMousePosition.x - x, initialMousePosition.y - y).nor().angle() + 90f
        sb.color = arrowColor
        drawCurvedLine(sb, Vector2(initialMousePosition.x, initialMousePosition.y), Vector2(x, y), controlPoint)
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128f, y - 128f, 128f, 128f, 256f, 256f, Settings.scale, Settings.scale, angle, 0, 0, 256, 256, false, false)
    }

    private fun drawCurvedLine(sb: SpriteBatch, start: Vector2, end: Vector2, control: Vector2) {
        var radius = 7f * Settings.scale
        var tmp: Vector2
        var angle: Float

        for (i in arrowPoints.indices) {
            arrowPoints[i] = Bezier.quadratic(arrowPoints[i], i.toFloat() / 20f, start, control, end, Vector2())
            radius += (0.4f * Settings.scale)

            if (i != 0) {
                tmp = Vector2(arrowPoints[i - 1].x - arrowPoints[i].x, arrowPoints[i - 1].y - arrowPoints[i].y)
                angle = tmp.nor().angle() + 90f
            } else {
                tmp = Vector2(control.x - arrowPoints[i].x, control.y - arrowPoints[i].y)
                angle = tmp.nor().angle() + 270f
            }

            //64 is probably the width and height of the circle but hard coding based on copy of base game code
            sb.draw(ImageMaster.TARGET_UI_CIRCLE, arrowPoints[i].x - 64f, arrowPoints[i].y - 64f, 64f, 64f, 128f, 128f, radius / 18f, radius / 18f, angle, 0, 0, 128, 128, false, false)
        }
    }

    interface PlaceSoulComplete {
        fun onPlaceSoulComplete(completed: Boolean)
    }
}