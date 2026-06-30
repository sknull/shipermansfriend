package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_ar
import de.visualdigits.compose.resources.flag_bo
import de.visualdigits.compose.resources.flag_br
import de.visualdigits.compose.resources.flag_cl
import de.visualdigits.compose.resources.flag_co
import de.visualdigits.compose.resources.flag_ec
import de.visualdigits.compose.resources.flag_fk
import de.visualdigits.compose.resources.flag_gy
import de.visualdigits.compose.resources.flag_pe
import de.visualdigits.compose.resources.flag_py
import de.visualdigits.compose.resources.flag_sr
import de.visualdigits.compose.resources.flag_unknown
import de.visualdigits.compose.resources.flag_uy
import de.visualdigits.compose.resources.flag_ve
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountrySouthAmerica(
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_ARGENTINA("701", "ar", "Argentina", Res.drawable.flag_ar),
    COUNTRY_BOLIVIA("720", "bo", "Bolivia", Res.drawable.flag_bo),
    COUNTRY_BRAZIL("710", "br", "Brazil", Res.drawable.flag_br),
    COUNTRY_CHILE("725", "cl", "Chile", Res.drawable.flag_cl),
    COUNTRY_COLOMBIA("730", "co", "Colombia", Res.drawable.flag_co),
    COUNTRY_ECUADOR("735", "ec", "Ecuador", Res.drawable.flag_ec),
    COUNTRY_FALKLAND_ISLANDS("740", "fk", "Falkland Islands", Res.drawable.flag_fk),
    COUNTRY_FRENCH_GUIANA("745", "gf", "French Guiana", Res.drawable.flag_unknown),
    COUNTRY_GUYANA("750", "gy", "Guyana", Res.drawable.flag_gy),
    COUNTRY_PARAGUAY("755", "py", "Paraguay", Res.drawable.flag_py),
    COUNTRY_PERU("760", "pe", "Peru", Res.drawable.flag_pe),
    COUNTRY_SURINAME("765", "sr", "Suriname", Res.drawable.flag_sr),
    COUNTRY_URUGUAY("770", "uy", "Uruguay", Res.drawable.flag_uy),
    COUNTRY_VENEZUELA("775", "ve", "Venezuela", Res.drawable.flag_ve),
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountrySouthAmerica? {
            return MmsiCountrySouthAmerica.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
