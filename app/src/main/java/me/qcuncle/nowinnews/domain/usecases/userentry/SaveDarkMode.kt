package me.qcuncle.nowinnews.domain.usecases.userentry

import me.qcuncle.nowinnews.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveDarkMode @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(value: Int) {
        localUserManager.saveDarkMode(value)
    }
}