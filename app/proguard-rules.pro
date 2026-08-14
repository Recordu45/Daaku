# ==========================================
# DAAKU - ProGuard / R8 Rules
# ==========================================

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Keep DAAKU application classes
-keep class com.daaku.app.** { *; }

# Keep data classes and constructors
-keepclassmembers class com.daaku.app.** {
    <fields>;
    <methods>;
}

# Don't print warnings during the V1 build
-dontwarn kotlinx.**
-dontwarn androidx.**
