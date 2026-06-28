package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.ui.graphics.Color
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

enum class ReceiverState(
    val color: Color,
    val delayUntilNextState: Duration
) {

    receivingData(IndicatorColor, 3.seconds),
    noData(Color(0xFF333333), 7.seconds),
    connectionLost(Color(0xFFFF0000), 5.seconds), // it seems the connection manager needs some time to realize connection loss
    retryToConnectAttemp1(Color(0xFFFF8800), 30.seconds),
    retryToConnectAttemp2(Color(0xFFFF7711), 30.seconds),
    retryToConnectAttemp3(Color(0xFFFF6633), 1.minutes),
    retryToConnectAttemp4(Color(0xFFFF5555), 1.minutes),
    retryToConnectAttemp5(Color(0xFFFF4477), 2.minutes),
    retryToConnectAttemp6(Color(0xFFFF33aa), 2.minutes),
    retryToConnectAttemp7(Color(0xFFFF22cc), 3.minutes),
    cannotRecoverConnection(Color(0xFFFF00FF), 0.seconds),
    serverDown(Color(0xFF00FFFF), 0.seconds)
    ;

    companion object {
        val retryStates = setOf(
            retryToConnectAttemp1,
            retryToConnectAttemp2,
            retryToConnectAttemp3,
            retryToConnectAttemp4,
            retryToConnectAttemp5,
            retryToConnectAttemp6,
            retryToConnectAttemp7
        )
    }
}
