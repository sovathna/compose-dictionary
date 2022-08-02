
-verbose
-dontoptimize
-dontobfuscate

-keepattributes *Annotation*
-dontwarn kotlinx.**

-keepclasseswithmembers public class io.github.sovathna.MainKt {
    public static void main(java.lang.String[]);
}

-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }

-keep class org.sqlite.** {*;}
