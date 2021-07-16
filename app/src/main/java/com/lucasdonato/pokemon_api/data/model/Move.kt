import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Move (
	@SerializedName("name") val name : String
) : Serializable