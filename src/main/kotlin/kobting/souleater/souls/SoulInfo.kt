package kobting.souleater.souls

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.megacrit.cardcrawl.helpers.EnemyData
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
        var height: Float = 0F)

