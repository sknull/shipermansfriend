package de.visualdigits.shipermansfriend.data.model

import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountryAfrica
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountryAsia
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountryEurope
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountryNorthAmerica
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountryOceania
import de.visualdigits.shipermansfriend.domain.model.geodata.MmsiCountrySouthAmerica
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

@Disabled("Only for local maintenance")
class RenameFlags {

    @Test
    fun cleanupUnused() {
        val rootDirectory = File("E:\\Programmierung\\IntelliJ\\shipermansfriend\\composeApp\\src\\commonMain\\composeResources\\drawable")
        val flagFiles = rootDirectory
            .listFiles { file -> file.isFile && file.name.startsWith("flag_") }
            .map { it.name }.toSet()

        val used = listOf(
            MmsiCountryEurope.entries.map { it.countryCode },
            MmsiCountryNorthAmerica.entries.map { it.countryCode },
            MmsiCountryAsia.entries.map { it.countryCode },
            MmsiCountryOceania.entries.map { it.countryCode },
            MmsiCountryAfrica.entries.map { it.countryCode },
            MmsiCountrySouthAmerica.entries.map { it.countryCode }
        ).flatten().map { "flag_$it.png" }.toSet() + setOf("flag_unknown.png")

        val unused = flagFiles - used
        unused.forEach { name ->
            println(name)
//            File(rootDirectory, name).delete()
        }
    }

