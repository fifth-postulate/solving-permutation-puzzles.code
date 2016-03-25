package nl.fifth.postulate.groups;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Word implements GroupElement<Word> {
    public static Word generator(String symbol) {
        return new Word(symbol);
    }

    private static class PrimitiveWord {
        public final String generator;
        public final int power;

        public PrimitiveWord(String generator, int power) {
            this.generator = generator;
            this.power = power;
        }

        public PrimitiveWord invert() { return new PrimitiveWord(generator, -power); }

        public PrimitiveWord merge(PrimitiveWord element) {
            if (!this.generator.equals(element.generator)) { throw new IllegalArgumentException("can not merge primitive words on different generators"); }
            return new PrimitiveWord(generator, this.power + element.power);
        }
    }

    private List<PrimitiveWord> primitiveWords = new ArrayList<PrimitiveWord>();

    private Word(String symbol) {
        this(new PrimitiveWord(symbol, 1));
    }

    private Word(PrimitiveWord... primitiveWords) {
        this(Arrays.asList(primitiveWords));
    }

    private Word(List<PrimitiveWord> primitiveWords) {
        this.primitiveWords = normalize(primitiveWords);
    }

    private List<PrimitiveWord> normalize(List<PrimitiveWord> notNormalized) {
        if (notNormalized.size() <= 1) { return notNormalized; }
        List<PrimitiveWord> normalized = new ArrayList<PrimitiveWord>();
        PrimitiveWord current = notNormalized.get(0);
        for (int index = 1; index < notNormalized.size(); index++) {
            PrimitiveWord primitiveWord = notNormalized.get(index);
            if (primitiveWord.generator.equals(current.generator)) {
                current = current.merge(primitiveWord);
            } else {
                if (current.power != 0) {
                    normalized.add(current);
                }
                current = primitiveWord;
            }
        }
        if (current.power != 0) {
            normalized.add(current);
        }
        return normalized;
    }

    @Override
    public boolean isIdentity() {
        return primitiveWords.size() == 0;
    }

    @Override
    public Word times(Word multiplicand) {
        List<PrimitiveWord> concatenation = new ArrayList<PrimitiveWord>();
        concatenation.addAll(this.primitiveWords);
        concatenation.addAll(multiplicand.primitiveWords);
        return new Word(concatenation);
    }

    @Override
    public Word inverse() {
        List<PrimitiveWord> inverted = this.primitiveWords.stream().map(w -> w.invert()).collect(Collectors.toList());
        Collections.reverse(inverted);
        return new Word(inverted);
    }

    @Override
    public String toString(){
        if (isIdentity()) {
            return "Id";
        } else {
            StringBuilder builder = new StringBuilder();
            for (PrimitiveWord primitiveWord: primitiveWords) {
                builder.append(primitiveWord.generator);
                if (primitiveWord.power != 1) {
                    builder.append("^");
                    builder.append(primitiveWord.power);
                }
            }
            return builder.toString();
        }
    }
}
