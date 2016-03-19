package nl.fifth.postulate.groups;

import java.util.*;
import java.util.stream.Collectors;

public class Permutation {
    public static Permutation permutation(Integer... image) {
        return new Permutation(image);
    }

    private final List<Integer> image;

    public Permutation(Integer... image) {
        this(Arrays.asList(image));
    }

    private Permutation(List<Integer> image) {
        this.image = image;
    }

    public Permutation times(Permutation multiplicand) {
        return new Permutation(image.stream().map(element -> multiplicand.image.get(element)).collect(Collectors.toList()));
    }


    public boolean isIdentity() {
        for (int index = 0; index < image.size(); index++) {
            if (image.get(index) != index) {
                return false;
            }
        }
        return true;
    }

    public Permutation inverse() {
        List<Integer> inverseImage = new ArrayList<Integer>();
        inverseImage.addAll(image);
        for (int index = 0; index < image.size(); index++) {
            inverseImage.set(image.get(index), index);
        }
        return new Permutation(inverseImage);
    }

    public Set<Integer> fix() {
        Set<Integer> fixedElements = new HashSet<>();
        for (int element = 0; element < image.size(); element++) {
            if (image.get(element) == element) {
                fixedElements.add(element);
            }
        }
        return fixedElements;
    }

    public Integer actOn(int element) {
        return image.get(element);
    }

    public Integer degree() {
        return image.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permutation that = (Permutation) o;

        return image.equals(that.image);

    }

    @Override
    public int hashCode() {
        return image.hashCode();
    }

}
