package nl.fifth.postulate.groups;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PermutationTest {
    @Test
    public void multiplicationShouldBeFromLeftToRight() {
        Permutation p = new Permutation(1, 0, 2);
        Permutation q = new Permutation(0, 2, 1);

        Permutation product = p.times(q);

        assertThat(product, is(new Permutation(2, 0, 1)));
    }
}

