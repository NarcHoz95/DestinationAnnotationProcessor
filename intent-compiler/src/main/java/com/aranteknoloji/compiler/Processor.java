package com.aranteknoloji.compiler;

import com.aranteknoloji.intent.Destination;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class Processor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementsUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementsUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver()) {
            Set<TypeElement> typeElements = ProcessingUtils.getTypeElementsToProcess(
                    roundEnvironment.getRootElements(), annotations);

            String generatedClassPackageName = NameStore.getPackageName();
            ClassName intentClass = ClassName.get(NameStore.Package.ANDROID_CONTENT, NameStore.Class.ANDROID_INTENT);
            ClassName contextClass = ClassName.get(NameStore.Package.ANDROID_CONTENT, NameStore.Class.ANDROID_CONTEXT);
            ClassName applicationClass = ClassName.get(NameStore.Package.ANDROID_APPLICATION, NameStore.Class.ANDROID_APPLICATION);
            ClassName generatedClassName = ClassName.get(generatedClassPackageName, "IntentLauncher");


            //create the class
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            //generate constructor
//            MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
//                    .addModifiers(Modifier.PUBLIC)
//                    .addParameter(applicationClass, NameStore.Variable.ANDROID_APPLICATION)
//                    .addStatement("$T.$N = $N", generatedClassName, NameStore.Variable.ANDROID_APPLICATION, NameStore.Variable.ANDROID_APPLICATION);

            //add constructor
//            classBuilder.addMethod(constructor.build());

            for (TypeElement element : typeElements) {
                String packageName = elementsUtils.getPackageOf(element).getQualifiedName().toString();
                String typeName = element.getSimpleName().toString();
                ClassName className = ClassName.get(packageName, typeName);

                //create launch methods
                MethodSpec.Builder launchMethod = MethodSpec.methodBuilder("launch" + typeName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addParameter(contextClass, NameStore.Variable.ANDROID_CONTEXT)
                        .returns(void.class)
                        .addStatement("$T $N = new $T($N, $T.class)", intentClass, NameStore.Variable.ANDROID_INTENT, intentClass, NameStore.Variable.ANDROID_CONTEXT, className)
                        .addStatement("$N.startActivity($N)", NameStore.Variable.ANDROID_CONTEXT, NameStore.Variable.ANDROID_INTENT);


                //add method to the class
                classBuilder.addMethod(launchMethod.build());
//                messager.printMessage(Diagnostic.Kind.NOTE, "Method has been added for the element", element);
            }

            //finally, write java file
            try {
                JavaFile.builder(generatedClassPackageName, classBuilder.build()).build()
                        .writeTo(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.WARNING, e.toString(), null);
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Collections.singletonList(Destination.class.getCanonicalName()));
    }
}

