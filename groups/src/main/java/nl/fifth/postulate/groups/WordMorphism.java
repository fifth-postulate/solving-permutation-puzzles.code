package nl.fifth.postulate.groups;

import java.util.HashMap;
import java.util.Map;

public class WordMorphism implements Morphism<Word> {
    private final Map<SLP, Word> morphism = new HashMap<SLP, Word>();

    @Override
    public Word identity() {
        return Word.identity();
    }

    @Override
    public Word get(SLP element) {
        return morphism.get(element);
    }

    public void put(SLP element, Word word) {
        morphism.put(element, word);
    }
}
