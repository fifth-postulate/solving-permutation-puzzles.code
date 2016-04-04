package nl.fifth.postulate.groups.factories;

import nl.fifth.postulate.groups.Permutation;

import java.util.List;

public class PermutationFactory implements GroupElementFactory<Permutation> {
    @Override
    public Permutation create(List<Integer> image, String symbol) {
        return new Permutation(image);
    }
}
