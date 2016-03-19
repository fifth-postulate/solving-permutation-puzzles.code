package nl.fifth.postulate.groups;

public interface GroupElement<T extends GroupElement<T>> {
    boolean isIdentity();
    T times(T multiplicand);
    T inverse();
}
