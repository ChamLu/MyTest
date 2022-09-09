package com.cham.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        // 获取Android扩展
        def android = project.extensions.getByType(AppExtension)
        // 注册Transform，其实就是添加了Task
        android.registerTransform(new ChamTransform(project))

//        AppExtension appExtension = project.extensions.findByType(AppExtension.class)
//        appExtension.registerTransform(new ChamTransform(project))

        // 这里只是随便定义一个Task而已，和Transform无关
        project.task('JustTask') {
            doLast {
                println('InjectTransform task')
            }
        }

    }
}