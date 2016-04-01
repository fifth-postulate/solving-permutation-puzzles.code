package nl.fifth.postulate.groups;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SLPTest {
    @Test
    public void multiplicationShouldBePerformedCorrectly() {
        SLP p = SLP.generator();
        SLP q = SLP.generator();

        SLP product = p.times(q);

        assertThat(product, is(SLP.product(p, q)));
    }

    @Test
    public void shouldHaveInverse() {
        SLP p = SLP.generator().times(SLP.generator());

        SLP product = p.times(p.inverse());

        assertNotNull(product);
    }
}

