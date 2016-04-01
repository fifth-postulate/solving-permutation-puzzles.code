package nl.fifth.postulate.groups;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SLP<T extends GroupElement<T>> implements GroupElement<SLP<T>> {
    public static <T extends GroupElement<T>> SLP<T> identity() {
        return new Identity();
    }

    public static <T extends GroupElement<T>> SLP<T> generator(T symbol) {
        return new Generator(symbol);
    }

    public static <T extends GroupElement<T>> SLP product(SLP left, SLP right) {
        return new Product(left, right);
    }

    public static <T extends GroupElement<T>> SLP inverse(SLP element) {
        return new Inverse(element);
    }

    public SLP times(SLP element) {
        return product(this, element);
    }

    @Override
    public SLP inverse() {
        return inverse(this);
    }

    /***
     * SLP elements are not used for identity checks
     *
     * @return {@code false}
     */
    @Override
    public boolean isIdentity() { return false; }

    public abstract T evaluate();

    protected static class Identity<T extends GroupElement<T>> extends SLP<T> {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            return true;

        }

        @Override
        public int hashCode() {
            return 31;
        }

        @Override
        public boolean isIdentity() {
            return true;
        }

        @Override
        public T evaluate() {
            throw new NotImplementedException(/* How to create an identity element for T */);
        }
    }

    protected static class Generator<T extends GroupElement<T>> extends SLP<T> {
        public final T generator;

        public Generator(T generator) {
            this.generator = generator;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Generator generator = (Generator) o;

            return generator.equals(generator.generator);

        }

        @Override
        public T evaluate() {
            return generator;
        }

        @Override
        public int hashCode() {
            return generator.hashCode();
        }

        @Override
        public boolean isIdentity() {
            return false;
        }
    }

    protected static class Product<T extends GroupElement<T>> extends SLP<T> {
        protected final SLP<T> left;
        protected final SLP<T> right;

        public Product(SLP<T> left, SLP<T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public T evaluate() {
            return left.evaluate().times(right.evaluate());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Product product = (Product) o;

            if (!left.equals(product.left)) return false;
            return right.equals(product.right);

        }

        @Override
        public int hashCode() {
            int result = left.hashCode();
            result = 31 * result + right.hashCode();
            return result;
        }
    }

    protected static class Inverse<T extends GroupElement<T>> extends SLP<T> {

        protected final SLP<T> element;

        public Inverse(SLP<T> element) {
            this.element = element;
        }

        @Override
        public boolean isIdentity() {
            return element.isIdentity();
        }

        @Override
        public T evaluate() {
            return element.evaluate().inverse();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Inverse inverse = (Inverse) o;

            return element.equals(inverse.element);

        }

        @Override
        public int hashCode() {
            return element.hashCode();
        }
    }
}