package nl.fifth.postulate.joy;

import nl.fifth.postulate.groups.Group;
import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.factories.PermutationFactory;

import java.io.File;
import java.io.IOException;

import static nl.fifth.postulate.groups.Permutation.permutation;

public class Impossiball {
    public static void main(String[] args) throws IOException {
        Group<Permutation> group = Group.generatedBy(new File("content/groups/impossiball.group"), new PermutationFactory());
        Permutation g = permutation(1, 4, 2, 3, 0, 5, 6, 7, 8, 9);

        boolean isMember = group.isMember(g);

        System.out.printf("element %s is%s a member\n", g, isMember ? "" : " not");
    }
}
