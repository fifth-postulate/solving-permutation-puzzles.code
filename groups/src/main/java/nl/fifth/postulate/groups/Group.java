package nl.fifth.postulate.groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Group<T extends GroupElement<T> & GroupAction> {
    public static Group<Permutation> generatedBy(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Permutation> generators = new ArrayList<Permutation>();
        String line;
        while ((line = reader.readLine()) != null) {
            List<String> strings = Arrays.asList(line.split("\\s*,\\s*"));
            List<Integer> images = strings.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
            Permutation generator = new Permutation(images);
            generators.add(generator);
        }
        return new Group(generators);
    }

    public static <T extends GroupElement<T> & GroupAction>Group<T> generatedBy(T... generators) {
        return new Group(Arrays.asList(generators));
    }
    private List<Integer> bases = new ArrayList<Integer>();
    private List<BaseStrongGeneratorLevel<T>> levels = new ArrayList<BaseStrongGeneratorLevel<T>>();

    public Group(List<T> generators) {
        while (generators.size() > 0) {
            Integer base = findBase(bases, generators);
            bases.add(base);
            BaseStrongGeneratorLevel level = new BaseStrongGeneratorLevel(base, generators);
            levels.add(level);
            generators = level.stabilizers();
        }
    }

    private Integer findBase(List<Integer> bases, List<T> generators) {
        Integer base = null;
        candidateLoop: for (int candidate = 0; candidate < generators.get(0).degree(); candidate++) {
            for (T generator: generators) {
                if (generator.actOn(candidate) != candidate) {
                    base = candidate;
                    break candidateLoop;
                }
            }
        }
        return base;
    }


    public Integer size() {
        int size = 1;
        for (BaseStrongGeneratorLevel level: levels) {
            size *= level.orbit().size();
        }
        return size;
    }

    public boolean isMember(T candidate) {
        candidate = strip(candidate);
        return candidate.isIdentity();
    }

    public T strip(T candidate) {
        for (BaseStrongGeneratorLevel<T> level: levels) {
            if (level.hasTransversalFor(candidate)) {
                candidate = candidate.times(level.transversalFor(candidate).inverse());
            } else {
                break;
            }
        }
        return candidate;
    }

    public T randomElement(Random random) {
        T product = levels.get(0).identity();
        for (BaseStrongGeneratorLevel<T> level: levels) {
            product = product.times(level.randomElement(random));
        }
        return product;
    }
}


class BaseStrongGeneratorLevel<T extends GroupElement<T> & GroupAction> {
    private final Map<Integer, T> transversals  = new HashMap<Integer, T>();
    private final List<T> stabilizers = new ArrayList<T>();
    private final Integer base;
    private final List<T> generators;

    public BaseStrongGeneratorLevel(Integer base, List<T> generators) {
        this.base = base;
        this.generators = generators;
        calculateTransversals();
    }

    private void calculateTransversals() {
        PriorityQueue<Integer> toVisit = new PriorityQueue<Integer>();
        toVisit.add(this.base); transversals.put(this.base, identity());
        while (!toVisit.isEmpty()) {
            Integer element = toVisit.poll();
            for (T generator: generators) {
                Integer image = generator.actOn(element);
                if (!transversals.containsKey(image)) {
                    T transversal = transversals.get(element).times(generator);
                    transversals.put(image, transversal);
                    toVisit.add(image);
                } else {
                    T stabilizer = transversals.get(element).times(generator).times(transversals.get(image).inverse());
                    if (!stabilizer.isIdentity()) {
                        stabilizers.add(stabilizer);
                    }
                }
            }

        }
    }

    protected T identity() {
        T anyGenerator = generators.get(0);
        return anyGenerator.times(anyGenerator.inverse());
    }

    public List<T> stabilizers() {
        return Collections.unmodifiableList(stabilizers);
    }

    public Set<Integer> orbit() {
        return transversals.keySet();
    }

    public boolean hasTransversalFor(T permutation) {
        return transversals.containsKey(permutation.actOn(base));
    }

    public T transversalFor(T permutation) {
        return transversals.get(permutation.actOn(base));
    }

    public T randomElement(Random random) {
        List<Integer> orbit = new ArrayList<Integer>(this.orbit());
        Integer randomOrbitElement = orbit.get(random.nextInt(orbit.size()));
        return transversals.get(randomOrbitElement);
    }
}