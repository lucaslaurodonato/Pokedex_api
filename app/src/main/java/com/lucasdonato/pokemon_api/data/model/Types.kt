import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Types(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: Type
) : Serializable