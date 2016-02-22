package nl.fifth.postulate.groups;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class PermutationTest {
    @Test
    public void multiplicationShouldBeFromLeftToRight() {
        Permutation p = new Permutation(1, 0, 2);
        Permutation q = new Permutation(0, 2, 1);

        Permutation product = p.times(q);

        assertThat(product, is(new Permutation(2, 0, 1)));
    }

    @Test
    public void shouldKnowIfItIsIdentity() {
        Permutation p = new Permutation(0, 1, 2);
        Permutation q = new Permutation(1, 0, 2);

        assertThat(p.isIdentity(), is(true));
        assertThat(q.isIdentity(), is(not(true)));
    }

    @Test
    public void shouldHaveInverse() {
        Permutation p = new Permutation(1, 2, 3, 4, 0);

        Permutation product = p.times(p.inverse());

        assertThat(product.isIdentity(), is(true));
    }
}

