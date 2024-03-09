package me.qcuncle.nowinnews.presentation.setting

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.presentation.common.NewsCancelTextButton
import me.qcuncle.nowinnews.presentation.common.NewsTextButton
import me.qcuncle.nowinnews.presentation.common.SelectDialog
import me.qcuncle.nowinnews.presentation.common.SettingItem
import me.qcuncle.nowinnews.ui.theme.Color0
import me.qcuncle.nowinnews.ui.theme.Color1
import me.qcuncle.nowinnews.ui.theme.Color2
import me.qcuncle.nowinnews.ui.theme.Color3
import me.qcuncle.nowinnews.ui.theme.Color4
import me.qcuncle.nowinnews.ui.theme.Color5
import me.qcuncle.nowinnews.ui.theme.Color6
import me.qcuncle.nowinnews.ui.theme.Color7
import me.qcuncle.nowinnews.ui.theme.Color8
import me.qcuncle.nowinnews.ui.theme.Color9
import me.qcuncle.nowinnews.util.getAppVersionCode
import me.qcuncle.nowinnews.util.getAppVersionName
import me.qcuncle.nowinnews.util.jumpToBrowser
import me.qcuncle.nowinnews.util.showToast
import okhttp3.internal.toHexString
import okio.IOException
import java.io.InputStream

