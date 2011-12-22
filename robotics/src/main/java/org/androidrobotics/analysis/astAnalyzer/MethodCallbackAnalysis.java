package org.androidrobotics.analysis.astAnalyzer;

import org.androidrobotics.analysis.AnalysisContext;
import org.androidrobotics.analysis.adapter.ASTMethod;
import org.androidrobotics.annotations.OnCreate;
import org.androidrobotics.annotations.OnTouch;
import org.androidrobotics.model.InjectionNode;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author John Ericksen
 */
public class MethodCallbackAnalysis extends ASTAnalysisAdaptor {

    private Map<Class<?>, String> methodAnnotations = new HashMap<Class<?>, String>();

    public MethodCallbackAnalysis() {
        methodAnnotations.put(OnCreate.class, "onCreate");
        methodAnnotations.put(OnTouch.class, "onTouch");
    }

    @Override
    public void analyzeMethod(InjectionNode injectionNode, ASTMethod astMethod, AnalysisContext context) {
        for (Class<?> annotation : methodAnnotations.keySet()) {
            if (astMethod.isAnnotated((Class<Annotation>) annotation)) {
                addMethod(injectionNode, annotation, astMethod);
            }
        }
    }

    private void addMethod(InjectionNode injectionNode, Class<?> annotation, ASTMethod astMethod) {

        if (!injectionNode.containsAspect(MethodCallbackAspect.class)) {
            injectionNode.addAspect(new MethodCallbackAspect());
        }
        MethodCallbackAspect methodCallbackToken = injectionNode.getAspect(MethodCallbackAspect.class);

        methodCallbackToken.addMethod(methodAnnotations.get(annotation), astMethod);

    }
}