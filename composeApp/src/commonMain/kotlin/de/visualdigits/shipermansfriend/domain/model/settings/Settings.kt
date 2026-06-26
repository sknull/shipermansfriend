package de.visualdigits.shipermansfriend.domain.model.settings

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration
import de.visualdigits.common.domain.model.configuration.EnumFieldDescriptor
import de.visualdigits.common.domain.model.configuration.IntFieldDescriptor
import de.visualdigits.common.domain.model.configuration.PasswordFieldDescriptor
import de.visualdigits.common.domain.model.configuration.StringFieldDescriptor
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.configuration.keyfactory.IntKeyFactory
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.group_aisstream
import de.visualdigits.compose.resources.label_aisstreamApiKey
import de.visualdigits.compose.resources.label_language
import de.visualdigits.compose.resources.label_location
import de.visualdigits.compose.resources.label_radiusInner
import de.visualdigits.compose.resources.label_radiusOuter
import de.visualdigits.compose.resources.label_useGpsLocation
import de.visualdigits.compose.resources.tooltip_aisstreamApiKey
import de.visualdigits.compose.resources.tooltip_language
import de.visualdigits.compose.resources.tooltip_location
import de.visualdigits.compose.resources.tooltip_radiusInner
import de.visualdigits.compose.resources.tooltip_radiusOuter
import de.visualdigits.compose.resources.tooltip_useGpsLocation
import de.visualdigits.shipermansfriend.domain.model.type.Language

@Immutable
class Settings(
    values: Map<SK, Any?> = mapOf(),
): AbstractConfiguration<Settings, SK>(values, DESCRIPTORS) {

    companion object {
        val DESCRIPTORS = listOf(

            /** The UI language. */
            EnumFieldDescriptor(
                fieldClass = Language::class,
                key = SK.language,
                label = UiText.StringResourceId(Res.string.label_language),
                toolTip =  UiText.StringResourceId(Res.string.tooltip_language),
                options = { _, _ -> Language.options },
                keyFactory = Language,
                default = Language.EN
            ),

            /** The webDav password. */
            PasswordFieldDescriptor(
                group = UiText.StringResourceId(Res.string.group_aisstream),
                key = SK.aisstreamApiKey,
                label = UiText.StringResourceId(Res.string.label_aisstreamApiKey),
                toolTip = UiText.StringResourceId(Res.string.tooltip_aisstreamApiKey),
            ),

            /** The current location for offline use. */
            StringFieldDescriptor(
                group = UiText.StringResourceId(Res.string.group_aisstream),
                key = SK.location,
                label = UiText.StringResourceId(Res.string.label_location),
                toolTip = UiText.StringResourceId(Res.string.tooltip_location),
            ),

            /** Use GPS location. */
            EnumFieldDescriptor(
                fieldClass = BooleanEnum::class,
                group = UiText.StringResourceId(Res.string.group_aisstream),
                key = SK.useGpsLocation,
                label =  UiText.StringResourceId(Res.string.label_useGpsLocation),
                toolTip =  UiText.StringResourceId(Res.string.tooltip_useGpsLocation),
                options = { _, _ -> BooleanEnum.options },
                keyFactory = BooleanEnum,
                default = BooleanEnum.TRUE
            ),

            /** The current location for offline use. */
            StringFieldDescriptor(
                group = UiText.StringResourceId(Res.string.group_aisstream),
                key = SK.radiusOuter,
                label = UiText.StringResourceId(Res.string.label_radiusOuter),
                toolTip = UiText.StringResourceId(Res.string.tooltip_radiusOuter),
            ),

            /** The current location for offline use. */
            StringFieldDescriptor(
                group = UiText.StringResourceId(Res.string.group_aisstream),
                key = SK.radiusInner,
                label = UiText.StringResourceId(Res.string.label_radiusInner),
                toolTip = UiText.StringResourceId(Res.string.tooltip_radiusInner),
            ),



            /** Hidden field for maxImageSize. */
            EnumFieldDescriptor(
                fieldClass = Int::class,
                visible = false,
                key = SK.maxImageSize,
                label =  UiText.DynamicString(""),
                keyFactory = IntKeyFactory
            ),

            /** Hidden field for maxImageSize. */
            IntFieldDescriptor(
                visible = false,
                key = SK.maxImageSize,
                label =  UiText.DynamicString(""),
            )
        )
    }

    override fun createInstance(newValues: Map<SK, Any?>): Settings {
        return Settings(newValues)
    }
}
