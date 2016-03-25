package nl.fifth.postulate.groups;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WordTest {
    @Test
    public void shouldMultiply() {
        Word u = Word.generator("a");
        Word v = Word.generator("b");

        Word product = u.times(v);

        assertThat(product.toString(), is("ab"));
    }

    @Test
    public void shouldHaveInverse() {
        Word u = Word.generator("a");

        Word product = u.times(u.inverse());

        assertTrue(product.isIdentity());
    }
}
