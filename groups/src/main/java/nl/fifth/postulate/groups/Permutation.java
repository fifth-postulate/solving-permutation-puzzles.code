package nl.fifth.postulate.groups;

import java.util.Arrays;
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
