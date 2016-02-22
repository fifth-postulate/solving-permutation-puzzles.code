package nl.fifth.postulate.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Permutation {

    private final List<Integer> image;

    public Permutation(Integer... image) {
        this(Arrays.asList(image));
    }

    private Permutation(List<Integer> image) {
        this.image = image;
    }

    public Permutation times(Permutation multiplicant) {
        return new Permutation(image.stream().map(element -> multiplicant.image.get(element)).collect(Collectors.toList()));
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
