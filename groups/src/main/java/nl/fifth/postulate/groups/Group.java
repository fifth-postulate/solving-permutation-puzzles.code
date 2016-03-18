package nl.fifth.postulate.groups;

import java.util.*;

public class Group {
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
            generators = level.fixGenerators();
        }
    }

    private Integer findBase(List<Integer> bases, List<Permutation> generators) {
        Integer base = null;
        candidateLoop: for (int candidate = 0; candidate < generators.get(0).gsetSize(); candidate++) {
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
    private final List<Permutation> fixGenerators = new ArrayList<Permutation>();
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
                    Permutation fixGenerator = transversals.get(element).times(generator).times(transversals.get(image).inverse());
                    if (!fixGenerator.isIdentity()) {
                        fixGenerators.add(fixGenerator);
                    }
                }
            }

        }
    }

    private Permutation identity() {
        Permutation anyGenerator = generators.get(0);
        return anyGenerator.times(anyGenerator.inverse());
    }

    public List<Permutation> fixGenerators() {
        return Collections.unmodifiableList(fixGenerators);
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