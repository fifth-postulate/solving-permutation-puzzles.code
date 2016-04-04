package nl.fifth.postulate.joy;

import nl.fifth.postulate.groups.Group;
import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.SLP;
import nl.fifth.postulate.groups.factories.PermutationSLPFactory;
import nl.fifth.postulate.groups.special.PermutationSLP;

import java.io.File;
import java.io.IOException;

import static nl.fifth.postulate.groups.Permutation.permutation;

public class Impossiball {
    public static void main(String[] args) throws IOException {
        PermutationSLPFactory factory = new PermutationSLPFactory();
        Group<PermutationSLP> group = Group.generatedBy(new File("content/groups/impossiball.group"), factory);
        Permutation g = permutation(1, 4, 2, 3, 0, 5, 6, 7, 8, 9);
        PermutationSLP candidate = new PermutationSLP(g, SLP.identity());

        PermutationSLP strip = group.strip(candidate);
        boolean isMember = strip.permutation.isIdentity();

        System.out.printf("element %s is%s a member\n", g, isMember ? "" : " not");
        if (isMember) {
            System.out.printf("%s", strip.slp.inverse().evaluateWith(factory.morphism));
        }
    }
}
