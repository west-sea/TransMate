plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.madcampw2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.madcampw2"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding{
        enable=true
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.kakao.sdk:v2-all:2.19.0") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인
    implementation("com.kakao.sdk:v2-talk:2.19.0") // 친구, 메시지(카카오톡)
    implementation("com.kakao.sdk:v2-share:2.19.0") // 메시지(카카오톡 공유)
    implementation("com.kakao.sdk:v2-friend:2.19.0") // 카카오톡 소셜 피커, 리소스 번들 파일 포함
    implementation("com.kakao.sdk:v2-navi:2.19.0") // 카카오내비
    implementation("com.kakao.sdk:v2-cert:2.19.0") // 카카오 인증서비스
    implementation ("com.airbnb.android:lottie:5.0.2") // 로티 애니메이션
    implementation ("de.hdodenhof:circleimageview:3.1.0") // 둥근 이미지

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("de.hdodenhof:circleimageview:2.2.0") //이미지 원형으로 바꿔주기
    implementation("com.google.android.material:material:1.2.1") //modal bottom sheet
    implementation ("com.github.bumptech.glide:glide:4.12.0") // Replace with the latest version
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") // Replace with the same version
}