package com.cham.lib_processor

import com.cham.lib_annotations.BindLayout
import com.cham.lib_annotations.BindView
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/8 16:42
 * @UpdateUser:
 * @UpdateDate:     2021/3/8 16:42
 * @UpdateRemark:
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class BindProcessor : AbstractProcessor() {

    companion object {
        private const val PICK_END = "_Bind"
    }

    //存储类文件数据
    private val mInjectMaps = hashMapOf<String, InjectInfo>()


    private fun FileSpec.writeFile() {
        //文件编译后位置
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile.toPath())
    }
    private fun bindLayout(element: Element){
        //BindLayout注解的是Class，本身就是TypeElement
        val typeElement = element as TypeElement
        //一个类一个InjectInfo
        val className = typeElement.qualifiedName.toString()
        var injectInfo = mInjectMaps[className]
        if (injectInfo == null) {
            injectInfo = InjectInfo(typeElement)
        }
        typeElement.getAnnotation(BindLayout::class.java).run {
            injectInfo.layoutId =value
        }
        mInjectMaps[className] =injectInfo

    }

    private fun bindView(element: Element) {
        //Bind View注解的是变量 element 就是VariableElement
        val variableElement = element as VariableElement
        val typeElement = element.enclosingElement as TypeElement
        //一个类一个InjectInfo
        val className = typeElement.qualifiedName.toString()
        var injectInfo = mInjectMaps[className]
        if (injectInfo == null) {
            injectInfo = InjectInfo(typeElement)
        }


        variableElement.getAnnotation(BindView::class.java).run {
            injectInfo.viewMap[value] = variableElement
        }

        mInjectMaps[className] = injectInfo
    }


    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(BindView::class.java.canonicalName,
        BindLayout::class.java.canonicalName)
    }


    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        //里面就要生成我们需要的文件


        roundEnv.getElementsAnnotatedWith(BindLayout::class.java).forEach {
            bindLayout(it)

        }

        roundEnv.getElementsAnnotatedWith(BindView::class.java).forEach {
            bindView(it)

        }

        mInjectMaps.forEach { (_, info) ->
            val file = FileSpec.builder(info.packageName, info.className.simpleName + PICK_END)
                .addType(
                    TypeSpec.classBuilder(info.className.simpleName + PICK_END)
                        .primaryConstructor(info.generateConstructor()).build()
                ).build()

            file.writeFile()


        }

        return false
    }
}