package nl.fifth.postulate.groups;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SLPTest {
    @Test
    public void multiplicationShouldBePerformedCorrectly() {
        SLP<Word> p = SLP.generator(Word.generator("a"));
        SLP<Word> q = SLP.generator(Word.generator("b"));

        SLP<Word> product = p.times(q);

        assertThat(product, is(SLP.product(p, q)));
    }

    @Test
    public void shouldHaveInverse() {
        SLP<Word> p = SLP.generator(Word.generator("a")).times(SLP.generator(Word.generator("b")));

        SLP product = p.times(p.inverse());

        assertNotNull(product);
    }
}

