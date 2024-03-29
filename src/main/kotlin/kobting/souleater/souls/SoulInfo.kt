package kobting.souleater.souls

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.megacrit.cardcrawl.monsters.AbstractMonster
import java.lang.reflect.Type

data class SoulInfo(
        @SerializedName("souls")
        val souls: List<Soul>
) {

    companion object {
        val type = object : TypeToken<SoulInfo>() {}.type
    }
}

class SoulInfoDeserializer : JsonDeserializer<SoulInfo> {

    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): SoulInfo {
        val list = json!!.asJsonObject

        return GsonBuilder().create().fromJson(list, SoulInfo.type)
    }

}

data class Soul(
        @Expose
        @SerializedName("id")
        val monsterId: String,
        @Expose
        @SerializedName("name")
        val monsterName: String,
        @Expose
        @SerializedName("image")
        val monsterImage: String?,
        @Expose
        @SerializedName("atlas")
        val monsterAtlasUrl: String?,
        @Expose
        @SerializedName("json")
        val monsterSkeletonJson: String?,
        var monsterType: AbstractMonster.EnemyType = AbstractMonster.EnemyType.NORMAL,
        var width: Float = 0F,
        var height: Float = 0F,
        @SerializedName("moves")
        val soulMoveInfo: List<SoulMove>? = null,
        @SerializedName("requiresUpgrade")
        val requiresUpgrade: Boolean = false,
    ) {

    //Fix this. The SoulMapper is using a set which is really a java hashset and there is a null pointer issue when adding to the set
    override fun hashCode(): Int {
        return super.hashCode()
    }

}

