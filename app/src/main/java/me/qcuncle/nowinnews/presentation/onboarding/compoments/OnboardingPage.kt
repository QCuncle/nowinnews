package me.qcuncle.nowinnews.presentation.onboarding.compoments

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.presentation.onboarding.Page
import me.qcuncle.nowinnews.presentation.onboarding.pages
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column(
        modifier = modifier
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.Inside,
            painter = painterResource(id = page.image),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "", //page.title,
            modifier = Modifier.padding(horizontal = 32.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = 32.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun OnBoardingPagePreview() {
    NinTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}