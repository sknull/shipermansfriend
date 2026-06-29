package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_ao
import de.visualdigits.compose.resources.flag_bf
import de.visualdigits.compose.resources.flag_bi
import de.visualdigits.compose.resources.flag_bj
import de.visualdigits.compose.resources.flag_bw
import de.visualdigits.compose.resources.flag_cd
import de.visualdigits.compose.resources.flag_cf
import de.visualdigits.compose.resources.flag_cg
import de.visualdigits.compose.resources.flag_ci
import de.visualdigits.compose.resources.flag_cm
import de.visualdigits.compose.resources.flag_cv
import de.visualdigits.compose.resources.flag_dz
import de.visualdigits.compose.resources.flag_eg
import de.visualdigits.compose.resources.flag_er
import de.visualdigits.compose.resources.flag_ga
import de.visualdigits.compose.resources.flag_gh
import de.visualdigits.compose.resources.flag_gm
import de.visualdigits.compose.resources.flag_gn
import de.visualdigits.compose.resources.flag_gq
import de.visualdigits.compose.resources.flag_gw
import de.visualdigits.compose.resources.flag_ke
import de.visualdigits.compose.resources.flag_km
import de.visualdigits.compose.resources.flag_lr
import de.visualdigits.compose.resources.flag_ls
import de.visualdigits.compose.resources.flag_ly
import de.visualdigits.compose.resources.flag_ma
import de.visualdigits.compose.resources.flag_mg
import de.visualdigits.compose.resources.flag_ml
import de.visualdigits.compose.resources.flag_mr
import de.visualdigits.compose.resources.flag_mu
import de.visualdigits.compose.resources.flag_mz
import de.visualdigits.compose.resources.flag_na
import de.visualdigits.compose.resources.flag_ne
import de.visualdigits.compose.resources.flag_ng
import de.visualdigits.compose.resources.flag_rw
import de.visualdigits.compose.resources.flag_sc
import de.visualdigits.compose.resources.flag_sd
import de.visualdigits.compose.resources.flag_sh
import de.visualdigits.compose.resources.flag_sl
import de.visualdigits.compose.resources.flag_sn
import de.visualdigits.compose.resources.flag_so
import de.visualdigits.compose.resources.flag_st
import de.visualdigits.compose.resources.flag_sz
import de.visualdigits.compose.resources.flag_tg
import de.visualdigits.compose.resources.flag_tn
import de.visualdigits.compose.resources.flag_tz
import de.visualdigits.compose.resources.flag_ug
import de.visualdigits.compose.resources.flag_unknown
import de.visualdigits.compose.resources.flag_za
import de.visualdigits.compose.resources.flag_zm
import de.visualdigits.compose.resources.flag_zw
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountryAfrica(
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_ALGERIA("605", "dz", "Algeria", Res.drawable.flag_dz),
    COUNTRY_ANGOLA("603", "ao", "Angola", Res.drawable.flag_ao),
    COUNTRY_BENIN("607", "bj", "Benin", Res.drawable.flag_bj),
    COUNTRY_BOTSWANA("608", "bw", "Botswana", Res.drawable.flag_bw),
    COUNTRY_BURKINA_FASO("679", "bf", "Burkina Faso", Res.drawable.flag_bf),
    COUNTRY_BURUNDI("609", "bi", "Burundi", Res.drawable.flag_bi),
    COUNTRY_CAMEROON("610", "cm", "Cameroon", Res.drawable.flag_cm),
    COUNTRY_CAPE_VERDE("611", "cv", "Cape Verde", Res.drawable.flag_cv),
    COUNTRY_CENTRAL_AFRICAN_REPUBLIC("612", "cf", "Central African Republic", Res.drawable.flag_cf),
    COUNTRY_CHAD("670", "td", "Chad", Res.drawable.flag_unknown),
    COUNTRY_COMOROS("613", "km", "Comoros", Res.drawable.flag_km),
    COUNTRY_CONGO("615", "cg", "Congo", Res.drawable.flag_cg),
    COUNTRY_CONGO_DEM_REP("616", "cd", "Congo Dem Rep", Res.drawable.flag_cd),
    COUNTRY_EGYPT("678", "eg", "Egypt", Res.drawable.flag_eg),
    COUNTRY_EQUATORIAL_GUINEA_1("620", "gq", "Equatorial Guinea", Res.drawable.flag_gq),
    COUNTRY_EQUATORIAL_GUINEA_2("631", "gq", "Equatorial Guinea", Res.drawable.flag_gq),
    COUNTRY_ERITREA("621", "er", "Eritrea", Res.drawable.flag_er),
    COUNTRY_ESWATINI("668", "sz", "Eswatini", Res.drawable.flag_sz),
    COUNTRY_ETHIOPIA("622", "et", "Ethiopia", Res.drawable.flag_unknown),
    COUNTRY_GABON_1("618", "ga", "Gabon", Res.drawable.flag_ga),
    COUNTRY_GABON_2("624", "ga", "Gabon", Res.drawable.flag_ga),
    COUNTRY_GAMBIA("625", "gm", "Gambia", Res.drawable.flag_gm),
    COUNTRY_GHANA_1("626", "gh", "Ghana", Res.drawable.flag_gh),
    COUNTRY_GHANA_2("627", "gh", "Ghana", Res.drawable.flag_gh),
    COUNTRY_GUINEA("629", "gn", "Guinea", Res.drawable.flag_gn),
    COUNTRY_GUINEA_BISSAU("630", "gw", "Guinea Bissau", Res.drawable.flag_gw),
    COUNTRY_IVORY_COAST_1("617", "ci", "Ivory Coast", Res.drawable.flag_ci),
    COUNTRY_IVORY_COAST_2("619", "ci", "Ivory Coast", Res.drawable.flag_ci),
    COUNTRY_KENYA("632", "ke", "Kenya", Res.drawable.flag_ke),
    COUNTRY_LESOTHO("633", "ls", "Lesotho", Res.drawable.flag_ls),
    COUNTRY_LIBERIA_1("634", "lr", "Liberia", Res.drawable.flag_lr),
    COUNTRY_LIBERIA_2("635", "lr", "Liberia", Res.drawable.flag_lr),
    COUNTRY_LIBYA_1("636", "ly", "Libya", Res.drawable.flag_ly),
    COUNTRY_LIBYA_2("637", "ly", "Libya", Res.drawable.flag_ly),
    COUNTRY_MADAGASCAR_1("638", "mg", "Madagascar", Res.drawable.flag_mg),
    COUNTRY_MADAGASCAR_2("642", "mg", "Madagascar", Res.drawable.flag_mg),
    COUNTRY_MALI("644", "ml", "Mali", Res.drawable.flag_ml),
    COUNTRY_MAURITANIA("645", "mr", "Mauritania", Res.drawable.flag_mr),
    COUNTRY_MAURITIUS("647", "mu", "Mauritius", Res.drawable.flag_mu),
    COUNTRY_MOROCCO("649", "ma", "Morocco", Res.drawable.flag_ma),
    COUNTRY_MOZAMBIQUE("650", "mz", "Mozambique", Res.drawable.flag_mz),
    COUNTRY_NAMIBIA("654", "na", "Namibia", Res.drawable.flag_na),
    COUNTRY_NIGER("655", "ne", "Niger", Res.drawable.flag_ne),
    COUNTRY_NIGERIA_1("656", "ng", "Nigeria", Res.drawable.flag_ng),
    COUNTRY_NIGERIA_2("657", "ng", "Nigeria", Res.drawable.flag_ng),
    COUNTRY_RWANDA("660", "rw", "Rwanda", Res.drawable.flag_rw),
    COUNTRY_RÉUNION("659", "re", "Réunion", Res.drawable.flag_unknown),
    COUNTRY_SENEGAL("663", "sn", "Senegal", Res.drawable.flag_sn),
    COUNTRY_SEYCHELLES("664", "sc", "Seychelles", Res.drawable.flag_sc),
    COUNTRY_SIERRA_LEONE("665", "sl", "Sierra Leone", Res.drawable.flag_sl),
    COUNTRY_SOMALIA("666", "so", "Somalia", Res.drawable.flag_so),
    COUNTRY_SOUTH_AFRICAN("601", "za", "South African", Res.drawable.flag_za),
    COUNTRY_ST_HELENA("661", "sh", "St Helena", Res.drawable.flag_sh),
    COUNTRY_SUDAN("667", "sd", "Sudan", Res.drawable.flag_sd),
    COUNTRY_SÃO_TOMÉ_AND_PRÍNCIPE("662", "st", "São Tomé And Príncipe", Res.drawable.flag_st),
    COUNTRY_TANZANIA_1("669", "tz", "Tanzania", Res.drawable.flag_tz),
    COUNTRY_TANZANIA_2("677", "tz", "Tanzania", Res.drawable.flag_tz),
    COUNTRY_TOGO("671", "tg", "Togo", Res.drawable.flag_tg),
    COUNTRY_TUNISIA("672", "tn", "Tunisia", Res.drawable.flag_tn),
    COUNTRY_UGANDA("674", "ug", "Uganda", Res.drawable.flag_ug),
    COUNTRY_ZAMBIA("675", "zm", "Zambia", Res.drawable.flag_zm),
    COUNTRY_ZIMBABWE("676", "zw", "Zimbabwe", Res.drawable.flag_zw),
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountryAfrica? {
            return MmsiCountryAfrica.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
