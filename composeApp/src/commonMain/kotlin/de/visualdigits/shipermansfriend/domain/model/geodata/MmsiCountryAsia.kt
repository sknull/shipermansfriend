package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_ae
import de.visualdigits.compose.resources.flag_af
import de.visualdigits.compose.resources.flag_az
import de.visualdigits.compose.resources.flag_bd
import de.visualdigits.compose.resources.flag_bh
import de.visualdigits.compose.resources.flag_bt
import de.visualdigits.compose.resources.flag_cn
import de.visualdigits.compose.resources.flag_hk
import de.visualdigits.compose.resources.flag_il
import de.visualdigits.compose.resources.flag_in
import de.visualdigits.compose.resources.flag_iq
import de.visualdigits.compose.resources.flag_ir
import de.visualdigits.compose.resources.flag_jo
import de.visualdigits.compose.resources.flag_jp
import de.visualdigits.compose.resources.flag_kg
import de.visualdigits.compose.resources.flag_kp
import de.visualdigits.compose.resources.flag_kr
import de.visualdigits.compose.resources.flag_kw
import de.visualdigits.compose.resources.flag_kz
import de.visualdigits.compose.resources.flag_lb
import de.visualdigits.compose.resources.flag_lk
import de.visualdigits.compose.resources.flag_mm
import de.visualdigits.compose.resources.flag_mn
import de.visualdigits.compose.resources.flag_mo
import de.visualdigits.compose.resources.flag_mv
import de.visualdigits.compose.resources.flag_np
import de.visualdigits.compose.resources.flag_om
import de.visualdigits.compose.resources.flag_ph
import de.visualdigits.compose.resources.flag_pk
import de.visualdigits.compose.resources.flag_ps
import de.visualdigits.compose.resources.flag_qa
import de.visualdigits.compose.resources.flag_sa
import de.visualdigits.compose.resources.flag_sy
import de.visualdigits.compose.resources.flag_th
import de.visualdigits.compose.resources.flag_tj
import de.visualdigits.compose.resources.flag_tm
import de.visualdigits.compose.resources.flag_tw
import de.visualdigits.compose.resources.flag_uz
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountryAsia(
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_AFGHANISTAN("401", "af", "Afghanistan", Res.drawable.flag_af),
    COUNTRY_AZERBAIJAN("423", "az", "Azerbaijan", Res.drawable.flag_az),
    COUNTRY_BAHRAIN("408", "bh", "Bahrain", Res.drawable.flag_bh),
    COUNTRY_BANGLADESH("405", "bd", "Bangladesh", Res.drawable.flag_bd),
    COUNTRY_BHUTAN("410", "bt", "Bhutan", Res.drawable.flag_bt),
    COUNTRY_CHINA_1("412", "cn", "China", Res.drawable.flag_cn),
    COUNTRY_CHINA_2("413", "cn", "China", Res.drawable.flag_cn),
    COUNTRY_CHINA_3("414", "cn", "China", Res.drawable.flag_cn),
    COUNTRY_HONG_KONG("477", "hk", "Hong Kong", Res.drawable.flag_hk),
    COUNTRY_INDIA("419", "in", "India", Res.drawable.flag_in),
    COUNTRY_IRAN("422", "ir", "Iran", Res.drawable.flag_ir),
    COUNTRY_IRAQ("425", "iq", "Iraq", Res.drawable.flag_iq),
    COUNTRY_ISRAEL("428", "il", "Israel", Res.drawable.flag_il),
    COUNTRY_JAPAN_1("431", "jp", "Japan", Res.drawable.flag_jp),
    COUNTRY_JAPAN_2("432", "jp", "Japan", Res.drawable.flag_jp),
    COUNTRY_JORDAN("436", "jo", "Jordan", Res.drawable.flag_jo),
    COUNTRY_KAZAKHSTAN("499", "kz", "Kazakhstan", Res.drawable.flag_kz),
    COUNTRY_KUWAIT("443", "kw", "Kuwait", Res.drawable.flag_kw),
    COUNTRY_KYRGYZSTAN("437", "kg", "Kyrgyzstan", Res.drawable.flag_kg),
    COUNTRY_LEBANON("445", "lb", "Lebanon", Res.drawable.flag_lb),
    COUNTRY_MACAU("447", "mo", "Macau", Res.drawable.flag_mo),
    COUNTRY_MALDIVES("450", "mv", "Maldives", Res.drawable.flag_mv),
    COUNTRY_MONGOLIA("451", "mn", "Mongolia", Res.drawable.flag_mn),
    COUNTRY_MYANMAR("453", "mm", "Myanmar", Res.drawable.flag_mm),
    COUNTRY_NEPAL("455", "np", "Nepal", Res.drawable.flag_np),
    COUNTRY_NORTH_KOREA("438", "kp", "North Korea", Res.drawable.flag_kp),
    COUNTRY_OMAN("457", "om", "Oman", Res.drawable.flag_om),
    COUNTRY_PAKISTAN("459", "pk", "Pakistan", Res.drawable.flag_pk),
    COUNTRY_PALESTINE("461", "ps", "Palestine", Res.drawable.flag_ps),
    COUNTRY_PHILIPPINES("463", "ph", "Philippines", Res.drawable.flag_ph),
    COUNTRY_QATAR("466", "qa", "Qatar", Res.drawable.flag_qa),
    COUNTRY_SAUDI_ARABIA("403", "sa", "Saudi Arabia", Res.drawable.flag_sa),
    COUNTRY_SOUTH_KOREA_1("440", "kr", "South Korea", Res.drawable.flag_kr),
    COUNTRY_SOUTH_KOREA_2("441", "kr", "South Korea", Res.drawable.flag_kr),
    COUNTRY_SRI_LANKA("417", "lk", "Sri Lanka", Res.drawable.flag_lk),
    COUNTRY_SYRIA("468", "sy", "Syria", Res.drawable.flag_sy),
    COUNTRY_TAIWAN("416", "tw", "Taiwan", Res.drawable.flag_tw),
    COUNTRY_TAJIKISTAN("472", "tj", "Tajikistan", Res.drawable.flag_tj),
    COUNTRY_THAILAND_1("473", "th", "Thailand", Res.drawable.flag_th),
    COUNTRY_THAILAND_2("475", "th", "Thailand", Res.drawable.flag_th),
    COUNTRY_TURKMENISTAN("434", "tm", "Turkmenistan", Res.drawable.flag_tm),
    COUNTRY_UNITED_ARAB_EMIRATES_1("470", "ae", "United Arab Emirates", Res.drawable.flag_ae),
    COUNTRY_UNITED_ARAB_EMIRATES_2("471", "ae", "United Arab Emirates", Res.drawable.flag_ae),
    COUNTRY_UZBEKISTAN("478", "uz", "Uzbekistan", Res.drawable.flag_uz),
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountryAsia? {
            return MmsiCountryAsia.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
