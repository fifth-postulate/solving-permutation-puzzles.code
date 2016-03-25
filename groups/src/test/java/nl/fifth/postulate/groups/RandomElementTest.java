package nl.fifth.postulate.groups;

import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertNotNull;
import static nl.fifth.postulate.groups.Permutation.permutation;

public class RandomElementTest {
    @Test
    public void shouldCreateARandomElement() {
        Group<Permutation> group = Group.generatedBy(permutation(1, 2, 3, 4, 5, 0));

        Permutation permutation = group.randomElement(new Random());

        assertNotNull(permutation);
    }
}

