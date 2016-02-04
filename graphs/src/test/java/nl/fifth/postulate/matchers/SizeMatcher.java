package nl.fifth.postulate.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

public class SizeMatcher extends TypeSafeMatcher<Collection<?>> {
    public static SizeMatcher hasSize(int expectedSize) {
        return new SizeMatcher(expectedSize);
    }

    private final int expectedSize;

    private SizeMatcher(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    protected boolean matchesSafely(Collection<?> objects) {
        return objects.size() == expectedSize;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("collection does not have size ");
        description.appendValue(expectedSize);
    }
}
