package org.androidtransfuse.analysis;

import org.androidtransfuse.TransfuseTestInjector;
import org.androidtransfuse.analysis.adapter.ASTClassFactory;
import org.androidtransfuse.analysis.adapter.ASTMethod;
import org.androidtransfuse.analysis.adapter.ASTType;
import org.androidtransfuse.analysis.adapter.MethodSignature;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author John Ericksen
 */
public class MethodSignatureTest {

    @Inject
    private ASTClassFactory astClassFactory;
    private ASTMethod methodOne;
    private ASTMethod methodTwo;
    private ASTMethod methodThree;

    public class MethodSignatureTarget {
        public void methodOne(String arg) {
        }

        public void methodTwo(int[] args) {
        }

        public long methodThree() {
            return 0;
        }
    }

    @Before
    public void setup() {
        TransfuseTestInjector.inject(this);

        ASTType targetType = astClassFactory.buildASTClassType(MethodSignatureTarget.class);

        Iterator<ASTMethod> iterator = targetType.getMethods().iterator();

        methodOne = iterator.next();
        methodTwo = iterator.next();
        methodThree = iterator.next();
    }

    @Test
    public void testEquality() {

        assertEquals(new MethodSignature(methodOne), new MethodSignature(methodOne));
        assertFalse(new MethodSignature(methodOne).equals(new MethodSignature(methodTwo)));
        assertFalse(new MethodSignature(methodTwo).equals(new MethodSignature(methodThree)));
    }

    @Test
    public void testSet() {
        Set<MethodSignature> signatures = new HashSet<MethodSignature>();

        signatures.add(new MethodSignature(methodOne));

        assertTrue(signatures.contains(new MethodSignature(methodOne)));
        assertFalse(signatures.contains(new MethodSignature(methodTwo)));
    }
}
