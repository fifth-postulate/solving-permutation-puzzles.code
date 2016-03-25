package nl.fifth.postulate.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class WordRepresentationTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Word u = Word.generator("u");
        Word v = Word.generator("v");

        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{ u, "u"});
        data.add(new Object[]{ u.times(u), "u^2"});
        data.add(new Object[]{ u.times(v), "uv"});
        data.add(new Object[]{ u.times(u.inverse()), "Id"});
        return data;
    }

    private final Word element;
    private final String expectedResult;

    public WordRepresentationTest(Word element, String expectedResult) {
        this.element = element;
        this.expectedResult = expectedResult;
    }

    @Test
    public void shouldHaveCorrectRepresentation(){
        assertThat(element.toString(), is(expectedResult));
    }
}
