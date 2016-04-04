package nl.fifth.postulate.groups.special;

import nl.fifth.postulate.groups.GroupAction;
import nl.fifth.postulate.groups.GroupElement;
import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.SLP;

public class PermutationSLP implements GroupElement<PermutationSLP>, GroupAction {
    public final Permutation permutation;
    public final SLP slp;

    public PermutationSLP(Permutation permutation, SLP slp) {
        this.permutation = permutation;
        this.slp = slp;
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
    public boolean isIdentity() {
        return permutation.isIdentity();
    }

    @Override
    public PermutationSLP times(PermutationSLP multiplicand) {
        return new PermutationSLP(this.permutation.times(multiplicand.permutation), this.slp.times(multiplicand.slp));
    }

    @Override
    public PermutationSLP inverse() {
        return new PermutationSLP(permutation.inverse(), slp.inverse());
    }
}
