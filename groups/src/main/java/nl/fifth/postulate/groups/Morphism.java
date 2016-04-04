package nl.fifth.postulate.groups;

public interface Morphism<T extends GroupElement<T>> {
    T identity();
    T get(SLP element);
}
