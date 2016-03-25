package nl.fifth.postulate.groups;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static nl.fifth.postulate.groups.Permutation.permutation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProductTest {
    @Test
    public void shouldMultiply() {
        Permutation p = permutation(1, 2, 3, 4, 0);
        Word a = Word.generator("a");
        Product<Permutation, Word> u = new Product(p, a);
        Permutation q = permutation(1, 0, 2, 3, 4);
        Word b = Word.generator("b");
        Product<Permutation, Word> v = new Product(q, b);

        Product<Permutation, Word> product = u.times(v);

        assertThat(product, is(new Product(p.times(q), a.times(b))));
    }

    @Test
    public void shouldHaveInverse() {
        Product<Permutation, Word> p = new Product(permutation(1, 2, 3, 4, 5, 0), Word.generator("a"));

        Product<Permutation, Word> product = p.times(p.inverse());

        assertTrue(product.isIdentity());
    }


}

