package nl.fifth.postulate.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static nl.fifth.postulate.groups.Permutation.permutation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class CycleNotationTest {
    @Parameterized.Parameters(name = "{0}.toString() == {1}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{permutation(0, 1, 2, 3), "Id"});

        data.add(new Object[]{permutation(1, 0, 2, 3), "(0 1)"});
        data.add(new Object[]{permutation(2, 1, 0, 3), "(0 2)"});
        data.add(new Object[]{permutation(3, 1, 2, 0), "(0 3)"});
        data.add(new Object[]{permutation(0, 2, 1, 3), "(1 2)"});
        data.add(new Object[]{permutation(0, 3, 2, 1), "(1 3)"});
        data.add(new Object[]{permutation(0, 1, 3, 2), "(2 3)"});

        data.add(new Object[]{permutation(1, 0, 3, 2), "(0 1)(2 3)"});
        data.add(new Object[]{permutation(2, 3, 0, 1), "(0 2)(1 3)"});
        data.add(new Object[]{permutation(3, 2, 1, 0), "(0 3)(1 2)"});

        data.add(new Object[]{permutation(1, 2, 0, 3), "(0 1 2)"});
        data.add(new Object[]{permutation(1, 3, 2, 0), "(0 1 3)"});
        data.add(new Object[]{permutation(2, 0, 1, 3), "(0 2 1)"});
        data.add(new Object[]{permutation(2, 1, 3, 0), "(0 2 3)"});
        data.add(new Object[]{permutation(3, 0, 2, 1), "(0 3 1)"});
        data.add(new Object[]{permutation(3, 1, 0, 2), "(0 3 2)"});
        data.add(new Object[]{permutation(0, 2, 3, 1), "(1 2 3)"});
        data.add(new Object[]{permutation(0, 3, 1, 2), "(1 3 2)"});

        data.add(new Object[]{permutation(1, 2, 3, 0), "(0 1 2 3)"});
        data.add(new Object[]{permutation(1, 3, 0, 2), "(0 1 3 2)"});
        data.add(new Object[]{permutation(2, 3, 1, 0), "(0 2 1 3)"});
        data.add(new Object[]{permutation(2, 0, 3, 1), "(0 2 3 1)"});
        data.add(new Object[]{permutation(3, 2, 0, 1), "(0 3 1 2)"});
        data.add(new Object[]{permutation(3, 0, 1, 2), "(0 3 2 1)"});

        return data;
    }
    private final Permutation permutation;
    private final String result;

    public CycleNotationTest(Permutation permutation, String result) {
        this.permutation = permutation;
        this.result = result;
    }

    @Test
    public void shouldBeCorrectForPermutation() {
        assertThat(permutation.toString(), is(result));
    }


}
