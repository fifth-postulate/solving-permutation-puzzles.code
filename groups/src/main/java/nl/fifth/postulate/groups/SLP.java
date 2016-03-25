package nl.fifth.postulate.groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        RepresentationVisitor visitor = new RepresentationVisitor();
        this.visit(visitor);
        return visitor.toString();
    }


    public void visit(Visitor visitor) {
        if (this instanceof Identity) {
            visitor.visit((Identity) this);
        } else if (this instanceof Generator) {
            visitor.visit((Generator) this);
        } else if (this instanceof Inverse) {
            visitor.visit((Inverse) this);
        } else if (this instanceof Product) {
            visitor.visit((Product) this);
        } else {
            throw new IllegalStateException();
        }
    }

    public interface Visitor {
        void visit(Identity identity);
        void visit(Generator generator);
        void visit(Inverse inverse);
        void visit(Product product);
    }

    protected static class Identity extends SLP {

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

    protected static class Generator extends SLP {
        public final String symbol;

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

    protected static class Product extends SLP {
        protected final SLP left;
        protected final SLP right;

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

class RepresentationVisitor implements SLP.Visitor {
    private static class SLPElementPair {
        public String generator;
        public int power;

        public SLPElementPair(String generator, int power) {
            this.generator = generator;
            this.power = power;
        }

        public SLPElementPair invert() {
            return new SLPElementPair(generator, -power);
        }

        public void collect(SLPElementPair pair) {
            this.power += pair.power;
        }
    }

    private List<SLPElementPair> pairs = new ArrayList<SLPElementPair>();

    @Override
    public void visit(SLP.Identity identity) {
        /* do nothing */
    }

    @Override
    public void visit(SLP.Generator generator) {
        pairs.add(new SLPElementPair(generator.symbol, 1));

    }

    @Override
    public void visit(SLP.Inverse inverse) {
        RepresentationVisitor subVisitor = new RepresentationVisitor();
        inverse.element.visit(subVisitor);
        List<SLPElementPair> subPairs = subVisitor.pairs;
        Collections.reverse(subPairs);
        pairs.addAll(subPairs.stream().map(pair -> pair.invert()).collect(Collectors.toList()));
    }

    @Override
    public void visit(SLP.Product product) {
        product.left.visit(this);
        product.right.visit(this);
    }

    private List<SLPElementPair> normalize() {
        if (pairs.size() <= 1) { return pairs; }
        List<SLPElementPair> normalized = new ArrayList<SLPElementPair>();
        SLPElementPair current = pairs.get(0);
        for (int index = 1; index < pairs.size(); index++) {
            SLPElementPair pair = pairs.get(index);
            if (pair.generator.equals(current.generator)) {
                current.collect(pair);
            } else {
                if (current.power != 0) {
                    normalized.add(current);
                }
                current = pair;
            }
        }
        if (current.power != 0) {
            normalized.add(current);
        }
        return normalized;
    }

    @Override
    public String toString() {
        List<SLPElementPair> normalized = this.normalize();
        if (normalized.size() == 0) {
            return "Id";
        } else {
            StringBuilder builder = new StringBuilder();
            for (SLPElementPair pair: normalized) {
                builder.append(pair.generator);
                if (pair.power != 1) {
                    builder.append("^");
                    builder.append(pair.power);
                }
            }
            return builder.toString();
        }
    }

}