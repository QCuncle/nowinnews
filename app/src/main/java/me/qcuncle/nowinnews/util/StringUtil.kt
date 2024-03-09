package me.qcuncle.nowinnews.util

import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val byteArray = md.digest(this.toByteArray())
    val hexString = StringBuilder()
    for (byte in byteArray) {
        hexString.append(String.format("%02x", byte))
    }
    return hexString.toString()
}