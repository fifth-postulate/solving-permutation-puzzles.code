package nl.fifth.postulate.groups;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
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

    @Test
    public void shouldDeterrmineMembership() {
        Group group = Group.generatedBy(permutation(1, 2, 3, 4, 0), permutation(0, 4, 3, 2, 1));
        Permutation member = permutation(4, 3, 2, 1, 0);
        Permutation nonMember = permutation(1, 0, 2, 3, 4);

        assertTrue(group.isMember(member));
        assertFalse(group.isMember(nonMember));

    }
}
