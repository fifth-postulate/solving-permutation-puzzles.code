package nl.fifth.postulate.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class SLPRepresentationTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{ SLP.identity(), "Id"});
        data.add(new Object[]{ SLP.generator("a"), "a"});
        data.add(new Object[]{ SLP.generator("b"), "b"});
        data.add(new Object[]{ SLP.inverse(SLP.generator("a")), "a^-1"});
        data.add(new Object[]{ SLP.product(SLP.generator("a"), SLP.generator("b")), "ab"});
        data.add(new Object[]{ SLP.product(SLP.generator("a"), SLP.generator("a")), "a^2"});
        data.add(new Object[]{ SLP.product(SLP.generator("a"), SLP.inverse(SLP.generator("a"))), "Id"});
        return data;
    }

    private final SLP element;
    private final String expectedResult;

    public SLPRepresentationTest(SLP element, String expectedResult) {
        this.element = element;
        this.expectedResult = expectedResult;
    }

    @Test
    public void shouldHaveCorrectRepresentation(){
        assertThat(element.toString(), is(expectedResult));
    }
}
