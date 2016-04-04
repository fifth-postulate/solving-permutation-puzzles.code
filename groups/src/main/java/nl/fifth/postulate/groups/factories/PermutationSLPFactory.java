package nl.fifth.postulate.groups.factories;

import nl.fifth.postulate.groups.*;
import nl.fifth.postulate.groups.special.PermutationSLP;

import java.util.List;

public class PermutationSLPFactory implements GroupElementFactory<PermutationSLP> {
    public final WordMorphism morphism = new WordMorphism();

    @Override
    public PermutationSLP create(List<Integer> image, String symbol) {
        SLP generator = SLP.generator();
        morphism.put(generator, Word.generator(symbol));
        return new PermutationSLP(new Permutation(image), generator);
    }
}
