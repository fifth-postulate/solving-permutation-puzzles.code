package nl.fifth.postulate.groups.factories;

import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.special.PermutationWord;

import java.util.List;

import static nl.fifth.postulate.groups.Word.generator;

public class PermutationWordFactory implements GroupElementFactory<PermutationWord> {
    @Override
    public PermutationWord create(List<Integer> image, String symbol) {
        return new PermutationWord(new Permutation(image), generator(symbol));
    }
}
