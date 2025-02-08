import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mhmtn.DishWhiz.ui.theme.Acik
import com.mhmtn.DishWhiz.ui.theme.Koyu

@Composable
fun SetSystemBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val statusBarColor = if (isSystemInDarkTheme()) {
        Koyu
    } else {
        Acik
    }

    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = useDarkIcons
    )
}
