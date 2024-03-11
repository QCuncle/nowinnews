package me.qcuncle.nowinnews.presentation.search.compoments

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.qcuncle.nowinnews.presentation.search.SearchScreenTopBar
import me.qcuncle.nowinnews.ui.theme.NinTheme


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun NewsTextButtonPreview() {
    NinTheme {
        Surface {
            SearchScreenTopBar(onKeyWordChanged = {}, onBackClick = {})
        }
    }
}