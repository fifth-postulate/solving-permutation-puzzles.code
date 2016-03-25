package nl.fifth.postulate.groups.special;

import nl.fifth.postulate.groups.*;

public class PermutationWord implements GroupElement<PermutationWord>, GroupAction {
    public final Permutation permutation;
    public final Word word;

    public PermutationWord(Permutation permutation, Word word) {
        this.permutation = permutation;
        this.word = word;
    }

    @Override
    public boolean isIdentity() {
        return permutation.isIdentity();
    }

    @Override
    public PermutationWord times(PermutationWord multiplicand) {
        return new PermutationWord(permutation.times(multiplicand.permutation), word.times(multiplicand.word));
    }

    @Override
    public PermutationWord inverse() {
        return new PermutationWord(permutation.inverse(), word.inverse());
    }

    @Override
    public Integer actOn(Integer element) {
        return permutation.actOn(element);
    }

    @Override
    public Integer degree() {
        return permutation.degree();
    }

    @Override
    public String toString() {
        return String.format("[ %s, %s ]", permutation, word);
    }
}