@SuppressLint("Recycle")
@Composable
fun SettingScreen(
    newsShowNum: Int,
    darkMode: Int,
    themeIndex: Int,
    event: (SettingEvent) -> Unit
) {

    val context = LocalContext.current
    var showNumberSelectDialog by remember { mutableStateOf(false) }
    var showDarkModeSelectDialog by remember { mutableStateOf(false) }
    var showThemeSelectDialog by remember { mutableStateOf(false) }
    var showRestConfig by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // 处理选择的文件的 Uri
        uri?.let {
            try {
                val isJsonFile = uri.path?.endsWith(".json", true)
                if (isJsonFile == null || isJsonFile == false) {
                    context.showToast("文件格式不支持")
                    return@let
                }
                val contentResolver = context.contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val bufferSize = 1024
                    val buffer = ByteArray(bufferSize)
                    var bytesRead: Int
                    val stringBuilder = StringBuilder()

                    while (inputStream.read(buffer, 0, bufferSize).also { bytesRead = it } != -1) {
                        // 处理每次读取的数据
                        val chunk = buffer.copyOfRange(0, bytesRead)
                        stringBuilder.append(String(chunk))
                    }
                    val jsonString = stringBuilder.toString()
                    event(SettingEvent.UploadSubscriptionConfiguration(context, jsonString))
                } else {
                    context.showToast("Failed to open InputStream")
                }
            } catch (e: IOException) {
                context.showToast(e.message)
            }
        }
    }

    val darkModeDesc = when (darkMode) {
        0 -> stringResource(R.string.setting_open)
        1 -> stringResource(R.string.setting_close)
        2 -> stringResource(R.string.setting_follow_system)
        else -> stringResource(R.string.setting_follow_system)
    }
    val colorHex = MaterialTheme.colorScheme.primary
        .toArgb().toHexString().toUpperCase(Locale.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.basis),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )

        SettingItem(
            icon = R.drawable.baseline_format_list_numbered_24,
            title = stringResource(R.string.setting_hot_num),
            content = "$newsShowNum",
            onClick = { showNumberSelectDialog = true }
        )

        SettingItem(
            icon = R.drawable.baseline_dark_mode_24,
            title = stringResource(R.string.setting_dark_mode),
            content = darkModeDesc,
            onClick = { showDarkModeSelectDialog = true }
        )

        SettingItem(
            icon = R.drawable.baseline_color_lens_24,
            title = stringResource(R.string.setting_theme),
            content = colorHex,
            contentTextColor = MaterialTheme.colorScheme.primary,
            onClick = { showThemeSelectDialog = true }
        )

        SettingItem(
            icon = R.drawable.baseline_replay_circle_filled_24,
            title = stringResource(R.string.setting_reset_config),
            content = stringResource(R.string.setting_desc_reset_config),
            onClick = { showRestConfig = true }
        )

        SettingItem(
            icon = R.drawable.baseline_export_24,
            title = stringResource(R.string.setting_export_config),
            content = stringResource(R.string.setting_desc_export_config),
            onClick = {
                event(SettingEvent.ExportFile(context))
            }
        )

        SettingItem(
            icon = R.drawable.baseline_upload_file_24,
            title = stringResource(R.string.setting_upload_config),
            content = stringResource(R.string.setting_desc_upload_config),
            onClick = {
                launcher.launch("application/json")
            }
        )

        Text(
            text = stringResource(R.string.other),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )

        SettingItem(
            icon = R.drawable.baseline_code_24,
            title = stringResource(R.string.about),
            content = "${context.getAppVersionName()}(${context.getAppVersionCode()})",
            onClick = {
                context.jumpToBrowser("https://github.com/QCuncle/nowinnews")
            }
        )

        SettingItem(
            icon = R.drawable.baseline_telegram,
            title = stringResource(R.string.setting_swipe_water),
            content = stringResource(R.string.setting_desc_chat),
            onClick = {
                context.jumpToBrowser("https://t.me/nowinnews")
            }
        )
    }

    if (showNumberSelectDialog) {
        var confirmNum = newsShowNum
        SelectDialog(
            title = {},
            content = {
                NumSelectView(newsShowNum) { num ->
                    confirmNum = num
                }
            },
            onDismiss = {
                showNumberSelectDialog = false
            },
            onConfirm = {
                event(SettingEvent.SaveShowNum(confirmNum))
            }
        )
    }

    if (showDarkModeSelectDialog) {
        var confirmDarkMode = darkMode
        SelectDialog(
            title = {},
            content = {
                DarkModeSelectView(darkMode) { mode ->
                    confirmDarkMode = mode
                }
            },
            onDismiss = {
                showDarkModeSelectDialog = false
            },
            onConfirm = {
                event(SettingEvent.SaveDarkMode(confirmDarkMode))
            }
        )
    }

    if (showThemeSelectDialog) {
        var confirmThemeConfig = themeIndex
        SelectDialog(
            title = {},
            content = {
                ThemeConfigSelectView(themeIndex) { index ->
                    confirmThemeConfig = index
                }
            },
            onDismiss = {
                showThemeSelectDialog = false
            },
            onConfirm = {
                event(SettingEvent.SaveThemeConfig(confirmThemeConfig))
            }
        )
    }

    if (showRestConfig) {
        AlertDialog(
            onDismissRequest = {
                showRestConfig = false
            },
            title = {
                Text(
                    text = "是否确认重置App的订阅配置",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            dismissButton = {
                NewsCancelTextButton(
                    text = stringResource(id = R.string.cancel),
                ) { showRestConfig = false }
            },
            confirmButton = {
                NewsTextButton(
                    text = stringResource(id = R.string.confirm),
                ) {
                    showRestConfig = false
                    event(SettingEvent.RestSubscriptionConfiguration)
                }
            },
        )
    }
}

@Composable
fun NumSelectView(
    newsShowNum: Int,
    onclick: (Int) -> Unit
) {
    val selectIndex = when (newsShowNum) {
        3 -> 0
        5 -> 1
        10 -> 2
        15 -> 3
        else -> 0
    }
    var selectedOption by remember {
        mutableIntStateOf(selectIndex)
    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        RadioButtonOption(0, "3", selectedOption) {
            selectedOption = it
            onclick(3)
        }
        RadioButtonOption(1, "5", selectedOption) {
            selectedOption = it
            onclick(5)
        }
        RadioButtonOption(2, "10", selectedOption) {
            selectedOption = it
            onclick(10)
        }
        RadioButtonOption(3, "15", selectedOption) {
            selectedOption = it
            onclick(15)
        }
    }
}

@Composable
fun DarkModeSelectView(
    darkMode: Int,
    onclick: (Int) -> Unit
) {
    var selectedOption by remember {
        mutableIntStateOf(darkMode)
    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        RadioButtonOption(0, stringResource(R.string.setting_open), selectedOption) {
            selectedOption = it
            onclick(it)
        }
        RadioButtonOption(1, stringResource(R.string.setting_close), selectedOption) {
            selectedOption = it
            onclick(it)
        }
        RadioButtonOption(2, stringResource(R.string.setting_follow_system), selectedOption) {
            selectedOption = it
            onclick(it)
        }
    }
}

@Composable
fun ThemeConfigSelectView(
    themeIndex: Int,
    onSelect: (Int) -> Unit
) {
    var selectedOption by remember {
        mutableIntStateOf(themeIndex)
    }
    val colors = listOf(
        Color0.seed,
        Color1.seed,
        Color2.seed,
        Color3.seed,
        Color4.seed,
        Color5.seed,
        Color6.seed,
        Color7.seed,
        Color8.seed,
        Color9.seed
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(colors.size) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp) // 调整子项的大小
                    .clip(CircleShape) // 使用半径的一半作为圆形的圆角
                    .background(colors[index])
                    .clickable {
                        selectedOption = index
                        onSelect(index)
                    }
            ) {
                if (selectedOption == index) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_checked),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun RadioButtonOption(
    index: Int,
    text: String,
    selectedOption: Int,
    onclick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onclick(index)
            }
            .padding(vertical = 6.dp)
    ) {

        RadioButton(
            selected = index == selectedOption,
            onClick = null
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 24.dp)
        )
    }
}