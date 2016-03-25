package nl.fifth.postulate.groups;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SLPTest {
    @Test
    public void multiplicationShouldBePerformedCorrectly() {
        SLP p = SLP.generator("a");
        SLP q = SLP.generator("b");

        SLP product = p.times(q);

        assertThat(product, is(SLP.product(p, q)));
    }

    @Test
    public void shouldHaveInverse() {
        SLP p = SLP.generator("a").times(SLP.generator("b"));

        SLP product = p.times(p.inverse());

        assertNotNull(product);
    }
}

