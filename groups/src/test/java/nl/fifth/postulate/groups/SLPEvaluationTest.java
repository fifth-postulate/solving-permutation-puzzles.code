package nl.fifth.postulate.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static nl.fifth.postulate.groups.Permutation.permutation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class SLPEvaluationTest<T extends GroupElement<T>> {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Permutation g = permutation(1, 2, 3, 4, 5, 6, 0);
        Permutation h = permutation(1, 2, 3, 0, 5, 6, 4);
        return Arrays.asList(
                new Object[]{ SLP.generator(g), g },
                new Object[]{ SLP.inverse(SLP.generator(h)), h.inverse() },
                new Object[]{ SLP.product(SLP.generator(g), SLP.generator(h)), g.times(h) },
                new Object[]{ SLP.product(SLP.generator(g), SLP.inverse(SLP.generator(g))), g.times(g.inverse()) }
        );
    }

    private final SLP<T> slp;
    private final T expected;

    public SLPEvaluationTest(SLP<T> slp, T expected) {
        this.slp = slp;
        this.expected = expected;
    }

    @Test
    public void shouldEvalutateCorrectly() {
        T evaluation = slp.evaluate();

        assertThat(evaluation, is(expected));
    }


}
