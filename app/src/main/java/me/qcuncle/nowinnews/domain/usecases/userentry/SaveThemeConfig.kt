package me.qcuncle.nowinnews.domain.usecases.userentry

import me.qcuncle.nowinnews.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveThemeConfig @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(index: Int) {
        localUserManager.saveThemeConfig(index)
    }
}