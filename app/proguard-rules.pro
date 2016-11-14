# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\fung.lam\AppData\Local\Android\sdk\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
#-keep class android.support.v7.** { *; }
#-keep interface android.support.v7.** { *; }
#-keep class android.support.v4.** { *; }
#-keep interface android.support.v4.** { *; }
-keep public class com.google.android.gms.**
#-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}
-keep class android.support.** {*;}
-dontwarn android.support.v7.**
-dontwarn com.google.android.gms.**
-dontwarn android.support.**
-dontwarn com.github.**
-dontwarn com.squareup.picasso.**
-dontwarn com.etsy.android.grid.**

# for GPS
-keep class com.google.api.services.*.mode.*
-keep class com.google.api.client.**
-keepattributes Signature,RuntimeVisableAnnotations,AnnotationDefault

# for Maps
-keep class com.android.**
-keep class com.google.android.**
-keep class com.google.android.gms.**
-keep class com.google.android.gms.location.**
-keep class com.google.api.client.**
-keep class com.google.maps.android.**


-keep class android.os.Parcelable.Creator
-keep class com.google.android.gms.location.ActivityRecognitionResult
-keep class com.google.android.gms.** {*;}
