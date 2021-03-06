package org.androidtransfuse.gen;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;
import org.androidtransfuse.analysis.astAnalyzer.VirtualProxyAspect;
import org.androidtransfuse.gen.proxy.VirtualProxyGenerator;
import org.androidtransfuse.model.InjectionNode;
import org.androidtransfuse.model.TypedExpression;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author John Ericksen
 */
public class InjectionFragmentGenerator {

    private final InjectionBuilderContextFactory injectionBuilderContextFactory;
    private final InjectionExpressionBuilder injectionExpressionBuilder;
    private final VirtualProxyGenerator virtualProxyGenerator;

    @Inject
    public InjectionFragmentGenerator(InjectionBuilderContextFactory injectionBuilderContextFactory,
                                      InjectionExpressionBuilder injectionExpressionBuilder,
                                      VirtualProxyGenerator virtualProxyGenerator) {
        this.injectionBuilderContextFactory = injectionBuilderContextFactory;
        this.injectionExpressionBuilder = injectionExpressionBuilder;
        this.virtualProxyGenerator = virtualProxyGenerator;
    }

    public Map<InjectionNode, TypedExpression> buildFragment(JBlock block, JDefinedClass definedClass, InjectionNode injectionNode) throws ClassNotFoundException, JClassAlreadyExistsException {

        InjectionBuilderContext injectionBuilderContext = injectionBuilderContextFactory.buildContext(block, definedClass);

        injectionExpressionBuilder.buildVariable(injectionBuilderContext, injectionNode);

        //loop over remaining injection chains, building cyclic graph
        while(!injectionBuilderContext.getProxyLoad().isEmpty()){
            List<InjectionNode> proxied = new ArrayList<InjectionNode>(injectionBuilderContext.getProxyLoad());
            injectionBuilderContext.getProxyLoad().clear();
            for (InjectionNode node : proxied) {
                injectionExpressionBuilder.setupInjectionRequirements(injectionBuilderContext, node);

                VirtualProxyAspect virtualProxyAspect = node.getAspect(VirtualProxyAspect.class);
                virtualProxyAspect.setProxyDefined(true); //signals the injection expression builder to avoid buliding a proxy
                TypedExpression proxyExpression = virtualProxyAspect.getProxyExpression();
                injectionBuilderContext.getVariableMap().remove(node);

                TypedExpression delegateVariable = injectionExpressionBuilder.buildVariable(injectionBuilderContext, node);

                //init proxy
                virtualProxyGenerator.initializeProxy(injectionBuilderContext, proxyExpression, delegateVariable);
            }
        }

        return injectionBuilderContext.getVariableMap();
    }
}
