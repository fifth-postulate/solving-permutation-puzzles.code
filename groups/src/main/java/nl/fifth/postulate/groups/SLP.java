package nl.fifth.postulate.groups;

public abstract class SLP implements GroupElement<SLP> {
    public static SLP identity() {
        return new Identity();
    }

    public static SLP generator() {
        return new Generator();
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

    public abstract <T extends GroupElement<T>> T evaluateWith(Morphism<T> morphism);

    protected static class Identity extends SLP {

        @Override
        public boolean isIdentity() {
            return true;
        }

        @Override
        public<T extends GroupElement<T>> T evaluateWith(Morphism<T> morphism) {
            return morphism.identity();
        }

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
    }

    protected static class Generator extends SLP {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            return false;
        }

        @Override
        public <T extends GroupElement<T>> T evaluateWith(Morphism<T> morphism) {
            return morphism.get(this);
        }

        @Override
        public int hashCode() {
            return 31;
        }

        @Override
        public boolean isIdentity() {
            return false;
        }
    }

    protected static class Product extends SLP {
        protected final SLP left;
        protected final SLP right;

        public Product(SLP left, SLP right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public <T extends GroupElement<T>> T evaluateWith(Morphism<T> morphism) {
            return left.evaluateWith(morphism).times(right.evaluateWith(morphism));
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

    protected static class Inverse extends SLP {

        protected final SLP element;

        public Inverse(SLP element) {
            this.element = element;
        }

        @Override
        public boolean isIdentity() {
            return element.isIdentity();
        }

        @Override
        public <T extends GroupElement<T>> T evaluateWith(Morphism<T> morphism) {
            return element.evaluateWith(morphism).inverse();
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