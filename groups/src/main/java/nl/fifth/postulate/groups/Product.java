package nl.fifth.postulate.groups;

public class Product<U extends GroupElement<U>, V extends GroupElement<V>> implements GroupElement<Product<U, V>> {
    private final U u;
    private final V v;

    public Product(U u, V v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean isIdentity() {
        return u.isIdentity() && v.isIdentity();
    }

    @Override
    public Product<U, V> times(Product<U, V> multiplicand) {
        return new Product(u.times(multiplicand.u), v.times(multiplicand.v));
    }

    @Override
    public Product<U, V> inverse() {
        return new Product(u.inverse(), v.inverse());
    }

    public U first() {
        return u;
    }

    public V second() {
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product<?, ?> product = (Product<?, ?>) o;

        if (!u.equals(product.u)) return false;
        return v.equals(product.v);

    }

    @Override
    public int hashCode() {
        int result = u.hashCode();
        result = 31 * result + v.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("[ %s, %s ]", u, v);
    }
}
