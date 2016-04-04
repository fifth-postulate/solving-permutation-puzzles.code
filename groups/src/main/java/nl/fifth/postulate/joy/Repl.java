package nl.fifth.postulate.joy;

import nl.fifth.postulate.groups.Group;
import nl.fifth.postulate.groups.Permutation;
import nl.fifth.postulate.groups.Word;
import nl.fifth.postulate.groups.factories.PermutationWordFactory;
import nl.fifth.postulate.groups.special.PermutationWord;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Repl {
    private static final int NO_FILE_ARGUMENT = 1;
    private static final int FILE_DOES_NOT_EXIST = 2;
    private static final int FILE_CAN_NOT_BE_READ = 3;
    private static final int IO_EXCEPTION_OCCURED = 4;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please input a group file");
            System.exit(NO_FILE_ARGUMENT);
        }

        String fileName = args[0];
        File groupFile = new File(fileName);
        if (!groupFile.exists()) {
            System.out.printf("File \"%s\" does not exist\n", fileName);
            System.exit(FILE_DOES_NOT_EXIST);
        } else if (!groupFile.canRead()) {
            System.out.printf("File \"%s\" can not be read\n", fileName);
            System.exit(FILE_CAN_NOT_BE_READ);
        }

        Repl repl;
        try {
            repl = new Repl(groupFile, System.in, System.out);
            repl.start();
        } catch (IOException e) {
            System.out.printf("IO exception occured while reading \"%s\"\n", fileName);
            System.exit(IO_EXCEPTION_OCCURED);
        }
    }

    private final Group<PermutationWord> group;
    private final CommandFactory commandFactory;
    private final PrintStream out;

    public Repl(File groupFile, InputStream in, PrintStream out) throws IOException {
        this.group = Group.generatedBy(groupFile, new PermutationWordFactory());
        this.commandFactory = new CommandFactory(new Scanner(in), out);
        this.out = out;
    }


    private void start() {
        out.println("Starting group calculations");
        while (true) {
            out.print("> ");
            Command command = commandFactory.nextCommand();
            boolean quit = command.executeWith(group);
            if (quit) {
                break;
            }
        }
        out.println("\nStopping group calculations");
    }
}

interface Command {
    /***
     * The {@code executeWith} method executes the command on {@code group}.
     * @param group The {@code Group} to execute on.
     * @returns {@code true} if the command loop needs to stop, {@code false} otherwise
     */
    boolean executeWith(Group<PermutationWord> group);
}

class DoNothingCommand implements Command {
    @Override
    public boolean executeWith(Group<PermutationWord> group) {
        /* do nothing */
        return false;
    }
}

class QuitCommand implements Command {

    @Override
    public boolean executeWith(Group<PermutationWord> group) {
        /* do nothing */
        return true;
    }
}

class SizeCommand implements Command {
    private PrintStream out;

    public SizeCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public boolean executeWith(Group<PermutationWord> group) {
        out.printf("%d\n", group.size());
        return false;
    }
}

/***
 * Check if a permutation is a member
 *
 * syntax: {@code member \d+( \d+)*;}
 */
class MemberCommand implements Command {
    private final Scanner scanner;
    private final PrintStream out;

    public MemberCommand(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public boolean executeWith(Group<PermutationWord> group) {
        List<Integer> image = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            int element = scanner.nextInt();
            image.add(element);
        }
        scanner.next(/* consumes ; */);
        Permutation permutation = new Permutation(image);
        PermutationWord permutationWord = new PermutationWord(permutation, Word.identity());
        boolean isMember = group.isMember(permutationWord);
        out.printf("%s is%s a member\n", permutation, isMember ? "": " not");
        return false;
    }
}

/***
 * Return a word in the generators
 *
 * syntax: {@code strip \d+( \d+)*;}
 */
class StripCommand implements Command {
    private final Scanner scanner;
    private final PrintStream out;

    public StripCommand(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public boolean executeWith(Group<PermutationWord> group) {
        List<Integer> image = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            int element = scanner.nextInt();
            image.add(element);
        }
        scanner.next(/* consumes ; */);
        Permutation permutation = new Permutation(image);
        PermutationWord permutationWord = new PermutationWord(permutation, Word.identity());
        permutationWord = group.strip(permutationWord);
        if (permutationWord.permutation.isIdentity()) {
            out.printf("%s\n", permutationWord.word.inverse());
        } else {
            out.println("not a member");
        }
        return false;
    }
}

class CommandFactory {
    private final Scanner scanner;
    private final PrintStream out;

    public CommandFactory(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public Command nextCommand() {
        String commandWord = scanner.next();
        return from(commandWord);
    }

    public Command from(String command) {
        if (command.startsWith("quit")) {
            return new QuitCommand();
        }
        if (command.startsWith("size")) {
            return new SizeCommand(out);
        }
        if (command.startsWith("member")) {
            return new MemberCommand(scanner, out);
        }
        if (command.startsWith("strip")) {
            return new StripCommand(scanner, out);
        }
        return new DoNothingCommand();
    }
}