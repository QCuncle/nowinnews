package me.qcuncle.nowinnews.presentation.navigator.compoments

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedItem
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(index) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        ThemedXmlDrawable(
                            drawableResId = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            modifier = Modifier.size(24.dp),
                            iconColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val text: String
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun NewsBottomNavigationPreview() {
    NinTheme(dynamicColor = false) {
        BottomNavigation(
            items = listOf(
                BottomNavigationItem(
                    unselectedIcon = R.drawable.outline_whatshot_24,
                    selectedIcon = R.drawable.baseline_whatshot_24,
                    text = "热榜"
                ),
                BottomNavigationItem(
                    unselectedIcon = R.drawable.outline_bookmarks_24,
                    selectedIcon = R.drawable.baseline_bookmarks_24,
                    text = "书签"
                ),
                BottomNavigationItem(
                    unselectedIcon = R.drawable.outline_settings_24,
                    selectedIcon = R.drawable.baseline_settings_24,
                    text = "设置"
                ),
            ),
            selectedItem = 0,
            onItemClick = {}
        )
    }
}