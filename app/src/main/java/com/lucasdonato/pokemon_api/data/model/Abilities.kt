import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Abilities (
	@SerializedName("ability") val ability : Ability,
	@SerializedName("is_hidden") val is_hidden : Boolean,
	@SerializedName("slot") val slot : Int
): Serializable