plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.workspacecreate"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.member)
    implementation(projects.data.workspace)
    // 이거 data.workspace가 data.core를 참조하는데 왜 profileModel()을 참조 못할까요
    implementation(projects.data.core)
    implementation(projects.common)
    implementation(projects.ui)
}