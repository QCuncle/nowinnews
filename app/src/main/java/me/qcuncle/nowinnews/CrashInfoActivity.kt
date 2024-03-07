package me.qcuncle.nowinnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.presentation.common.NewsButton
import me.qcuncle.nowinnews.ui.theme.NinTheme
import me.qcuncle.nowinnews.util.rebootApp

class CrashInfoActivity : ComponentActivity() {
    companion object {
        const val EXTRA_ERROR_MESSAGE = "extra_error_message"
        const val EXTRA_ERROR_STACK = "extra_error_stack"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val message: String = intent.getStringExtra(EXTRA_ERROR_MESSAGE) ?: "unKnown"
        val cause: String = intent.getStringExtra(EXTRA_ERROR_STACK) ?: "unKnown"

        setContent {
            NinTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState(0))
                ) {
                    val context = LocalContext.current
                    NewsButton(text = getString(R.string.reboot_app)) {
                        context.rebootApp()
                    }
                    SelectionContainer {

                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = message,
                            color = colorResource(id = R.color.black)
                        )
                    }

                    SelectionContainer {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.extraSmall)
                                .border(1.dp, Color.Gray)
                                .padding(4.dp),
                            text = "Error Stack Trace:\n $cause",
                            color = colorResource(id = R.color.black)
                        )
                    }

                }
            }
        }
    }
}