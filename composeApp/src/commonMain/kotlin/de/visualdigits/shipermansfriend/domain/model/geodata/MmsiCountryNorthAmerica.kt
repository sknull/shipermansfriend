package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_ag
import de.visualdigits.compose.resources.flag_an
import de.visualdigits.compose.resources.flag_aw
import de.visualdigits.compose.resources.flag_bb
import de.visualdigits.compose.resources.flag_bm
import de.visualdigits.compose.resources.flag_bs
import de.visualdigits.compose.resources.flag_bz
import de.visualdigits.compose.resources.flag_ca
import de.visualdigits.compose.resources.flag_cr
import de.visualdigits.compose.resources.flag_cu
import de.visualdigits.compose.resources.flag_dm
import de.visualdigits.compose.resources.flag_do
import de.visualdigits.compose.resources.flag_gd
import de.visualdigits.compose.resources.flag_gl
import de.visualdigits.compose.resources.flag_gt
import de.visualdigits.compose.resources.flag_hn
import de.visualdigits.compose.resources.flag_ht
import de.visualdigits.compose.resources.flag_jm
import de.visualdigits.compose.resources.flag_kn
import de.visualdigits.compose.resources.flag_ky
import de.visualdigits.compose.resources.flag_lc
import de.visualdigits.compose.resources.flag_mq
import de.visualdigits.compose.resources.flag_ms
import de.visualdigits.compose.resources.flag_mx
import de.visualdigits.compose.resources.flag_ni
import de.visualdigits.compose.resources.flag_pa
import de.visualdigits.compose.resources.flag_pr
import de.visualdigits.compose.resources.flag_sv
import de.visualdigits.compose.resources.flag_tc
import de.visualdigits.compose.resources.flag_tt
import de.visualdigits.compose.resources.flag_unknown
import de.visualdigits.compose.resources.flag_us
import de.visualdigits.compose.resources.flag_vc
import de.visualdigits.compose.resources.flag_vg
import de.visualdigits.compose.resources.flag_vi
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountryNorthAmerica(
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_ALASKA("303", "us", "Alaska", Res.drawable.flag_us),
    COUNTRY_ANGUILLA("301", "ai", "Anguilla", Res.drawable.flag_unknown),
    COUNTRY_ANTIGUA_AND_BARBUDA_1("304", "ag", "Antigua And Barbuda", Res.drawable.flag_ag),
    COUNTRY_ANTIGUA_AND_BARBUDA_2("305", "ag", "Antigua And Barbuda", Res.drawable.flag_ag),
    COUNTRY_ARUBA("307", "aw", "Aruba", Res.drawable.flag_aw),
    COUNTRY_BAHAMAS_1("308", "bs", "Bahamas", Res.drawable.flag_bs),
    COUNTRY_BAHAMAS_2("309", "bs", "Bahamas", Res.drawable.flag_bs),
    COUNTRY_BAHAMAS_3("311", "bs", "Bahamas", Res.drawable.flag_bs),
    COUNTRY_BARBADOS("314", "bb", "Barbados", Res.drawable.flag_bb),
    COUNTRY_BELIZE("312", "bz", "Belize", Res.drawable.flag_bz),
    COUNTRY_BERMUDA("310", "bm", "Bermuda", Res.drawable.flag_bm),
    COUNTRY_BRITISH_VIRGIN_ISLANDS("378", "vg", "British Virgin Islands", Res.drawable.flag_vg),
    COUNTRY_CANADA("316", "ca", "Canada", Res.drawable.flag_ca),
    COUNTRY_CAYMAN_ISLANDS("319", "ky", "Cayman Islands", Res.drawable.flag_ky),
    COUNTRY_COSTA_RICA("321", "cr", "Costa Rica", Res.drawable.flag_cr),
    COUNTRY_CUBA("323", "cu", "Cuba", Res.drawable.flag_cu),
    COUNTRY_DOMINICA("325", "dm", "Dominica", Res.drawable.flag_dm),
    COUNTRY_DOMINICAN_REPUBLIC("327", "do", "Dominican Republic", Res.drawable.flag_do),
    COUNTRY_EL_SALVADOR("359", "sv", "El Salvador", Res.drawable.flag_sv),
    COUNTRY_GREENLAND("331", "gl", "Greenland", Res.drawable.flag_gl),
    COUNTRY_GRENADA("330", "gd", "Grenada", Res.drawable.flag_gd),
    COUNTRY_GUADELOUPE("329", "gp", "Guadeloupe", Res.drawable.flag_unknown),
    COUNTRY_GUATEMALA("332", "gt", "Guatemala", Res.drawable.flag_gt),
    COUNTRY_HAITI("336", "ht", "Haiti", Res.drawable.flag_ht),
    COUNTRY_HONDURAS("334", "hn", "Honduras", Res.drawable.flag_hn),
    COUNTRY_JAMAICA("339", "jm", "Jamaica", Res.drawable.flag_jm),
    COUNTRY_MARTINIQUE("347", "mq", "Martinique", Res.drawable.flag_mq),
    COUNTRY_MEXICO("345", "mx", "Mexico", Res.drawable.flag_mx),
    COUNTRY_MONTSERRAT("348", "ms", "Montserrat", Res.drawable.flag_ms),
    COUNTRY_NETHERLANDS_ANTILLES("306", "an", "Netherlands Antilles", Res.drawable.flag_an),
    COUNTRY_NICARAGUA("350", "ni", "Nicaragua", Res.drawable.flag_ni),
    COUNTRY_PANAMA_1("351", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_2("352", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_3("353", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_4("354", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_5("355", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_6("356", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_7("357", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_8("370", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_9("371", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_10("372", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_11("373", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PANAMA_12("374", "pa", "Panama", Res.drawable.flag_pa),
    COUNTRY_PUERTO_RICO("358", "pr", "Puerto Rico", Res.drawable.flag_pr),
    COUNTRY_ST_KITTS_AND_NEVIS("341", "kn", "St Kitts And Nevis", Res.drawable.flag_kn),
    COUNTRY_ST_LUCIA("343", "lc", "St Lucia", Res.drawable.flag_lc),
    COUNTRY_ST_PIERRE_AND_MIQUELON("361", "pm", "St Pierre And Miquelon", Res.drawable.flag_unknown),
    COUNTRY_ST_VINCENT_AND_THE_GRENADINES_1("375", "vc", "St Vincent And The Grenadines", Res.drawable.flag_vc),
    COUNTRY_ST_VINCENT_AND_THE_GRENADINES_2("376", "vc", "St Vincent And The Grenadines", Res.drawable.flag_vc),
    COUNTRY_ST_VINCENT_AND_THE_GRENADINES_3("377", "vc", "St Vincent And The Grenadines", Res.drawable.flag_vc),
    COUNTRY_TRINIDAD_AND_TOBAGO("362", "tt", "Trinidad And Tobago", Res.drawable.flag_tt),
    COUNTRY_TURKS_AND_CAICOS_ISLANDS("364", "tc", "Turks And Caicos Islands", Res.drawable.flag_tc),
    COUNTRY_UNITED_STATES_1("338", "us", "United States", Res.drawable.flag_us),
    COUNTRY_UNITED_STATES_2("366", "us", "United States", Res.drawable.flag_us),
    COUNTRY_UNITED_STATES_3("367", "us", "United States", Res.drawable.flag_us),
    COUNTRY_UNITED_STATES_4("368", "us", "United States", Res.drawable.flag_us),
    COUNTRY_UNITED_STATES_5("369", "us", "United States", Res.drawable.flag_us),
    COUNTRY_UNITED_STATES_VIRGIN_ISLANDS("379", "vi", "United States Virgin Islands", Res.drawable.flag_vi),
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountryNorthAmerica? {
            return MmsiCountryNorthAmerica.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
