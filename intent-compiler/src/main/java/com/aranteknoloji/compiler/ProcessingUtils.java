package com.aranteknoloji.compiler;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

class ProcessingUtils {

    private ProcessingUtils() {
    }

    static Set<TypeElement> getTypeElementsToProcess(Set<? extends Element> elements,
                                                     Set<? extends Element> supportedAnnotations) {
        Set<TypeElement> typeElements = new HashSet<>();
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                    for (Element annotation : supportedAnnotations) {
                        if (annotationMirror.getAnnotationType().asElement().equals(annotation)) {
                            typeElements.add((TypeElement) element);
                            break;
                        }
                    }
                }
            }
        }
        return typeElements;
    }
}
