package de.visualdigits.shipermansfriend.domain.model.geodata.unlocode

object PortRegistry {

    // Cache für bereits geladene Länder, um Performance zu sichern
    private val loadedCountries = mutableMapOf<String, Map<String, PortCode>>()

    fun findPort(code: String?): PortCode? {
        if (code == null) return null
        
        val normalizedCode = code.trim().uppercase()
        return when (normalizedCode.length) {
            5 -> {
                val country = normalizedCode.take(2)
                val port = normalizedCode.substring(2)
                getOrLoadCountry(country)?.get(port)
            }
            3 -> {
                // Sucht dynamisch in allen Ländern (Lazy Loading)
                Country.entries.forEach { country ->
                    getOrLoadCountry(country.prefix)?.get(normalizedCode)?.let { return it }
                }
                null
            }
            else -> null
        }
    }
    
    private fun getOrLoadCountry(countryCode: String): Map<String, PortCode>? {
        return loadedCountries.getOrPut(countryCode) {
            when (countryCode) {
                "AE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAE.PORTS.associateBy { it.code }
                "AF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAF.PORTS.associateBy { it.code }
                "AG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAG.PORTS.associateBy { it.code }
                "AI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAI.PORTS.associateBy { it.code }
                "AL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAL.PORTS.associateBy { it.code }
                "AM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAM.PORTS.associateBy { it.code }
                "AO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAO.PORTS.associateBy { it.code }
                "AQ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAQ.PORTS.associateBy { it.code }
                "AR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAR.PORTS.associateBy { it.code }
                "AS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAS.PORTS.associateBy { it.code }
                "AT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAT.PORTS.associateBy { it.code }
                "AU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAU.PORTS.associateBy { it.code }
                "AW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAW.PORTS.associateBy { it.code }
                "AX" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAX.PORTS.associateBy { it.code }
                "AZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsAZ.PORTS.associateBy { it.code }
                "BA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBA.PORTS.associateBy { it.code }
                "BB" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBB.PORTS.associateBy { it.code }
                "BD" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBD.PORTS.associateBy { it.code }
                "BE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBE.PORTS.associateBy { it.code }
                "BF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBF.PORTS.associateBy { it.code }
                "BG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBG.PORTS.associateBy { it.code }
                "BH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBH.PORTS.associateBy { it.code }
                "BI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBI.PORTS.associateBy { it.code }
                "BJ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBJ.PORTS.associateBy { it.code }
                "BL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBL.PORTS.associateBy { it.code }
                "BM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBM.PORTS.associateBy { it.code }
                "BN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBN.PORTS.associateBy { it.code }
                "BO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBO.PORTS.associateBy { it.code }
                "BQ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBQ.PORTS.associateBy { it.code }
                "BR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBR.PORTS.associateBy { it.code }
                "BS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBS.PORTS.associateBy { it.code }
                "BW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBW.PORTS.associateBy { it.code }
                "BY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBY.PORTS.associateBy { it.code }
                "BZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsBZ.PORTS.associateBy { it.code }
                "CA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCA.PORTS.associateBy { it.code }
                "CC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCC.PORTS.associateBy { it.code }
                "CD" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCD.PORTS.associateBy { it.code }
                "CF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCF.PORTS.associateBy { it.code }
                "CG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCG.PORTS.associateBy { it.code }
                "CH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCH.PORTS.associateBy { it.code }
                "CI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCI.PORTS.associateBy { it.code }
                "CK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCK.PORTS.associateBy { it.code }
                "CL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCL.PORTS.associateBy { it.code }
                "CM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCM.PORTS.associateBy { it.code }
                "CN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCN.PORTS.associateBy { it.code }
                "CO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCO.PORTS.associateBy { it.code }
                "CR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCR.PORTS.associateBy { it.code }
                "CU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCU.PORTS.associateBy { it.code }
                "CV" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCV.PORTS.associateBy { it.code }
                "CW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCW.PORTS.associateBy { it.code }
                "CX" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCX.PORTS.associateBy { it.code }
                "CY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCY.PORTS.associateBy { it.code }
                "CZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsCZ.PORTS.associateBy { it.code }
                "DE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDE.PORTS.associateBy { it.code }
                "DJ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDJ.PORTS.associateBy { it.code }
                "DK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDK.PORTS.associateBy { it.code }
                "DM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDM.PORTS.associateBy { it.code }
                "DO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDO.PORTS.associateBy { it.code }
                "DZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsDZ.PORTS.associateBy { it.code }
                "EC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEC.PORTS.associateBy { it.code }
                "EE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEE.PORTS.associateBy { it.code }
                "EG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEG.PORTS.associateBy { it.code }
                "EH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsEH.PORTS.associateBy { it.code }
                "ER" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsER.PORTS.associateBy { it.code }
                "ES" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsES.PORTS.associateBy { it.code }
                "FI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFI.PORTS.associateBy { it.code }
                "FJ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFJ.PORTS.associateBy { it.code }
                "FK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFK.PORTS.associateBy { it.code }
                "FM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFM.PORTS.associateBy { it.code }
                "FO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFO.PORTS.associateBy { it.code }
                "FR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsFR.PORTS.associateBy { it.code }
                "GA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGA.PORTS.associateBy { it.code }
                "GB" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGB.PORTS.associateBy { it.code }
                "GD" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGD.PORTS.associateBy { it.code }
                "GE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGE.PORTS.associateBy { it.code }
                "GF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGF.PORTS.associateBy { it.code }
                "GG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGG.PORTS.associateBy { it.code }
                "GH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGH.PORTS.associateBy { it.code }
                "GI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGI.PORTS.associateBy { it.code }
                "GL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGL.PORTS.associateBy { it.code }
                "GM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGM.PORTS.associateBy { it.code }
                "GN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGN.PORTS.associateBy { it.code }
                "GP" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGP.PORTS.associateBy { it.code }
                "GQ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGQ.PORTS.associateBy { it.code }
                "GR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGR.PORTS.associateBy { it.code }
                "GS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGS.PORTS.associateBy { it.code }
                "GT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGT.PORTS.associateBy { it.code }
                "GU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGU.PORTS.associateBy { it.code }
                "GW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGW.PORTS.associateBy { it.code }
                "GY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsGY.PORTS.associateBy { it.code }
                "HK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHK.PORTS.associateBy { it.code }
                "HM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHM.PORTS.associateBy { it.code }
                "HN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHN.PORTS.associateBy { it.code }
                "HR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHR.PORTS.associateBy { it.code }
                "HT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHT.PORTS.associateBy { it.code }
                "HU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsHU.PORTS.associateBy { it.code }
                "ID" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsID.PORTS.associateBy { it.code }
                "IE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIE.PORTS.associateBy { it.code }
                "IL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIL.PORTS.associateBy { it.code }
                "IM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIM.PORTS.associateBy { it.code }
                "IN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIN.PORTS.associateBy { it.code }
                "IQ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIQ.PORTS.associateBy { it.code }
                "IR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIR.PORTS.associateBy { it.code }
                "IS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIS.PORTS.associateBy { it.code }
                "IT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsIT.PORTS.associateBy { it.code }
                "JE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJE.PORTS.associateBy { it.code }
                "JM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJM.PORTS.associateBy { it.code }
                "JO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJO.PORTS.associateBy { it.code }
                "JP" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsJP.PORTS.associateBy { it.code }
                "KE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKE.PORTS.associateBy { it.code }
                "KH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKH.PORTS.associateBy { it.code }
                "KI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKI.PORTS.associateBy { it.code }
                "KM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKM.PORTS.associateBy { it.code }
                "KN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKN.PORTS.associateBy { it.code }
                "KP" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKP.PORTS.associateBy { it.code }
                "KR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKR.PORTS.associateBy { it.code }
                "KW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKW.PORTS.associateBy { it.code }
                "KY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKY.PORTS.associateBy { it.code }
                "KZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsKZ.PORTS.associateBy { it.code }
                "LB" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLB.PORTS.associateBy { it.code }
                "LC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLC.PORTS.associateBy { it.code }
                "LK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLK.PORTS.associateBy { it.code }
                "LR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLR.PORTS.associateBy { it.code }
                "LT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLT.PORTS.associateBy { it.code }
                "LU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLU.PORTS.associateBy { it.code }
                "LV" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLV.PORTS.associateBy { it.code }
                "LY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsLY.PORTS.associateBy { it.code }
                "MA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMA.PORTS.associateBy { it.code }
                "MC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMC.PORTS.associateBy { it.code }
                "MD" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMD.PORTS.associateBy { it.code }
                "ME" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsME.PORTS.associateBy { it.code }
                "MF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMF.PORTS.associateBy { it.code }
                "MG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMG.PORTS.associateBy { it.code }
                "MH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMH.PORTS.associateBy { it.code }
                "MK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMK.PORTS.associateBy { it.code }
                "MM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMM.PORTS.associateBy { it.code }
                "MO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMO.PORTS.associateBy { it.code }
                "MP" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMP.PORTS.associateBy { it.code }
                "MQ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMQ.PORTS.associateBy { it.code }
                "MR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMR.PORTS.associateBy { it.code }
                "MS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMS.PORTS.associateBy { it.code }
                "MT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMT.PORTS.associateBy { it.code }
                "MU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMU.PORTS.associateBy { it.code }
                "MV" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMV.PORTS.associateBy { it.code }
                "MW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMW.PORTS.associateBy { it.code }
                "MX" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMX.PORTS.associateBy { it.code }
                "MY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMY.PORTS.associateBy { it.code }
                "MZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsMZ.PORTS.associateBy { it.code }
                "NA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNA.PORTS.associateBy { it.code }
                "NC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNC.PORTS.associateBy { it.code }
                "NG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNG.PORTS.associateBy { it.code }
                "NI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNI.PORTS.associateBy { it.code }
                "NL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNL.PORTS.associateBy { it.code }
                "NO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNO.PORTS.associateBy { it.code }
                "NP" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNP.PORTS.associateBy { it.code }
                "NR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNR.PORTS.associateBy { it.code }
                "NU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNU.PORTS.associateBy { it.code }
                "NZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsNZ.PORTS.associateBy { it.code }
                "OM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsOM.PORTS.associateBy { it.code }
                "PA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPA.PORTS.associateBy { it.code }
                "PE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPE.PORTS.associateBy { it.code }
                "PF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPF.PORTS.associateBy { it.code }
                "PG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPG.PORTS.associateBy { it.code }
                "PH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPH.PORTS.associateBy { it.code }
                "PK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPK.PORTS.associateBy { it.code }
                "PL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPL.PORTS.associateBy { it.code }
                "PM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPM.PORTS.associateBy { it.code }
                "PN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPN.PORTS.associateBy { it.code }
                "PR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPR.PORTS.associateBy { it.code }
                "PT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPT.PORTS.associateBy { it.code }
                "PW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPW.PORTS.associateBy { it.code }
                "PY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsPY.PORTS.associateBy { it.code }
                "QA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsQA.PORTS.associateBy { it.code }
                "RE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRE.PORTS.associateBy { it.code }
                "RO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRO.PORTS.associateBy { it.code }
                "RS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRS.PORTS.associateBy { it.code }
                "RU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRU.PORTS.associateBy { it.code }
                "RW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsRW.PORTS.associateBy { it.code }
                "SA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSA.PORTS.associateBy { it.code }
                "SB" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSB.PORTS.associateBy { it.code }
                "SC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSC.PORTS.associateBy { it.code }
                "SD" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSD.PORTS.associateBy { it.code }
                "SE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSE.PORTS.associateBy { it.code }
                "SG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSG.PORTS.associateBy { it.code }
                "SH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSH.PORTS.associateBy { it.code }
                "SI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSI.PORTS.associateBy { it.code }
                "SJ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSJ.PORTS.associateBy { it.code }
                "SK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSK.PORTS.associateBy { it.code }
                "SL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSL.PORTS.associateBy { it.code }
                "SM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSM.PORTS.associateBy { it.code }
                "SN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSN.PORTS.associateBy { it.code }
                "SO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSO.PORTS.associateBy { it.code }
                "SR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSR.PORTS.associateBy { it.code }
                "ST" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsST.PORTS.associateBy { it.code }
                "SV" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSV.PORTS.associateBy { it.code }
                "SX" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSX.PORTS.associateBy { it.code }
                "SY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSY.PORTS.associateBy { it.code }
                "SZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsSZ.PORTS.associateBy { it.code }
                "TC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTC.PORTS.associateBy { it.code }
                "TF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTF.PORTS.associateBy { it.code }
                "TG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTG.PORTS.associateBy { it.code }
                "TH" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTH.PORTS.associateBy { it.code }
                "TK" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTK.PORTS.associateBy { it.code }
                "TL" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTL.PORTS.associateBy { it.code }
                "TM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTM.PORTS.associateBy { it.code }
                "TN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTN.PORTS.associateBy { it.code }
                "TO" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTO.PORTS.associateBy { it.code }
                "TR" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTR.PORTS.associateBy { it.code }
                "TT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTT.PORTS.associateBy { it.code }
                "TV" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTV.PORTS.associateBy { it.code }
                "TW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTW.PORTS.associateBy { it.code }
                "TZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsTZ.PORTS.associateBy { it.code }
                "UA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUA.PORTS.associateBy { it.code }
                "UG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUG.PORTS.associateBy { it.code }
                "UM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUM.PORTS.associateBy { it.code }
                "US" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUS.PORTS.associateBy { it.code }
                "UY" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUY.PORTS.associateBy { it.code }
                "UZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsUZ.PORTS.associateBy { it.code }
                "VC" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVC.PORTS.associateBy { it.code }
                "VE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVE.PORTS.associateBy { it.code }
                "VG" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVG.PORTS.associateBy { it.code }
                "VI" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVI.PORTS.associateBy { it.code }
                "VN" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVN.PORTS.associateBy { it.code }
                "VU" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsVU.PORTS.associateBy { it.code }
                "WF" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsWF.PORTS.associateBy { it.code }
                "WS" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsWS.PORTS.associateBy { it.code }
                "XZ" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsXZ.PORTS.associateBy { it.code }
                "YE" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsYE.PORTS.associateBy { it.code }
                "YT" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsYT.PORTS.associateBy { it.code }
                "ZA" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZA.PORTS.associateBy { it.code }
                "ZM" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZM.PORTS.associateBy { it.code }
                "ZW" -> de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.ports.PortsZW.PORTS.associateBy { it.code }
                else -> emptyMap()
            }
        }
    }
}
