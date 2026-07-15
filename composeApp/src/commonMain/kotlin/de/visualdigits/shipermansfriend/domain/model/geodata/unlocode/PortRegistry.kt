package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

object PortRegistry {

    // Cache für bereits geladene Länder, um Performance zu sichern
    private val loadedCountries = mutableMapOf<String, Map<String, PortCode>>()

    fun findPort(code: String?): PortCode? {
        if (code == null) return null

        val normalizedCode = code.trim().lowercase()
        return when (normalizedCode.length) {
            5 -> {
                getOrLoadCountry(normalizedCode.take(2))[normalizedCode.substring(2)]
            }
            3 -> {
                Country.entries.firstNotNullOfOrNull { country ->
                    getOrLoadCountry(country.prefix)[normalizedCode]
                }
            }
            else -> null
        }
    }
    
    private fun getOrLoadCountry(countryCode: String): Map<String, PortCode> {
        return loadedCountries.getOrPut(countryCode) {
            when (countryCode) {
                "ae" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAE.PORTS.associateBy { it.code }
                "af" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAF.PORTS.associateBy { it.code }
                "ag" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAG.PORTS.associateBy { it.code }
                "ai" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAI.PORTS.associateBy { it.code }
                "al" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAL.PORTS.associateBy { it.code }
                "am" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAM.PORTS.associateBy { it.code }
                "ao" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAO.PORTS.associateBy { it.code }
                "aq" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAQ.PORTS.associateBy { it.code }
                "ar" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAR.PORTS.associateBy { it.code }
                "as" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAS.PORTS.associateBy { it.code }
                "at" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAT.PORTS.associateBy { it.code }
                "au" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAU.PORTS.associateBy { it.code }
                "aw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAW.PORTS.associateBy { it.code }
                "ax" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAX.PORTS.associateBy { it.code }
                "az" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAZ.PORTS.associateBy { it.code }
                "ba" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBA.PORTS.associateBy { it.code }
                "bb" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBB.PORTS.associateBy { it.code }
                "bd" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBD.PORTS.associateBy { it.code }
                "be" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBE.PORTS.associateBy { it.code }
                "bf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBF.PORTS.associateBy { it.code }
                "bg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBG.PORTS.associateBy { it.code }
                "bh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBH.PORTS.associateBy { it.code }
                "bi" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBI.PORTS.associateBy { it.code }
                "bj" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBJ.PORTS.associateBy { it.code }
                "bl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBL.PORTS.associateBy { it.code }
                "bm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBM.PORTS.associateBy { it.code }
                "bn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBN.PORTS.associateBy { it.code }
                "bo" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBO.PORTS.associateBy { it.code }
                "bq" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBQ.PORTS.associateBy { it.code }
                "br" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBR.PORTS.associateBy { it.code }
                "bs" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBS.PORTS.associateBy { it.code }
                "bw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBW.PORTS.associateBy { it.code }
                "by" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBY.PORTS.associateBy { it.code }
                "bz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBZ.PORTS.associateBy { it.code }
                "ca" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCA.PORTS.associateBy { it.code }
                "cc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCC.PORTS.associateBy { it.code }
                "cd" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCD.PORTS.associateBy { it.code }
                "cf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCF.PORTS.associateBy { it.code }
                "cg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCG.PORTS.associateBy { it.code }
                "ch" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCH.PORTS.associateBy { it.code }
                "ci" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCI.PORTS.associateBy { it.code }
                "ck" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCK.PORTS.associateBy { it.code }
                "cl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCL.PORTS.associateBy { it.code }
                "cm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCM.PORTS.associateBy { it.code }
                "cn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCN.PORTS.associateBy { it.code }
                "co" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCO.PORTS.associateBy { it.code }
                "cr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCR.PORTS.associateBy { it.code }
                "cu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCU.PORTS.associateBy { it.code }
                "cv" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCV.PORTS.associateBy { it.code }
                "cw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCW.PORTS.associateBy { it.code }
                "cx" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCX.PORTS.associateBy { it.code }
                "cy" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCY.PORTS.associateBy { it.code }
                "cz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCZ.PORTS.associateBy { it.code }
                "de" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDE.PORTS.associateBy { it.code }
                "dj" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDJ.PORTS.associateBy { it.code }
                "dk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDK.PORTS.associateBy { it.code }
                "dm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDM.PORTS.associateBy { it.code }
                "do" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDO.PORTS.associateBy { it.code }
                "dz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDZ.PORTS.associateBy { it.code }
                "ec" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEC.PORTS.associateBy { it.code }
                "ee" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEE.PORTS.associateBy { it.code }
                "eg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEG.PORTS.associateBy { it.code }
                "eh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEH.PORTS.associateBy { it.code }
                "er" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsER.PORTS.associateBy { it.code }
                "es" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsES.PORTS.associateBy { it.code }
                "fi" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFI.PORTS.associateBy { it.code }
                "fj" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFJ.PORTS.associateBy { it.code }
                "fk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFK.PORTS.associateBy { it.code }
                "fm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFM.PORTS.associateBy { it.code }
                "fo" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFO.PORTS.associateBy { it.code }
                "fr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFR.PORTS.associateBy { it.code }
                "ga" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGA.PORTS.associateBy { it.code }
                "gb" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGB.PORTS.associateBy { it.code }
                "gd" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGD.PORTS.associateBy { it.code }
                "ge" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGE.PORTS.associateBy { it.code }
                "gf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGF.PORTS.associateBy { it.code }
                "gg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGG.PORTS.associateBy { it.code }
                "gh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGH.PORTS.associateBy { it.code }
                "gi" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGI.PORTS.associateBy { it.code }
                "gl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGL.PORTS.associateBy { it.code }
                "gm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGM.PORTS.associateBy { it.code }
                "gn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGN.PORTS.associateBy { it.code }
                "gp" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGP.PORTS.associateBy { it.code }
                "gq" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGQ.PORTS.associateBy { it.code }
                "gr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGR.PORTS.associateBy { it.code }
                "gs" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGS.PORTS.associateBy { it.code }
                "gt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGT.PORTS.associateBy { it.code }
                "gu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGU.PORTS.associateBy { it.code }
                "gw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGW.PORTS.associateBy { it.code }
                "gy" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGY.PORTS.associateBy { it.code }
                "hk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHK.PORTS.associateBy { it.code }
                "hm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHM.PORTS.associateBy { it.code }
                "hn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHN.PORTS.associateBy { it.code }
                "hr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHR.PORTS.associateBy { it.code }
                "ht" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHT.PORTS.associateBy { it.code }
                "hu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHU.PORTS.associateBy { it.code }
                "id" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsID.PORTS.associateBy { it.code }
                "ie" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIE.PORTS.associateBy { it.code }
                "il" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIL.PORTS.associateBy { it.code }
                "im" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIM.PORTS.associateBy { it.code }
                "in" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIN.PORTS.associateBy { it.code }
                "iq" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIQ.PORTS.associateBy { it.code }
                "ir" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIR.PORTS.associateBy { it.code }
                "is" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIS.PORTS.associateBy { it.code }
                "it" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIT.PORTS.associateBy { it.code }
                "je" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJE.PORTS.associateBy { it.code }
                "jm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJM.PORTS.associateBy { it.code }
                "jo" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJO.PORTS.associateBy { it.code }
                "jp" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJP.PORTS.associateBy { it.code }
                "ke" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKE.PORTS.associateBy { it.code }
                "kh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKH.PORTS.associateBy { it.code }
                "ki" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKI.PORTS.associateBy { it.code }
                "km" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKM.PORTS.associateBy { it.code }
                "kn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKN.PORTS.associateBy { it.code }
                "kp" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKP.PORTS.associateBy { it.code }
                "kr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKR.PORTS.associateBy { it.code }
                "kw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKW.PORTS.associateBy { it.code }
                "ky" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKY.PORTS.associateBy { it.code }
                "kz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKZ.PORTS.associateBy { it.code }
                "lb" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLB.PORTS.associateBy { it.code }
                "lc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLC.PORTS.associateBy { it.code }
                "lk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLK.PORTS.associateBy { it.code }
                "lr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLR.PORTS.associateBy { it.code }
                "lt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLT.PORTS.associateBy { it.code }
                "lu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLU.PORTS.associateBy { it.code }
                "lv" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLV.PORTS.associateBy { it.code }
                "ly" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLY.PORTS.associateBy { it.code }
                "ma" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMA.PORTS.associateBy { it.code }
                "mc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMC.PORTS.associateBy { it.code }
                "md" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMD.PORTS.associateBy { it.code }
                "me" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsME.PORTS.associateBy { it.code }
                "mf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMF.PORTS.associateBy { it.code }
                "mg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMG.PORTS.associateBy { it.code }
                "mh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMH.PORTS.associateBy { it.code }
                "mk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMK.PORTS.associateBy { it.code }
                "mm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMM.PORTS.associateBy { it.code }
                "mo" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMO.PORTS.associateBy { it.code }
                "mp" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMP.PORTS.associateBy { it.code }
                "mq" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMQ.PORTS.associateBy { it.code }
                "mr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMR.PORTS.associateBy { it.code }
                "ms" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMS.PORTS.associateBy { it.code }
                "mt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMT.PORTS.associateBy { it.code }
                "mu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMU.PORTS.associateBy { it.code }
                "mv" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMV.PORTS.associateBy { it.code }
                "mw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMW.PORTS.associateBy { it.code }
                "mx" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMX.PORTS.associateBy { it.code }
                "my" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMY.PORTS.associateBy { it.code }
                "mz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMZ.PORTS.associateBy { it.code }
                "na" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNA.PORTS.associateBy { it.code }
                "nc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNC.PORTS.associateBy { it.code }
                "ng" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNG.PORTS.associateBy { it.code }
                "ni" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNI.PORTS.associateBy { it.code }
                "nl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNL.PORTS.associateBy { it.code }
                "no" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNO.PORTS.associateBy { it.code }
                "np" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNP.PORTS.associateBy { it.code }
                "nr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNR.PORTS.associateBy { it.code }
                "nu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNU.PORTS.associateBy { it.code }
                "nz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNZ.PORTS.associateBy { it.code }
                "om" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsOM.PORTS.associateBy { it.code }
                "pa" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPA.PORTS.associateBy { it.code }
                "pe" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPE.PORTS.associateBy { it.code }
                "pf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPF.PORTS.associateBy { it.code }
                "pg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPG.PORTS.associateBy { it.code }
                "ph" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPH.PORTS.associateBy { it.code }
                "pk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPK.PORTS.associateBy { it.code }
                "pl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPL.PORTS.associateBy { it.code }
                "pm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPM.PORTS.associateBy { it.code }
                "pn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPN.PORTS.associateBy { it.code }
                "pr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPR.PORTS.associateBy { it.code }
                "pt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPT.PORTS.associateBy { it.code }
                "pw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPW.PORTS.associateBy { it.code }
                "py" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPY.PORTS.associateBy { it.code }
                "qa" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsQA.PORTS.associateBy { it.code }
                "re" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRE.PORTS.associateBy { it.code }
                "ro" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRO.PORTS.associateBy { it.code }
                "rs" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRS.PORTS.associateBy { it.code }
                "ru" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRU.PORTS.associateBy { it.code }
                "rw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRW.PORTS.associateBy { it.code }
                "sa" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSA.PORTS.associateBy { it.code }
                "sb" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSB.PORTS.associateBy { it.code }
                "sc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSC.PORTS.associateBy { it.code }
                "sd" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSD.PORTS.associateBy { it.code }
                "se" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSE.PORTS.associateBy { it.code }
                "sg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSG.PORTS.associateBy { it.code }
                "sh" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSH.PORTS.associateBy { it.code }
                "si" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSI.PORTS.associateBy { it.code }
                "sj" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSJ.PORTS.associateBy { it.code }
                "sk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSK.PORTS.associateBy { it.code }
                "sl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSL.PORTS.associateBy { it.code }
                "sm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSM.PORTS.associateBy { it.code }
                "sn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSN.PORTS.associateBy { it.code }
                "so" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSO.PORTS.associateBy { it.code }
                "sr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSR.PORTS.associateBy { it.code }
                "st" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsST.PORTS.associateBy { it.code }
                "sv" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSV.PORTS.associateBy { it.code }
                "sx" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSX.PORTS.associateBy { it.code }
                "sy" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSY.PORTS.associateBy { it.code }
                "sz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSZ.PORTS.associateBy { it.code }
                "tc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTC.PORTS.associateBy { it.code }
                "tf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTF.PORTS.associateBy { it.code }
                "tg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTG.PORTS.associateBy { it.code }
                "th" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTH.PORTS.associateBy { it.code }
                "tk" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTK.PORTS.associateBy { it.code }
                "tl" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTL.PORTS.associateBy { it.code }
                "tm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTM.PORTS.associateBy { it.code }
                "tn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTN.PORTS.associateBy { it.code }
                "to" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTO.PORTS.associateBy { it.code }
                "tr" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTR.PORTS.associateBy { it.code }
                "tt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTT.PORTS.associateBy { it.code }
                "tv" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTV.PORTS.associateBy { it.code }
                "tw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTW.PORTS.associateBy { it.code }
                "tz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTZ.PORTS.associateBy { it.code }
                "ua" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUA.PORTS.associateBy { it.code }
                "ug" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUG.PORTS.associateBy { it.code }
                "um" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUM.PORTS.associateBy { it.code }
                "us" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUS.PORTS.associateBy { it.code }
                "uy" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUY.PORTS.associateBy { it.code }
                "uz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUZ.PORTS.associateBy { it.code }
                "vc" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVC.PORTS.associateBy { it.code }
                "ve" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVE.PORTS.associateBy { it.code }
                "vg" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVG.PORTS.associateBy { it.code }
                "vi" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVI.PORTS.associateBy { it.code }
                "vn" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVN.PORTS.associateBy { it.code }
                "vu" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVU.PORTS.associateBy { it.code }
                "wf" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsWF.PORTS.associateBy { it.code }
                "ws" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsWS.PORTS.associateBy { it.code }
                "xz" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsXZ.PORTS.associateBy { it.code }
                "ye" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsYE.PORTS.associateBy { it.code }
                "yt" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsYT.PORTS.associateBy { it.code }
                "za" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZA.PORTS.associateBy { it.code }
                "zm" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZM.PORTS.associateBy { it.code }
                "zw" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZW.PORTS.associateBy { it.code }
                else -> emptyMap()
            }
        }
    }
}
