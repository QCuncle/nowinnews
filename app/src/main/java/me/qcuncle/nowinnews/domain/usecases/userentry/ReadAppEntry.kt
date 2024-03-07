package me.qcuncle.nowinnews.domain.usecases.userentry

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.manager.LocalUserManager
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}