    @Test
    fun generateCode1() {
        // import de.visualdigits.compose.resources.flag_en
        // Res.drawable.flag_en
        MmsiCountryEurope.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountryEurope.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))

        MmsiCountryNorthAmerica.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountryNorthAmerica.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))

        MmsiCountryAsia.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountryAsia.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))

        MmsiCountryOceania.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountryOceania.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))

        MmsiCountryAfrica.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountryAfrica.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))

        MmsiCountrySouthAmerica.entries.forEach { country ->
            println("import de.visualdigits.compose.resources.flag_${country.countryCode}")
        }
        println()
        MmsiCountrySouthAmerica.entries.forEach { country ->
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", Res.drawable.flag_${country.countryCode}),")
        }
        println("-".repeat(40))
    }
    @Test
    fun generateCode2() {
        val rootDirectory = File("E:\\Programmierung\\IntelliJ\\shipermansfriend\\composeApp\\src\\commonMain\\composeResources\\drawable")
        val flagFiles = rootDirectory
            .listFiles { file -> file.isFile && file.name.startsWith("flag_") }
            .map { it.name }.toSet()

        MmsiCountryEurope.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))

        MmsiCountryNorthAmerica.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))

        MmsiCountryAsia.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))

        MmsiCountryOceania.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))

        MmsiCountryAfrica.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))

        MmsiCountrySouthAmerica.entries.forEach { country ->
            val countryName = country.name.countryName()
            val flag = if (flagFiles.contains("flag_${country.countryCode}.png")) country.countryCode else "unknown"
            println("${country.name}(\"${country.prefix}\", \"${country.countryCode}\", \"$countryName\", Res.drawable.flag_$flag),")
        }
        println("-".repeat(40))
    }

    private fun String.countryName(): String {
        return this
            .removePrefix("COUNTRY_")
            .replace("_\\d+".toRegex(), "")
            .split("_").joinToString(" ") {
                it.first() + it.drop(1).lowercase()
            }
    }

    @Test
    fun renameFlags() {
        val rootDirectory = File("E:\\Bilder\\Flags\\flag-icons")

        File(rootDirectory, "Abkhaz_flags_flag_9333.png").renameTo(File(rootDirectory, "flag_ge_ab.png")) // Abchasien
        File(rootDirectory, "afganistan_flags_flag_9334.png").renameTo(File(rootDirectory, "flag_af.png"))
        File(rootDirectory, "aland_flags_flag_9335.png").renameTo(File(rootDirectory, "flag_ax.png"))
        File(rootDirectory, "albania_flags_flag_9332.png").renameTo(File(rootDirectory, "flag_al.png"))
        File(rootDirectory, "Algeria_flags_flag_9330.png").renameTo(File(rootDirectory, "flag_dz.png"))
        File(rootDirectory, "Andorra_flags_flag_9329.png").renameTo(File(rootDirectory, "flag_ad.png"))
        File(rootDirectory, "angolaflag_flags_angola_9328.png").renameTo(File(rootDirectory, "flag_ao.png"))
        File(rootDirectory, "antartida_flags_flag_9326.png").renameTo(File(rootDirectory, "flag_aq.png"))
        File(rootDirectory, "antiguaandbarbudaflag_flags_antiguaybarbuda_9324.png").renameTo(File(rootDirectory, "flag_ag.png"))
        File(rootDirectory, "Argentina_flags_flag_9325.png").renameTo(File(rootDirectory, "flag_ar.png"))
        File(rootDirectory, "Armenianflag_flags_armeni_9322.png").renameTo(File(rootDirectory, "flag_am.png"))
        File(rootDirectory, "aruba_flags_flag_9323.png").renameTo(File(rootDirectory, "flag_aw.png"))
        File(rootDirectory, "australia_flags_flag_9321.png").renameTo(File(rootDirectory, "flag_au.png"))
        File(rootDirectory, "austriaflag_flags_austri_9319.png").renameTo(File(rootDirectory, "flag_at.png"))
        File(rootDirectory, "azerbaijanflag_flags_azerbaijan_9320.png").renameTo(File(rootDirectory, "flag_az.png"))
        File(rootDirectory, "bahamasflag_flags_bahamas_9317.png").renameTo(File(rootDirectory, "flag_bs.png"))
        File(rootDirectory, "bahrain_flags_flag_9318.png").renameTo(File(rootDirectory, "flag_bh.png"))
        File(rootDirectory, "bangladeshflag_flags_bangladesh_9316.png").renameTo(File(rootDirectory, "flag_bd.png"))
        File(rootDirectory, "Barbados_flags_flag_9314.png").renameTo(File(rootDirectory, "flag_bb.png"))
        File(rootDirectory, "Basquecountry_flags_flag_9313.png").renameTo(File(rootDirectory, "flag_es_pv.png")) // Baskenland
        File(rootDirectory, "belarusflag_flags_bielorrusia_9315.png").renameTo(File(rootDirectory, "flag_by.png"))
        File(rootDirectory, "belgiumflag_flags_belgic_9311.png").renameTo(File(rootDirectory, "flag_be.png"))
        File(rootDirectory, "Belize_flags_flag_9312.png").renameTo(File(rootDirectory, "flag_bz.png"))
        File(rootDirectory, "benin_flags_flag_9310.png").renameTo(File(rootDirectory, "flag_bj.png"))
        File(rootDirectory, "bermuda_flags_flag_9308.png").renameTo(File(rootDirectory, "flag_bm.png"))
        File(rootDirectory, "Bhutan_flags_flag_9309.png").renameTo(File(rootDirectory, "flag_bt.png"))
        File(rootDirectory, "Bolivia_flags_flag_9306.png").renameTo(File(rootDirectory, "flag_bo.png"))
        File(rootDirectory, "BosniaandHerzegovina_flags_flag_9307.png").renameTo(File(rootDirectory, "flag_ba.png"))
        File(rootDirectory, "botswana_flags_flag_9305.png").renameTo(File(rootDirectory, "flag_bw.png"))
        File(rootDirectory, "brazil_flags_flag_9303.png").renameTo(File(rootDirectory, "flag_br.png"))
        File(rootDirectory, "BritishAntarcticTerritory_flag__9304.png").renameTo(File(rootDirectory, "flag_bat.png"))
        File(rootDirectory, "BritishVirginIslands_flags_flag_9302.png").renameTo(File(rootDirectory, "flag_vg.png"))
        File(rootDirectory, "brunei_flags_flag_9299.png").renameTo(File(rootDirectory, "flag_bn.png"))
        File(rootDirectory, "bulgariaflag_flags_bulgaria_9301.png").renameTo(File(rootDirectory, "flag_bg.png"))
        File(rootDirectory, "burkinafaso_flags_flag_9300.png").renameTo(File(rootDirectory, "flag_bf.png"))
        File(rootDirectory, "burundi_flags_flag_9297.png").renameTo(File(rootDirectory, "flag_bi.png"))
        File(rootDirectory, "cambodia_flags_flag_9298.png").renameTo(File(rootDirectory, "flag_kh.png"))
        File(rootDirectory, "camerun_flags_flag_9295.png").renameTo(File(rootDirectory, "flag_cm.png"))
        File(rootDirectory, "canadaflag_flags_canad_9294.png").renameTo(File(rootDirectory, "flag_ca.png"))
        File(rootDirectory, "Canary_flags_flag_9296.png").renameTo(File(rootDirectory, "flag_ic.png")) // Kanarische Inseln
        File(rootDirectory, "CapeVerde_flags_flag_9291.png").renameTo(File(rootDirectory, "flag_cv.png"))
        File(rootDirectory, "caymanislands_flags_flag_9293.png").renameTo(File(rootDirectory, "flag_ky.png"))
        File(rootDirectory, "CentralAfricanRepublic_flags_flag_9292.png").renameTo(File(rootDirectory, "flag_cf.png"))
        File(rootDirectory, "Chile_flags_flag_9289.png").renameTo(File(rootDirectory, "flag_cl.png"))
        File(rootDirectory, "china_flags_flag_9288.png").renameTo(File(rootDirectory, "flag_cn.png"))
        File(rootDirectory, "christmasislandflag_flags_isladenavidad_9286.png").renameTo(File(rootDirectory, "flag_cx.png"))
        File(rootDirectory, "Colombia_flags_flag_9283.png").renameTo(File(rootDirectory, "flag_co.png"))
        File(rootDirectory, "Comoros_flags_flag_9284.png").renameTo(File(rootDirectory, "flag_km.png"))
        File(rootDirectory, "CookIslands_theflags_theflag_9281.png").renameTo(File(rootDirectory, "flag_ck.png"))
        File(rootDirectory, "CostaRica_flags_flag_9282.png").renameTo(File(rootDirectory, "flag_cr.png"))
        File(rootDirectory, "cotedivoire_flags_flag_9280.png").renameTo(File(rootDirectory, "flag_ci.png"))
        File(rootDirectory, "Croatia_flags_flag_9278.png").renameTo(File(rootDirectory, "flag_hr.png"))
        File(rootDirectory, "Cuba_flags_flag_9277.png").renameTo(File(rootDirectory, "flag_cu.png"))
        File(rootDirectory, "curacao_flags_flag_9279.png").renameTo(File(rootDirectory, "flag_cw.png"))
        File(rootDirectory, "cyprusflag_flags_chipre_9275.png").renameTo(File(rootDirectory, "flag_cy.png"))
        File(rootDirectory, "czechrepublicflag_flags_republicachec_9274.png").renameTo(File(rootDirectory, "flag_cz.png"))
        File(rootDirectory, "democraticrepublicofcongoflag_flags_republicademocraticadelcongo_9276.png").renameTo(File(rootDirectory, "flag_cd.png"))
        File(rootDirectory, "denmark_flags_flag_9272.png").renameTo(File(rootDirectory, "flag_dk.png"))
        File(rootDirectory, "Djibouti_flags_flag_9271.png").renameTo(File(rootDirectory, "flag_dj.png"))
        File(rootDirectory, "DominicanRepublicflag_flags_RepublicaDominicana_9269.png").renameTo(File(rootDirectory, "flag_do.png"))
        File(rootDirectory, "dominica_flags_flag_9273.png").renameTo(File(rootDirectory, "flag_dm.png"))
        File(rootDirectory, "Ecuador_flags_flag_9268.png").renameTo(File(rootDirectory, "flag_ec.png"))
        File(rootDirectory, "eel_flags_flag_9327.png").renameTo(File(rootDirectory, "flag_ee.png")) // Estland
        File(rootDirectory, "egypt_flag_flags_9266.png").renameTo(File(rootDirectory, "flag_eg.png"))
        File(rootDirectory, "ElSalvador_flags_flag_9267.png").renameTo(File(rootDirectory, "flag_sv.png"))
        File(rootDirectory, "england_flags_flag_9264.png").renameTo(File(rootDirectory, "flag_gb_eng.png"))
        File(rootDirectory, "EquatorialGuinea_flags_flag_9263.png").renameTo(File(rootDirectory, "flag_gq.png"))
        File(rootDirectory, "Eritreanflag_flags_eritre_9265.png").renameTo(File(rootDirectory, "flag_er.png"))
        File(rootDirectory, "Estonianflags_flag_estoni_9262.png").renameTo(File(rootDirectory, "flag_ee.png"))
        File(rootDirectory, "etiquette_flags_flag_9261.png").renameTo(File(rootDirectory, "flag_etiquette.png")) // Signalwimpel/Etikette
        File(rootDirectory, "europeanunionflags_flag_unioneurope_9258.png").renameTo(File(rootDirectory, "flag_eu.png"))
        File(rootDirectory, "FalklandIslands_flags_flag_9260.png").renameTo(File(rootDirectory, "flag_fk.png"))
        File(rootDirectory, "faroes_flags_flag_9259.png").renameTo(File(rootDirectory, "flag_fo.png"))
        File(rootDirectory, "Fiji_flags_flag_9257.png").renameTo(File(rootDirectory, "flag_fj.png"))
        File(rootDirectory, "finland_flags_flag_9256.png").renameTo(File(rootDirectory, "flag_fi.png"))
        File(rootDirectory, "france_flags_flag_9255.png").renameTo(File(rootDirectory, "flag_fr.png"))
        File(rootDirectory, "FrenchPolynesia_flags_flag_9253.png").renameTo(File(rootDirectory, "flag_pf.png"))
        File(rootDirectory, "FrenchSouthernTerritories_flags_flag_9254.png").renameTo(File(rootDirectory, "flag_tf.png"))
        File(rootDirectory, "gabon_flags_flag_9250.png").renameTo(File(rootDirectory, "flag_ga.png"))
        File(rootDirectory, "gambia_flags_flag_9252.png").renameTo(File(rootDirectory, "flag_gm.png"))
        File(rootDirectory, "georgia_flags_flag_9251.png").renameTo(File(rootDirectory, "flag_ge.png"))
        File(rootDirectory, "germany_flags_flag_9248.png").renameTo(File(rootDirectory, "flag_de.png"))
        File(rootDirectory, "ghanaflag_flags_ghana_9249.png").renameTo(File(rootDirectory, "flag_gh.png"))
        File(rootDirectory, "gibraltar_flags_flag_9247.png").renameTo(File(rootDirectory, "flag_gi.png"))
        File(rootDirectory, "Granada_flags_flag_9243.png").renameTo(File(rootDirectory, "flag_gd.png"))
        File(rootDirectory, "greece_flags_flag_9246.png").renameTo(File(rootDirectory, "flag_gr.png"))
        File(rootDirectory, "greenlandflag_flags_groenlandia_9244.png").renameTo(File(rootDirectory, "flag_gl.png"))
        File(rootDirectory, "guam_flags_flag_9242.png").renameTo(File(rootDirectory, "flag_gu.png"))
        File(rootDirectory, "Guatemala_flags_flag_9239.png").renameTo(File(rootDirectory, "flag_gt.png"))
        File(rootDirectory, "guernsey_flags_flag_9241.png").renameTo(File(rootDirectory, "flag_gg.png"))
        File(rootDirectory, "guineabissauflag_flags_guineabissau_9237.png").renameTo(File(rootDirectory, "flag_gw.png"))
        File(rootDirectory, "guinea_flag_flags_9240.png").renameTo(File(rootDirectory, "flag_gn.png"))
        File(rootDirectory, "guyanaflag_flags_guyana_9238.png").renameTo(File(rootDirectory, "flag_gy.png"))
        File(rootDirectory, "haiti_flags_flag_9236.png").renameTo(File(rootDirectory, "flag_ht.png"))
        File(rootDirectory, "Honduras_flags_flag_9235.png").renameTo(File(rootDirectory, "flag_hn.png"))
        File(rootDirectory, "hongkong_flags_flag_9234.png").renameTo(File(rootDirectory, "flag_hk.png"))
        File(rootDirectory, "hungary_flags_flag_9231.png").renameTo(File(rootDirectory, "flag_hu.png"))
        File(rootDirectory, "icelandflag_flags_islandia_9232.png").renameTo(File(rootDirectory, "flag_is.png"))
        File(rootDirectory, "india_flags_flag_9233.png").renameTo(File(rootDirectory, "flag_in.png"))
        File(rootDirectory, "indonesia_flags_flag_9229.png").renameTo(File(rootDirectory, "flag_id.png"))
        File(rootDirectory, "iran_flags_flag_9230.png").renameTo(File(rootDirectory, "flag_ir.png"))
        File(rootDirectory, "iraqflag_flags_iraq_9227.png").renameTo(File(rootDirectory, "flag_iq.png"))
        File(rootDirectory, "ireland_flags_flag_9226.png").renameTo(File(rootDirectory, "flag_ie.png"))
        File(rootDirectory, "IsleofMan_flags_flag_9228.png").renameTo(File(rootDirectory, "flag_im.png"))
        File(rootDirectory, "israel_flags_flag_9224.png").renameTo(File(rootDirectory, "flag_il.png"))
        File(rootDirectory, "italy_flags_flag_9225.png").renameTo(File(rootDirectory, "flag_it.png"))
        File(rootDirectory, "jamaicaflag_flags_jamaica_9223.png").renameTo(File(rootDirectory, "flag_jm.png"))
        File(rootDirectory, "japanflag_flags_japo_9220.png").renameTo(File(rootDirectory, "flag_jp.png"))
        File(rootDirectory, "jersey_flags_flag_9222.png").renameTo(File(rootDirectory, "flag_je.png"))
        File(rootDirectory, "jordan_flags_flag_9221.png").renameTo(File(rootDirectory, "flag_jo.png"))
        File(rootDirectory, "Kazakhstanflags__kazajistan_9219.png").renameTo(File(rootDirectory, "flag_kz.png"))
        File(rootDirectory, "KeelingIslandscoconuts_flags_flag_9287.png").renameTo(File(rootDirectory, "flag_cc.png"))
        File(rootDirectory, "kenya_flags_flag_9218.png").renameTo(File(rootDirectory, "flag_ke.png"))
        File(rootDirectory, "Kiribati_flags_flag_9216.png").renameTo(File(rootDirectory, "flag_ki.png"))
        File(rootDirectory, "kosovo_flags_flag_9215.png").renameTo(File(rootDirectory, "flag_xk.png")) // XK ist der temporäre Nutzer-Ländercode
        File(rootDirectory, "kuwaitflag_flags_kuwai_9217.png").renameTo(File(rootDirectory, "flag_kw.png"))
        File(rootDirectory, "kyrgyzstan_flags_flag_9214.png").renameTo(File(rootDirectory, "flag_kg.png"))
        File(rootDirectory, "laos_flags_flag_9213.png").renameTo(File(rootDirectory, "flag_la.png"))
        File(rootDirectory, "Latvia_flags_flag_9212.png").renameTo(File(rootDirectory, "flag_lv.png"))
        File(rootDirectory, "lebanon_flags_flag_9211.png").renameTo(File(rootDirectory, "flag_lb.png"))
        File(rootDirectory, "lesothoflag_flags_lesotho_9210.png").renameTo(File(rootDirectory, "flag_ls.png"))
        File(rootDirectory, "liberia_flags_flag_9209.png").renameTo(File(rootDirectory, "flag_lr.png"))
        File(rootDirectory, "Libyanflags_flag_libi_9208.png").renameTo(File(rootDirectory, "flag_ly.png"))
        File(rootDirectory, "liechtenstein_flags_flag_9207.png").renameTo(File(rootDirectory, "flag_li.png"))
        File(rootDirectory, "Luxembourgflag_flags_luxemburg_9205.png").renameTo(File(rootDirectory, "flag_lu.png"))
        File(rootDirectory, "macao_flags_flag_9204.png").renameTo(File(rootDirectory, "flag_mo.png"))
        File(rootDirectory, "Macedonianflags_flag_macedoni_9203.png").renameTo(File(rootDirectory, "flag_mk.png"))
        File(rootDirectory, "madagascar_flags_flag_9202.png").renameTo(File(rootDirectory, "flag_mg.png"))
        File(rootDirectory, "malawi_flags_flag_9200.png").renameTo(File(rootDirectory, "flag_mw.png"))
        File(rootDirectory, "Malaysianflags__malasia_9199.png").renameTo(File(rootDirectory, "flag_my.png"))
        File(rootDirectory, "Maldives_flags_flag_9201.png").renameTo(File(rootDirectory, "flag_mv.png"))
        File(rootDirectory, "mali_flags_flag_9198.png").renameTo(File(rootDirectory, "flag_ml.png"))
        File(rootDirectory, "maltaflag_flags_malt_9197.png").renameTo(File(rootDirectory, "flag_mt.png"))
        File(rootDirectory, "MarshallIslands_theflags_theflag_9194.png").renameTo(File(rootDirectory, "flag_mh.png"))
        File(rootDirectory, "Martinique_flags_flag_9196.png").renameTo(File(rootDirectory, "flag_mq.png"))
        File(rootDirectory, "mauritania_flags_flag_9193.png").renameTo(File(rootDirectory, "flag_mr.png"))
        File(rootDirectory, "MAURITIUS_flags_flag_9192.png").renameTo(File(rootDirectory, "flag_mu.png"))
        File(rootDirectory, "mayotte_flags_flag_9190.png").renameTo(File(rootDirectory, "flag_yt.png"))
        File(rootDirectory, "Mexico_flags_flag_9191.png").renameTo(File(rootDirectory, "flag_mx.png"))
        File(rootDirectory, "Micronesia_flags_flag_9188.png").renameTo(File(rootDirectory, "flag_fm.png"))
        File(rootDirectory, "Moldova_flags_flag_9189.png").renameTo(File(rootDirectory, "flag_md.png"))
        File(rootDirectory, "monaco_flags_flag_9187.png").renameTo(File(rootDirectory, "flag_mc.png"))
        File(rootDirectory, "mongolia_flags_flag_9185.png").renameTo(File(rootDirectory, "flag_mn.png"))
        File(rootDirectory, "montenegro_flags_flag_9186.png").renameTo(File(rootDirectory, "flag_me.png"))
        File(rootDirectory, "montserrat_flags_flag_9184.png").renameTo(File(rootDirectory, "flag_ms.png"))
        File(rootDirectory, "morocco_flags_flag_9182.png").renameTo(File(rootDirectory, "flag_ma.png"))
        File(rootDirectory, "mozambique_flags_flag_9183.png").renameTo(File(rootDirectory, "flag_mz.png"))
        File(rootDirectory, "myanmar_flags_flag_9181.png").renameTo(File(rootDirectory, "flag_mm.png"))
        File(rootDirectory, "NagornoKarabakh_flags_flag_9180.png").renameTo(File(rootDirectory, "flag_az_nk.png")) // Bergkarabach
        File(rootDirectory, "Namibia_flags_flag_9177.png").renameTo(File(rootDirectory, "flag_na.png"))
        File(rootDirectory, "nato_flags_flag_9179.png").renameTo(File(rootDirectory, "flag_nato.png"))
        File(rootDirectory, "nauru_flags_flag_9178.png").renameTo(File(rootDirectory, "flag_nr.png"))
        File(rootDirectory, "nepal_flags_flag_9077.png").renameTo(File(rootDirectory, "flag_np.png"))
        File(rootDirectory, "NetherlandsAntilles_flags_flag_9176.png").renameTo(File(rootDirectory, "flag_an.png")) // Niederländische Antillen (historisch)
        File(rootDirectory, "netherlands_flags_flag_9175.png").renameTo(File(rootDirectory, "flag_nl.png"))
        File(rootDirectory, "NewCaledonia_flags_flag_9174.png").renameTo(File(rootDirectory, "flag_nc.png"))
        File(rootDirectory, "NewZealand_flags_flag_9172.png").renameTo(File(rootDirectory, "flag_nz.png"))
        File(rootDirectory, "Nicaragua_flags_flag_9173.png").renameTo(File(rootDirectory, "flag_ni.png"))
        File(rootDirectory, "nigeria_flags_flag_9171.png").renameTo(File(rootDirectory, "flag_ng.png"))
        File(rootDirectory, "niger_flags_flag_9170.png").renameTo(File(rootDirectory, "flag_ne.png"))
        File(rootDirectory, "Niue_flags_flag_9169.png").renameTo(File(rootDirectory, "flag_nu.png"))
        File(rootDirectory, "NorfolkIsland_flags_flag_9168.png").renameTo(File(rootDirectory, "flag_nf.png"))
        File(rootDirectory, "NorthernCyprus_flags_flag_9166.png").renameTo(File(rootDirectory, "flag_cy_tr.png")) // Nordzypern
        File(rootDirectory, "NorthernMarianaIslands_flags_flag_9165.png").renameTo(File(rootDirectory, "flag_mp.png"))
        File(rootDirectory, "northkorea_flags_flag_9167.png").renameTo(File(rootDirectory, "flag_kp.png"))
        File(rootDirectory, "Norwegianflags_flag_norueg_9164.png").renameTo(File(rootDirectory, "flag_no.png"))
        File(rootDirectory, "olympics_flags_flag_9163.png").renameTo(File(rootDirectory, "flag_olympics.png"))
        File(rootDirectory, "oman_flags_flag_9162.png").renameTo(File(rootDirectory, "flag_om.png"))
        File(rootDirectory, "pakistan_flags_flag_9161.png").renameTo(File(rootDirectory, "flag_pk.png"))
        File(rootDirectory, "palau_flags_flag_9159.png").renameTo(File(rootDirectory, "flag_pw.png"))
        File(rootDirectory, "Palestinianflags_flag_palestin_9160.png").renameTo(File(rootDirectory, "flag_ps.png"))
        File(rootDirectory, "Panamaflag_flags_Panama_9158.png").renameTo(File(rootDirectory, "flag_pa.png"))
        File(rootDirectory, "PapuaNewGuinea_flags_flag_9155.png").renameTo(File(rootDirectory, "flag_pg.png"))
        File(rootDirectory, "Paraguay_flags_flag_9156.png").renameTo(File(rootDirectory, "flag_py.png"))
        File(rootDirectory, "Peru_flags_flag_9157.png").renameTo(File(rootDirectory, "flag_pe.png"))
        File(rootDirectory, "Philippineflags_flag_filipina_9153.png").renameTo(File(rootDirectory, "flag_ph.png"))
        File(rootDirectory, "PitcairnIslands_flags_flag_9154.png").renameTo(File(rootDirectory, "flag_pn.png"))
        File(rootDirectory, "polandflag_flags_polonia_9151.png").renameTo(File(rootDirectory, "flag_pl.png"))
        File(rootDirectory, "portugal_flags_flag_9152.png").renameTo(File(rootDirectory, "flag_pt.png"))
        File(rootDirectory, "PuertoRico_flags_flag_9148.png").renameTo(File(rootDirectory, "flag_pr.png"))
        File(rootDirectory, "qatar_flags_flag_9150.png").renameTo(File(rootDirectory, "flag_qa.png"))
        File(rootDirectory, "redcrossflags__cruzroja_9149.png").renameTo(File(rootDirectory, "flag_redcross.png"))
        File(rootDirectory, "RepublicofCongo_flags_flag_9146.png").renameTo(File(rootDirectory, "flag_cg.png"))
        File(rootDirectory, "republic_flags_flag_9285.png").renameTo(File(rootDirectory, "flag_republic.png"))
        File(rootDirectory, "romaniaflag_flags_rumania_9147.png").renameTo(File(rootDirectory, "flag_ro.png"))
        File(rootDirectory, "russiaflag_flags_rusi_9144.png").renameTo(File(rootDirectory, "flag_ru.png"))
        File(rootDirectory, "Rwandanflags__Ruand_9145.png").renameTo(File(rootDirectory, "flag_rw.png"))
        File(rootDirectory, "SaintKittsandNevis_flags_flag_9142.png").renameTo(File(rootDirectory, "flag_kn.png"))
        File(rootDirectory, "SaintVincentandGrenadinesflag_flags_sanvicenteylasgranadina_9138.png").renameTo(File(rootDirectory, "flag_vc.png"))
        File(rootDirectory, "SalomonIslands_flags_flag_9124.png").renameTo(File(rootDirectory, "flag_sb.png"))
        File(rootDirectory, "samoaamericanflags_flag_americansamoa_9331.png").renameTo(File(rootDirectory, "flag_as.png"))
        File(rootDirectory, "samoa_flags_flag_9137.png").renameTo(File(rootDirectory, "flag_ws.png"))
        File(rootDirectory, "SanBartolome_flags_flag_9141.png").renameTo(File(rootDirectory, "flag_bl.png")) // St. Barthélemy
        File(rootDirectory, "sanmarino_flags_flag_9136.png").renameTo(File(rootDirectory, "flag_sm.png"))
        File(rootDirectory, "sanmartin_flags_flag_9140.png").renameTo(File(rootDirectory, "flag_mf.png")) // Saint-Martin (franz. Teil)
        File(rootDirectory, "santahelena_flags_flag_9143.png").renameTo(File(rootDirectory, "flag_sh.png"))
        File(rootDirectory, "santalucia_flags_flag_9139.png").renameTo(File(rootDirectory, "flag_lc.png"))
        File(rootDirectory, "SaoTomeandPrincipe_flags_flag_9134.png").renameTo(File(rootDirectory, "flag_st.png"))
        File(rootDirectory, "saudiarabia_flags_flag_9135.png").renameTo(File(rootDirectory, "flag_sa.png"))
        File(rootDirectory, "scotland_flags_flag_9133.png").renameTo(File(rootDirectory, "flag_gb_sct.png")) // Schottland (UK)
        File(rootDirectory, "senegalflag_flags_senegal_9132.png").renameTo(File(rootDirectory, "flag_sn.png"))
        File(rootDirectory, "Serbianflags__serbi_9130.png").renameTo(File(rootDirectory, "flag_rs.png"))
        File(rootDirectory, "seychellesflag_flags_seychelles_9131.png").renameTo(File(rootDirectory, "flag_sc.png"))
        File(rootDirectory, "sierraleone_flags_flag_9129.png").renameTo(File(rootDirectory, "flag_sl.png"))
        File(rootDirectory, "singaporeflag_flags_singapur_9127.png").renameTo(File(rootDirectory, "flag_sg.png"))
        File(rootDirectory, "Slovakia_flags_flag_9128.png").renameTo(File(rootDirectory, "flag_sk.png"))
        File(rootDirectory, "Slovenia_flags_flag_9126.png").renameTo(File(rootDirectory, "flag_si.png"))
        File(rootDirectory, "somaliaflag_flags_somalia_9125.png").renameTo(File(rootDirectory, "flag_so.png"))
        File(rootDirectory, "Somalilandflags__Somalilandia_9123.png").renameTo(File(rootDirectory, "flag_so_so.png")) // Somaliland (völkerrechtlich Somalia)
        File(rootDirectory, "southafricaflag_flags_surafric_9122.png").renameTo(File(rootDirectory, "flag_za.png"))
        File(rootDirectory, "SouthGeorgiaandSouthSandwich_flags_flag_9120.png").renameTo(File(rootDirectory, "flag_gs.png"))
        File(rootDirectory, "SouthKorea_flags_flag_9121.png").renameTo(File(rootDirectory, "flag_kr.png"))
        File(rootDirectory, "SouthOssetianflags__OsetiadelSur_9118.png").renameTo(File(rootDirectory, "flag_ge_os.png")) // Südossetien (völkerrechtlich Georgien)
        File(rootDirectory, "southsudanflag_flags_alsurdesuda_9117.png").renameTo(File(rootDirectory, "flag_ss.png"))
        File(rootDirectory, "Spain_flags_flag_9119.png").renameTo(File(rootDirectory, "flag_es.png"))
        File(rootDirectory, "srilanka_flags_flag_9115.png").renameTo(File(rootDirectory, "flag_lk.png"))
        File(rootDirectory, "sudan_flags_flag_9116.png").renameTo(File(rootDirectory, "flag_sd.png"))
        File(rootDirectory, "suriname_flags_flag_9114.png").renameTo(File(rootDirectory, "flag_sr.png"))
        File(rootDirectory, "Swaziland_flags_flag_9113.png").renameTo(File(rootDirectory, "flag_sz.png")) // Eswatini
        File(rootDirectory, "swedenflag_flags_sueci_9112.png").renameTo(File(rootDirectory, "flag_se.png"))
        File(rootDirectory, "Swissflags_flag_suiz_9078.png").renameTo(File(rootDirectory, "flag_ch.png"))
        File(rootDirectory, "Syrianflags_flag_siri_9110.png").renameTo(File(rootDirectory, "flag_sy.png"))
        File(rootDirectory, "taiwan_flags_flag_9111.png").renameTo(File(rootDirectory, "flag_tw.png"))
        File(rootDirectory, "Tajikistan_flags_flag_9109.png").renameTo(File(rootDirectory, "flag_tj.png"))
        File(rootDirectory, "Tanzania_flags_flag_9108.png").renameTo(File(rootDirectory, "flag_tz.png"))
        File(rootDirectory, "tarpon_flags_flag_9290.png").renameTo(File(rootDirectory, "flag_tarpon.png")) // Beibehalten / Unbekannt
        File(rootDirectory, "thailand_flags_flag_9107.png").renameTo(File(rootDirectory, "flag_th.png"))
        File(rootDirectory, "TheTurksandCaicosIslandsflag_flags__9100.png").renameTo(File(rootDirectory, "flag_tc.png"))
        File(rootDirectory, "timor_flags_flag_9270.png").renameTo(File(rootDirectory, "flag_tl.png")) // Osttimor / Timor-Leste
        File(rootDirectory, "togoflag_flags_togo_9106.png").renameTo(File(rootDirectory, "flag_tg.png"))
        File(rootDirectory, "Tokelau_flags_flag_9104.png").renameTo(File(rootDirectory, "flag_tk.png"))
        File(rootDirectory, "tonga_flags_flag_9105.png").renameTo(File(rootDirectory, "flag_to.png"))
        File(rootDirectory, "TrinidadandTobago_flags_flag_9103.png").renameTo(File(rootDirectory, "flag_tt.png"))
        File(rootDirectory, "tunez_flags_flag_9102.png").renameTo(File(rootDirectory, "flag_tn.png")) // Tunesien
        File(rootDirectory, "turkey_flags_flag_9101.png").renameTo(File(rootDirectory, "flag_tr.png"))
        File(rootDirectory, "turkmenistan_flags_flag_9099.png").renameTo(File(rootDirectory, "flag_tm.png"))
        File(rootDirectory, "Tuvalu_flags_flag_9096.png").renameTo(File(rootDirectory, "flag_tv.png"))
        File(rootDirectory, "uaeflag_flags_emiratosarabesunidos_9095.png").renameTo(File(rootDirectory, "flag_ae.png"))
        File(rootDirectory, "uganda_flags_flag_9098.png").renameTo(File(rootDirectory, "flag_ug.png"))
        File(rootDirectory, "Ukraine_flags_flag_9097.png").renameTo(File(rootDirectory, "flag_ua.png"))
        File(rootDirectory, "uk_flags_flag_9094.png").renameTo(File(rootDirectory, "flag_gb.png"))
        File(rootDirectory, "unitednations_flags_flag_9092.png").renameTo(File(rootDirectory, "flag_un.png")) // Vereinte Nationen
        File(rootDirectory, "unitedstates_flags_flag_9093.png").renameTo(File(rootDirectory, "flag_us.png"))
        File(rootDirectory, "unknown_flags_flag_9091.png").renameTo(File(rootDirectory, "flag_unknown.png"))
        File(rootDirectory, "Uruguay_flags_flag_9090.png").renameTo(File(rootDirectory, "flag_uy.png"))
        File(rootDirectory, "uzbekistanflag_flags_uzbekista_9088.png").renameTo(File(rootDirectory, "flag_uz.png"))
        File(rootDirectory, "vanuatu_flags_flag_9087.png").renameTo(File(rootDirectory, "flag_vu.png"))
        File(rootDirectory, "vaticancity_flags_flag_9076.png").renameTo(File(rootDirectory, "flag_va.png"))
        File(rootDirectory, "Venezuela_flags_flag_9086.png").renameTo(File(rootDirectory, "flag_ve.png"))
        File(rootDirectory, "vietnamflag_flags_vietna_9085.png").renameTo(File(rootDirectory, "flag_vn.png"))
        File(rootDirectory, "virginislands_flags_flag_9089.png").renameTo(File(rootDirectory, "flag_vi.png")) // Amerikanische Jungferninseln
        File(rootDirectory, "Wales_flags_flag_9083.png").renameTo(File(rootDirectory, "flag_gb_wls.png")) // Wales (UK)
        File(rootDirectory, "WallisandFutuna_flags_flag_9084.png").renameTo(File(rootDirectory, "flag_wf.png"))
        File(rootDirectory, "WesternSahara_flags_flag_9082.png").renameTo(File(rootDirectory, "flag_eh.png")) // Westsahara
        File(rootDirectory, "yemen_flag_flags_9081.png").renameTo(File(rootDirectory, "flag_ye.png"))
        File(rootDirectory, "zambia_flags_flag_9080.png").renameTo(File(rootDirectory, "flag_zm.png"))
        File(rootDirectory, "zimbabweflag_flags_zimbabwe_9079.png").renameTo(File(rootDirectory, "flag_zw.png"))
    }
}
