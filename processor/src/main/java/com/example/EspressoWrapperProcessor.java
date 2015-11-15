package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

import org.junit.Rule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class EspressoWrapperProcessor extends AbstractProcessor{

    private Filer filer;
    private Messager messager;
    public Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(TargetActivity.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        if(!env.processingOver()) {
            // access to all file and their methods and field
            for (Element e : env.getRootElements()) {
                TypeElement te = findEnclosingTypeElement(e);
                log(String.format("Scanning Type %s", te.getQualifiedName()));
                for (ExecutableElement ee : ElementFilter.methodsIn(te.getEnclosedElements())) {
                    log("ExecutableElement:"+ee);
                }
                for(VariableElement ve : ElementFilter.fieldsIn(te.getEnclosedElements())) {
                    log("VariableElement:"+ve);
                }
            }

            // access to variable annotated with TagetActivity
            for (Element e : env.getElementsAnnotatedWith(TargetActivity.class)) {
                TypeElement te = findEnclosingTypeElement(e);
                for (ExecutableElement ee : ElementFilter.methodsIn(te.getEnclosedElements())) {
                    log("[TargetActivity]ExecutableElement:" + ee);
                }
                for(VariableElement ve : ElementFilter.fieldsIn(te.getEnclosedElements())) {
                    log("[TargetActivity]VariableElement:"+ve);
                    List annotationMirrors = ve.getAnnotationMirrors();
                    if(annotationMirrors.isEmpty()) {
                        log("No AnnotationMirrors");
                    } else {
                        AnnotationMirror mirror = (AnnotationMirror)annotationMirrors.get(0);
                        log("AnnotationType: " + mirror.getAnnotationType());
                        for(ExecutableElement key : mirror.getElementValues().keySet()) {
                            AnnotationValue value = mirror.getElementValues().get(key);
                            log(String.format("key:%s, value:%s", key, value));
                            try {
                                log("getValueToString: "+value.getValue().toString());
                                log("getValue.getClass: "+value.getValue().getClass());
                                Class clazz = Class.forName(value.getValue().toString());
                                log("This is what I wanted!! -> class: "+clazz.getCanonicalName());
                            } catch (ClassNotFoundException error) {
                                log("ClassNotFoundException : "+error.getMessage());
                            }
                            String[] package_elements = value.getValue().toString().split("\\.",0);
                            writeTo(package_elements[package_elements.length-1]);  // last element is annotated className
                        }
                    }

                }
            }

        }
        return true;
    }

    private void writeTo(String classname) {
//        log("writeTo: class name is "+classname);
//        String genericClass = String.format("ActivityTestRule<%s>",classname);
//        log("generic class: "+genericClass);
        ClassName className = ClassName.get("android.support.test.rule", "ActivityTestRule");
        FieldSpec activityRule = FieldSpec.builder(className, "activityTestRule")
                .addAnnotation(Rule.class)
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .initializer("new ActivityTestRule<>(MainActivity.class,true,false)")
                .build();
        log("writeTo:" + activityRule.toString());
    }

    public static TypeElement findEnclosingTypeElement( Element e ) {
        while( e != null && !(e instanceof TypeElement) ) {
            e = e.getEnclosingElement();
        }
        return TypeElement.class.cast( e );
    }

    private void log(String msg) {
        System.out.println(msg);
    }

}
