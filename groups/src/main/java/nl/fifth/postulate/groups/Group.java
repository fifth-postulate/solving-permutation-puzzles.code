package nl.fifth.postulate.groups;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Group {
    public static Group generatedBy(File file) throws IOException {
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

    public static Group generatedBy(Permutation... generators) {
        return new Group(Arrays.asList(generators));
    }
    private List<Integer> bases = new ArrayList<Integer>();
    private List<BaseStrongGeneratorLevel> levels = new ArrayList<BaseStrongGeneratorLevel>();

    public Group(List<Permutation> generators) {
        while (generators.size() > 0) {
            Integer base = findBase(bases, generators);
            bases.add(base);
            BaseStrongGeneratorLevel level = new BaseStrongGeneratorLevel(base, generators);
            levels.add(level);
            generators = level.stabilizers();
        }
    }

    private Integer findBase(List<Integer> bases, List<Permutation> generators) {
        Integer base = null;
        candidateLoop: for (int candidate = 0; candidate < generators.get(0).degree(); candidate++) {
            for (Permutation generator: generators) {
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

    public boolean isMember(Permutation candidate) {
        for (BaseStrongGeneratorLevel level: levels) {
            if (level.hasTransversalFor(candidate)) {
                candidate = candidate.times(level.transversalFor(candidate).inverse());
            } else {
                return false;
            }
        }
        if (candidate.isIdentity()) {
            return true;
        }
        return false;
    }
}


class BaseStrongGeneratorLevel {
    private final Map<Integer, Permutation> transversals  = new HashMap<Integer, Permutation>();
    private final List<Permutation> stabilizers = new ArrayList<Permutation>();
    private final Integer base;
    private final List<Permutation> generators;

    public BaseStrongGeneratorLevel(Integer base, List<Permutation> generators) {
        this.base = base;
        this.generators = generators;
        calculateTransversals();
    }

    private void calculateTransversals() {
        PriorityQueue<Integer> toVisit = new PriorityQueue<Integer>();
        toVisit.add(this.base); transversals.put(this.base, identity());
        while (!toVisit.isEmpty()) {
            Integer element = toVisit.poll();
            for (Permutation generator: generators) {
                Integer image = generator.actOn(element);
                if (!transversals.containsKey(image)) {
                    Permutation transversal = transversals.get(element).times(generator);
                    transversals.put(image, transversal);
                    toVisit.add(image);
                } else {
                    Permutation stabilizer = transversals.get(element).times(generator).times(transversals.get(image).inverse());
                    if (!stabilizer.isIdentity()) {
                        stabilizers.add(stabilizer);
                    }
                }
            }

        }
    }

    private Permutation identity() {
        Permutation anyGenerator = generators.get(0);
        return anyGenerator.times(anyGenerator.inverse());
    }

    public List<Permutation> stabilizers() {
        return Collections.unmodifiableList(stabilizers);
    }

    public Set<Integer> orbit() {
        return transversals.keySet();
    }

    public boolean hasTransversalFor(Permutation permutation) {
        return transversals.containsKey(permutation.actOn(base));
    }

    public Permutation transversalFor(Permutation permutation) {
        return transversals.get(permutation.actOn(base));
    }
}