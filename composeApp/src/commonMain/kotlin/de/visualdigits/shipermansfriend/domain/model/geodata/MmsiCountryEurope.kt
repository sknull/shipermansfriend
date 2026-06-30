package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_ad
import de.visualdigits.compose.resources.flag_al
import de.visualdigits.compose.resources.flag_am
import de.visualdigits.compose.resources.flag_at
import de.visualdigits.compose.resources.flag_be
import de.visualdigits.compose.resources.flag_bg
import de.visualdigits.compose.resources.flag_by
import de.visualdigits.compose.resources.flag_cy
import de.visualdigits.compose.resources.flag_cz
import de.visualdigits.compose.resources.flag_de
import de.visualdigits.compose.resources.flag_dk
import de.visualdigits.compose.resources.flag_ee
import de.visualdigits.compose.resources.flag_es
import de.visualdigits.compose.resources.flag_fi
import de.visualdigits.compose.resources.flag_fo
import de.visualdigits.compose.resources.flag_fr
import de.visualdigits.compose.resources.flag_gb
import de.visualdigits.compose.resources.flag_ge
import de.visualdigits.compose.resources.flag_gi
import de.visualdigits.compose.resources.flag_gr
import de.visualdigits.compose.resources.flag_hr
import de.visualdigits.compose.resources.flag_hu
import de.visualdigits.compose.resources.flag_ie
import de.visualdigits.compose.resources.flag_is
import de.visualdigits.compose.resources.flag_it
import de.visualdigits.compose.resources.flag_li
import de.visualdigits.compose.resources.flag_lu
import de.visualdigits.compose.resources.flag_lv
import de.visualdigits.compose.resources.flag_ma
import de.visualdigits.compose.resources.flag_mc
import de.visualdigits.compose.resources.flag_md
import de.visualdigits.compose.resources.flag_me
import de.visualdigits.compose.resources.flag_mk
import de.visualdigits.compose.resources.flag_mt
import de.visualdigits.compose.resources.flag_nl
import de.visualdigits.compose.resources.flag_no
import de.visualdigits.compose.resources.flag_pl
import de.visualdigits.compose.resources.flag_pt
import de.visualdigits.compose.resources.flag_ro
import de.visualdigits.compose.resources.flag_rs
import de.visualdigits.compose.resources.flag_ru
import de.visualdigits.compose.resources.flag_se
import de.visualdigits.compose.resources.flag_si
import de.visualdigits.compose.resources.flag_sk
import de.visualdigits.compose.resources.flag_sm
import de.visualdigits.compose.resources.flag_tr
import de.visualdigits.compose.resources.flag_ua
import de.visualdigits.compose.resources.flag_unknown
import de.visualdigits.compose.resources.flag_va
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountryEurope(
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_ALBANIA("201", "al", "Albania", Res.drawable.flag_al),
    COUNTRY_ANDORRA("202", "ad", "Andorra", Res.drawable.flag_ad),
    COUNTRY_ARMENIA("216", "am", "Armenia", Res.drawable.flag_am),
    COUNTRY_AUSTRIA("203", "at", "Austria", Res.drawable.flag_at),
    COUNTRY_AZORES("204", "pt", "Azores", Res.drawable.flag_pt),
    COUNTRY_BELARUS("206", "by", "Belarus", Res.drawable.flag_by),
    COUNTRY_BELGIUM("205", "be", "Belgium", Res.drawable.flag_be),
    COUNTRY_BULGARIA("207", "bg", "Bulgaria", Res.drawable.flag_bg),
    COUNTRY_CROATIA("238", "hr", "Croatia", Res.drawable.flag_hr),
    COUNTRY_CYPRUS("212", "cy", "Cyprus", Res.drawable.flag_cy),
    COUNTRY_CYPRUS_1("209", "cy", "Cyprus", Res.drawable.flag_cy),
    COUNTRY_CYPRUS_2("210", "cy", "Cyprus", Res.drawable.flag_cy),
    COUNTRY_CZECH_REPUBLIC("270", "cz", "Czech Republic", Res.drawable.flag_cz),
    COUNTRY_DENMARK_1("219", "dk", "Denmark", Res.drawable.flag_dk),
    COUNTRY_DENMARK_2("220", "dk", "Denmark", Res.drawable.flag_dk),
    COUNTRY_ESTONIA("276", "ee", "Estonia", Res.drawable.flag_ee),
    COUNTRY_FAROE_ISLANDS("231", "fo", "Faroe Islands", Res.drawable.flag_fo),
    COUNTRY_FINLAND("230", "fi", "Finland", Res.drawable.flag_fi),
    COUNTRY_FRANCE_1("226", "fr", "France", Res.drawable.flag_fr),
    COUNTRY_FRANCE_2("227", "fr", "France", Res.drawable.flag_fr),
    COUNTRY_FRANCE_3("228", "fr", "France", Res.drawable.flag_fr),
    COUNTRY_GEORGIA("213", "ge", "Georgia", Res.drawable.flag_ge),
    COUNTRY_GERMANY_1("211", "de", "Germany", Res.drawable.flag_de),
    COUNTRY_GERMANY_2("218", "de", "Germany", Res.drawable.flag_de),
    COUNTRY_GIBRALTAR("236", "gi", "Gibraltar", Res.drawable.flag_gi),
    COUNTRY_GREECE_1("237", "gr", "Greece", Res.drawable.flag_gr),
    COUNTRY_GREECE_2("239", "gr", "Greece", Res.drawable.flag_gr),
    COUNTRY_GREECE_3("240", "gr", "Greece", Res.drawable.flag_gr),
    COUNTRY_GREECE_4("241", "gr", "Greece", Res.drawable.flag_gr),
    COUNTRY_HUNGARY("243", "hu", "Hungary", Res.drawable.flag_hu),
    COUNTRY_ICELAND("251", "is", "Iceland", Res.drawable.flag_is),
    COUNTRY_IRELAND("250", "ie", "Ireland", Res.drawable.flag_ie),
    COUNTRY_ITALY("247", "it", "Italy", Res.drawable.flag_it),
    COUNTRY_LATVIA("275", "lv", "Latvia", Res.drawable.flag_lv),
    COUNTRY_LIECHTENSTEIN("252", "li", "Liechtenstein", Res.drawable.flag_li),
    COUNTRY_LITHUANIA("277", "lt", "Lithuania", Res.drawable.flag_unknown),
    COUNTRY_LUXEMBOURG("253", "lu", "Luxembourg", Res.drawable.flag_lu),
    COUNTRY_MADEIRA("255", "pt", "Madeira", Res.drawable.flag_pt),
    COUNTRY_MACEDONIA("274", "mk", "Macedonia", Res.drawable.flag_mk),
    COUNTRY_MALTA_1("215", "mt", "Malta", Res.drawable.flag_mt),
    COUNTRY_MALTA_2("229", "mt", "Malta", Res.drawable.flag_mt),
    COUNTRY_MALTA_3("248", "mt", "Malta", Res.drawable.flag_mt),
    COUNTRY_MALTA_4("249", "mt", "Malta", Res.drawable.flag_mt),
    COUNTRY_MALTA_5("256", "mt", "Malta", Res.drawable.flag_mt),
    COUNTRY_MOLDOVA("214", "md", "Moldova", Res.drawable.flag_md),
    COUNTRY_MONACO("254", "mc", "Monaco", Res.drawable.flag_mc),
    COUNTRY_MONTENEGRO("262", "me", "Montenegro", Res.drawable.flag_me),
    COUNTRY_MOROCCO("242", "ma", "Morocco", Res.drawable.flag_ma),
    COUNTRY_NETHERLANDS_1("244", "nl", "Netherlands", Res.drawable.flag_nl),
    COUNTRY_NETHERLANDS_2("245", "nl", "Netherlands", Res.drawable.flag_nl),
    COUNTRY_NETHERLANDS_3("246", "nl", "Netherlands", Res.drawable.flag_nl),
    COUNTRY_POLAND("261", "pl", "Poland", Res.drawable.flag_pl),
    COUNTRY_NORWAY_1("257", "no", "Norway", Res.drawable.flag_no),
    COUNTRY_NORWAY_2("258", "no", "Norway", Res.drawable.flag_no),
    COUNTRY_NORWAY_3("259", "no", "Norway", Res.drawable.flag_no),
    COUNTRY_PORTUGAL("263", "pt", "Portugal", Res.drawable.flag_pt),
    COUNTRY_ROMANIA("264", "ro", "Romania", Res.drawable.flag_ro),
    COUNTRY_RUSSIA("273", "ru", "Russia", Res.drawable.flag_ru),
    COUNTRY_SAN_MARINO("268", "sm", "San Marino", Res.drawable.flag_sm),
    COUNTRY_SERBIA("279", "rs", "Serbia", Res.drawable.flag_rs),
    COUNTRY_SLOVAKIA("267", "sk", "Slovakia", Res.drawable.flag_sk),
    COUNTRY_SLOVENIA("278", "si", "Slovenia", Res.drawable.flag_si),
    COUNTRY_SPAIN_1("224", "es", "Spain", Res.drawable.flag_es),
    COUNTRY_SPAIN_2("225", "es", "Spain", Res.drawable.flag_es),
    COUNTRY_SWEDEN_1("265", "se", "Sweden", Res.drawable.flag_se),
    COUNTRY_SWEDEN_2("266", "se", "Sweden", Res.drawable.flag_se),
    COUNTRY_TURKEY("271", "tr", "Turkey", Res.drawable.flag_tr),
    COUNTRY_UKRAINE("272", "ua", "Ukraine", Res.drawable.flag_ua),
    COUNTRY_UNITED_KINGDOM_1("232", "gb", "United Kingdom", Res.drawable.flag_gb),
    COUNTRY_UNITED_KINGDOM_2("233", "gb", "United Kingdom", Res.drawable.flag_gb),
    COUNTRY_UNITED_KINGDOM_3("234", "gb", "United Kingdom", Res.drawable.flag_gb),
    COUNTRY_UNITED_KINGDOM_4("235", "gb", "United Kingdom", Res.drawable.flag_gb),
    COUNTRY_VATICAN_CITY("208", "va", "Vatican City", Res.drawable.flag_va),

    COUNTRY_UNKNOWN("999", "unknown", "Unknown", Res.drawable.flag_unknown)
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountryEurope? {
            return MmsiCountryEurope.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
