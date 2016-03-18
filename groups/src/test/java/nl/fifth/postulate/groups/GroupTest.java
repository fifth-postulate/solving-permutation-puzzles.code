package nl.fifth.postulate.groups;

import org.junit.Test;

import static nl.fifth.postulate.groups.Permutation.permutation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GroupTest {

    @Test
    public void shouldDetermineSize() {
        Group group = Group.generatedBy(permutation(1, 2, 3, 4, 0), permutation(0, 4, 3, 2, 1));

        Integer size = group.size();

        assertThat(size, is(10));
    }
}
