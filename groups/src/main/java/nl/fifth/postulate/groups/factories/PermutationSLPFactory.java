package nl.fifth.postulate.groups.factories;

import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.SLP;
import nl.fifth.postulate.groups.Word;
import nl.fifth.postulate.groups.special.PermutationSLP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermutationSLPFactory implements GroupElementFactory<PermutationSLP> {
    public final Map<SLP, Word> morphism = new HashMap<SLP, Word>();

    @Override
    public PermutationSLP create(List<Integer> image, String symbol) {
        SLP generator = SLP.generator();
        morphism.put(generator, Word.generator(symbol));
        return new PermutationSLP(new Permutation(image), generator);
    }
}
