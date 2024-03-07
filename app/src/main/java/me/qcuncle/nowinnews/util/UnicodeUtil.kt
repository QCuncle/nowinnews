package me.qcuncle.nowinnews.util

fun String.unicodeDecode(): String {
    val builder = StringBuilder()
    var i = 0
    while (i < this.length) {
        if (this[i] == '\\' && i + 1 < this.length && this[i + 1] == 'u') {
            // Found Unicode escape sequence
            val unicodeSequence = this.substring(i + 2, i + 6)
            val unicodeChar = unicodeSequence.toInt(16).toChar()
            builder.append(unicodeChar)
            i += 6 // Move the index to the next character after the Unicode sequence
        } else {
            // Normal character
            builder.append(this[i])
            i++
        }
    }
    return builder.toString()
}