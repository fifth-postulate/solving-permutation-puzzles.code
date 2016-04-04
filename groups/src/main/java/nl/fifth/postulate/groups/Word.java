package nl.fifth.postulate.groups;

import java.util.*;
import java.util.stream.Collectors;

public class Word implements GroupElement<Word> {
    public static Word generator(String symbol) {
        return new Word(symbol);
    }

    public static Word identity() {
        return new Word();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PrimitiveWord that = (PrimitiveWord) o;

            if (power != that.power) return false;
            return generator.equals(that.generator);

        }

        @Override
        public int hashCode() {
            int result = generator.hashCode();
            result = 31 * result + power;
            return result;
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
        Stack<PrimitiveWord> stack = new Stack<PrimitiveWord>();
        PrimitiveWord current = notNormalized.get(0);
        int index = 1;
        while (index < notNormalized.size()) {
            PrimitiveWord primitiveWord = notNormalized.get(index);
            if (primitiveWord.generator.equals(current.generator)) {
                current = current.merge(primitiveWord);
            } else {
                if (current.power != 0) {
                    stack.push(current);
                } else {
                    /* current.power == 0 */
                    if (!stack.isEmpty()) {
                        current = stack.pop();
                        continue;
                    }
                }
                current = primitiveWord;
            }
            index++;
        }
        if (current.power != 0) {
            stack.push(current);
        }
        List<PrimitiveWord> normalized = new ArrayList<PrimitiveWord>();
        normalized.addAll(stack);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return primitiveWords.equals(word.primitiveWords);

    }

    @Override
    public int hashCode() {
        return primitiveWords.hashCode();
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
