package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_as
import de.visualdigits.compose.resources.flag_au
import de.visualdigits.compose.resources.flag_bn
import de.visualdigits.compose.resources.flag_cc
import de.visualdigits.compose.resources.flag_ck
import de.visualdigits.compose.resources.flag_cx
import de.visualdigits.compose.resources.flag_fj
import de.visualdigits.compose.resources.flag_fm
import de.visualdigits.compose.resources.flag_id
import de.visualdigits.compose.resources.flag_kh
import de.visualdigits.compose.resources.flag_ki
import de.visualdigits.compose.resources.flag_la
import de.visualdigits.compose.resources.flag_mh
import de.visualdigits.compose.resources.flag_mp
import de.visualdigits.compose.resources.flag_my
import de.visualdigits.compose.resources.flag_nr
import de.visualdigits.compose.resources.flag_nu
import de.visualdigits.compose.resources.flag_nz
import de.visualdigits.compose.resources.flag_pg
import de.visualdigits.compose.resources.flag_pn
import de.visualdigits.compose.resources.flag_pw
import de.visualdigits.compose.resources.flag_sb
import de.visualdigits.compose.resources.flag_sg
import de.visualdigits.compose.resources.flag_to
import de.visualdigits.compose.resources.flag_tv
import de.visualdigits.compose.resources.flag_vn
import de.visualdigits.compose.resources.flag_vu
import de.visualdigits.compose.resources.flag_wf
import de.visualdigits.compose.resources.flag_ws
import org.jetbrains.compose.resources.DrawableResource

enum class MmsiCountryOceania (
    override val prefix: String,
    override val countryCode: String,
    override val countryName: String,
    override val flag: DrawableResource
) : MmsiCountry {
    COUNTRY_AMERICAN_SAMOA("559", "as", "American Samoa", Res.drawable.flag_as),
    COUNTRY_AUSTRALIA_1("501", "au", "Australia", Res.drawable.flag_au),
    COUNTRY_AUSTRALIA_2("503", "au", "Australia", Res.drawable.flag_au),
    COUNTRY_BRUNEI_1("506", "bn", "Brunei", Res.drawable.flag_bn),
    COUNTRY_BRUNEI_2("508", "bn", "Brunei", Res.drawable.flag_bn),
    COUNTRY_CAMBODIA_1("514", "kh", "Cambodia", Res.drawable.flag_kh),
    COUNTRY_CAMBODIA_2("515", "kh", "Cambodia", Res.drawable.flag_kh),
    COUNTRY_CHRISTMAS_ISLAND("516", "cx", "Christmas Island", Res.drawable.flag_cx),
    COUNTRY_COCOS_KEELING_ISLANDS("518", "cc", "Cocos Keeling Islands", Res.drawable.flag_cc),
    COUNTRY_COOK_ISLANDS("520", "ck", "Cook Islands", Res.drawable.flag_ck),
    COUNTRY_FIJI("523", "fj", "Fiji", Res.drawable.flag_fj),
    COUNTRY_INDONESIA("525", "id", "Indonesia", Res.drawable.flag_id),
    COUNTRY_KIRIBATI("529", "ki", "Kiribati", Res.drawable.flag_ki),
    COUNTRY_LAOS("533", "la", "Laos", Res.drawable.flag_la),
    COUNTRY_MALAYSIA("536", "my", "Malaysia", Res.drawable.flag_my),
    COUNTRY_MARSHALL_ISLANDS("538", "mh", "Marshall Islands", Res.drawable.flag_mh),
    COUNTRY_MICRONESIA("540", "fm", "Micronesia", Res.drawable.flag_fm),
    COUNTRY_NAURU("542", "nr", "Nauru", Res.drawable.flag_nr),
    COUNTRY_NEW_ZEALAND("512", "nz", "New Zealand", Res.drawable.flag_nz),
    COUNTRY_NIUE("544", "nu", "Niue", Res.drawable.flag_nu),
    COUNTRY_NORTHERN_MARIANA_ISLANDS("546", "mp", "Northern Mariana Islands", Res.drawable.flag_mp),
    COUNTRY_PALAU("548", "pw", "Palau", Res.drawable.flag_pw),
    COUNTRY_PAPUA_NEW_GUINEA("553", "pg", "Papua New Guinea", Res.drawable.flag_pg),
    COUNTRY_PITCAIRN_ISLANDS("555", "pn", "Pitcairn Islands", Res.drawable.flag_pn),
    COUNTRY_SAMOA("557", "ws", "Samoa", Res.drawable.flag_ws),
    COUNTRY_SINGAPORE_1("561", "sg", "Singapore", Res.drawable.flag_sg),
    COUNTRY_SINGAPORE_2("563", "sg", "Singapore", Res.drawable.flag_sg),
    COUNTRY_SINGAPORE_3("564", "sg", "Singapore", Res.drawable.flag_sg),
    COUNTRY_SINGAPORE_4("565", "sg", "Singapore", Res.drawable.flag_sg),
    COUNTRY_SINGAPORE_5("566", "sg", "Singapore", Res.drawable.flag_sg),
    COUNTRY_SOLOMON_ISLANDS_1("511", "sb", "Solomon Islands", Res.drawable.flag_sb),
    COUNTRY_SOLOMON_ISLANDS_2("567", "sb", "Solomon Islands", Res.drawable.flag_sb),
    COUNTRY_TONGA("570", "to", "Tonga", Res.drawable.flag_to),
    COUNTRY_TUVALU("572", "tv", "Tuvalu", Res.drawable.flag_tv),
    COUNTRY_VANUATU_1("576", "vu", "Vanuatu", Res.drawable.flag_vu),
    COUNTRY_VANUATU_2("577", "vu", "Vanuatu", Res.drawable.flag_vu),
    COUNTRY_VIETNAM("574", "vn", "Vietnam", Res.drawable.flag_vn),
    COUNTRY_WALLIS_AND_FUTUNA("578", "wf", "Wallis And Futuna", Res.drawable.flag_wf),
    ;

    companion object {

        fun fromMid(mid: String): MmsiCountryOceania? {
            return MmsiCountryOceania.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> mid.startsWith(prefix.prefix) }
        }
    }
}
