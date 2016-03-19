package nl.fifth.postulate.groups;

import java.util.*;
import java.util.stream.Collectors;

public class Permutation implements GroupElement<Permutation>, GroupAction {
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

    @Override
    public Permutation times(Permutation multiplicand) {
        return new Permutation(image.stream().map(element -> multiplicand.image.get(element)).collect(Collectors.toList()));
    }


    @Override
    public boolean isIdentity() {
        for (int index = 0; index < image.size(); index++) {
            if (image.get(index) != index) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Permutation inverse() {
        List<Integer> inverseImage = new ArrayList<Integer>();
        inverseImage.addAll(image);
        for (int index = 0; index < image.size(); index++) {
            inverseImage.set(image.get(index), index);
        }
        return new Permutation(inverseImage);
    }

    @Override
    public Integer actOn(Integer element) {
        return image.get(element);
    }

    @Override
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
