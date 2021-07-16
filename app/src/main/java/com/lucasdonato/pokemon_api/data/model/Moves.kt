import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Moves (
	@SerializedName("move") val move : Move
) : Serializable