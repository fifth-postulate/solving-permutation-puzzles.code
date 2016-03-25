package nl.fifth.postulate.groups;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class SLP implements GroupElement<SLP> {
    public static SLP identity() {
        return new Identity();
    }

    public static SLP generator(String symbol) {
        return new Generator(symbol);
    }

    public static SLP product(SLP left, SLP right) {
        return new Product(left, right);
    }

    public static SLP inverse(SLP element) {
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

    private static class Identity extends SLP {

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
    }

    private static class Generator extends SLP {
        private final String symbol;

        public Generator(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Generator generator = (Generator) o;

            return symbol.equals(generator.symbol);

        }

        @Override
        public int hashCode() {
            return symbol.hashCode();
        }

        @Override
        public boolean isIdentity() {
            return false;
        }
    }

    private static class Product extends SLP {
        private final SLP left;
        private final SLP right;

        public Product(SLP left, SLP right) {
            this.left = left;
            this.right = right;
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

    private static class Inverse extends SLP {

        protected final SLP element;

        public Inverse(SLP element) {
            this.element = element;
        }

        @Override
        public boolean isIdentity() {
            return element.isIdentity();
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
