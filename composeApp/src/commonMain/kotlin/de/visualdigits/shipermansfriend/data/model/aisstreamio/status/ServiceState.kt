package de.visualdigits.shipermansfriend.data.model.aisstreamio.status

import co.touchlab.kermit.Severity

enum class ServiceState(
    val severity: Severity
) {

    Up(Severity.Info),
    Down(Severity.Error)
}
