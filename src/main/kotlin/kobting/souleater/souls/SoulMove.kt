package kobting.souleater.souls

import com.google.gson.annotations.SerializedName

data class SoulMove(
        @SerializedName("type")
        val type: Type = Type.ATTACK,
        @SerializedName("amount")
        val amount: Int = 0,
        @SerializedName("id")
        val id: String? = "") {

    enum class Type(val value: String) {
        @SerializedName("ATTACK")
        ATTACK("ATTACK"),
        @SerializedName("DEFEND")
        DEFEND("DEFEND"),
        @SerializedName("HEAL")
        HEAL("HEAL"),
        @SerializedName("WEAKEN")
        WEAKEN("WEAKEN"),
        @SerializedName("VULNERABLE")
        VULNERABLE("VULNERABLE"),
        @SerializedName("CUSTOM")
        CUSTOM("CUSTOM")
    }

}