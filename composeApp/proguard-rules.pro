# Erhalte alle Compose-spezifischen Dinge
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Erhalte deine eigenen Klassen, damit Reflection (z.B. für Version oder Datenbanken) funktioniert
-keep class de.visualdigits.shipermansfriend.** { *; }
-keep class de.visualdigits.generated.** { *; }

# Schützt die generierten Ressourcen-Klassen von Compose Multiplatform vor der Umbenennung
-keep class de.visualdigits.compose.resources.** { *; }
-keep class org.jetbrains.compose.resources.** { *; }

# Falls du Kotlin Serialization oder Ktor nutzt
-keepattributes *Annotation*, InnerClasses, Signature, EnclosingMethod
-dontwarn kotlinx.serialization.**
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}
# Falls du Ktor nutzt, stelle sicher, dass er nicht die Java-Engine auf Android sucht
-dontwarn io.ktor.client.engine.java.**

# Ignoriere Desktop/AWT/Swing Klassen auf Android
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn de.visualdigits.common.presentation.components.form.DesktopFileChooserKt

# Ignoriere die Java-Standard-HTTP-Library (Ktor nutzt auf Android OkHttp oder Darwin)
-dontwarn java.net.http.**


# Verhindert, dass die Ressourcen-Metadaten wegoptimiert werden
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
