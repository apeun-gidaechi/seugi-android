plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.main"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.common)
    implementation(projects.data.profile)
    implementation(projects.data.workspace)

    implementation(projects.featureMain.chat)
    implementation(projects.featureMain.chatDatail)
    implementation(projects.featureMain.home)
    implementation(projects.featureMain.room)
    implementation(projects.featureMain.roomCreate)
    implementation(projects.featureMain.profile)
    implementation(projects.featureMain.notification)
    implementation(projects.featureMain.chatSeugi)
    implementation(projects.featureMain.workspace)
    implementation(projects.featureMain.notificationCreate)
    implementation(projects.featureMain.notificationEdit)
    implementation(projects.featureMain.workspaceDetail)
    implementation(projects.featureMain.workspaceCreate)
    implementation(projects.featureMain.timetable)
    implementation(projects.featureMain.setting)
    implementation(projects.featureMain.assignment)
    implementation(projects.featureMain.assignmentCreate)
    implementation(projects.featureMain.meal)

}