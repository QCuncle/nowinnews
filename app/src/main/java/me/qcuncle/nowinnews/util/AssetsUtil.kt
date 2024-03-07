package me.qcuncle.nowinnews.util

object AssetsUtil {
    fun readJsonFromAsset(fileName: String): String {
        return try {
            // 获取 assets 中的文件输入流
            val inputStream = this::class.java.classLoader?.getResourceAsStream("assets/$fileName")

            // 读取文件内容并转换为字符串
            inputStream?.bufferedReader()?.use { it.readText() } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}