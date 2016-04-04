package nl.fifth.postulate.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static nl.fifth.postulate.groups.Permutation.permutation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class SLPEvaluationTest<T extends GroupElement<T>> {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Permutation g = permutation(1, 2, 3, 4, 5, 6, 0);
        Permutation h = permutation(1, 2, 3, 0, 5, 6, 4);
        SLP slpG = SLP.generator();
        SLP slpH = SLP.generator();
        Map<SLP, Permutation> morphismImage = new HashMap<SLP, Permutation>();
        morphismImage.put(slpG, g);
        morphismImage.put(slpH, h);
        Morphism morphism = new Morphism() {
            @Override
            public GroupElement identity() {
                return g.times(g.inverse());
            }

            @Override
            public GroupElement get(SLP element) {
                return morphismImage.get(element);
            }
        };
        return Arrays.asList(
                new Object[]{ slpG, g, morphism },
                new Object[]{ SLP.inverse(slpH), h.inverse(), morphism },
                new Object[]{ SLP.product(slpG, slpH), g.times(h), morphism },
                new Object[]{ SLP.product(slpG, SLP.inverse(slpG)), g.times(g.inverse()), morphism }
        );
    }

    private final SLP slp;
    private final T expected;
    private final Morphism<T> morphism;

    public SLPEvaluationTest(SLP slp, T expected, Morphism<T> morphism) {
        this.slp = slp;
        this.expected = expected;
        this.morphism = morphism;
    }

    @Test
    public void shouldEvalutateCorrectly() {
        T evaluation = slp.evaluateWith(morphism);

        assertThat(evaluation, is(expected));
    }


}
