package tm.packt.ex.jshell.extension;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import jdk.jshell.SourceCodeAnalysis;

import java.io.IOException;

import static java.lang.System.out;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Objects.*;
import static jdk.jshell.Snippet.Status.REJECTED;

/**
 * This class can execute JShell expressions in sequence
 * We can write java commands like shell script and execute it.
 * Just write commands in a way that we give in JShell and save it in a file and execute it.
 *
 * @author Mohamed Taman,
 * @since 16/6/2018
 * Course:  Hands-On Java 10 Programming with JShell
 *          By Packt Publisher
 * Section: JShell, Advanced Topics
 * Video:   3.5 examples - Execute Java Code Like OS Shell Script
 */

public class JShellScriptExecutor {

    public static void main(String[] args) {
        new JShellScriptExecutor().evaluate(args[0]);
    }

    private void evaluate(String scriptFileName) {

        try (JShell jshell = JShell.create()) {

            // Handle snippet events. We can print value or take action if evaluation failed.
            jshell.onSnippetEvent(this::snippetEventHandler);

            var s = new String(readAllBytes(get(scriptFileName)));

            while (true) {
                // Read source line by line till semicolon (;)
                SourceCodeAnalysis.CompletionInfo an = jshell.sourceCodeAnalysis().analyzeCompletion(s);
                if (!an.completeness().isComplete()) break;
                // If there are any method declaration or class declaration
                // in new lines, resolve it.
                jshell.eval(trimNewlines(an.source()));
                // EOF
                if (an.remaining().isEmpty()) break;
                // If there is semicolon, execute next seq
                s = an.remaining();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void snippetEventHandler(SnippetEvent snippetEvent) {
        String value = snippetEvent.value();

        // Prints output of code evaluation
        if (!isNull(value) && value.trim().length() > 0) out.println(value);

        // If there are any erros print and exit
        if (REJECTED.equals(snippetEvent.status()))
            out.printf("Evaluation failed : %s\nIgnoring execution of above script%n", snippetEvent.snippet().toString());
    }

    private String trimNewlines(String s) {

        int b = 0;

        while (b < s.length() && s.charAt(b) == '\n') ++b;

        int e = s.length() - 1;

        while (e >= 0 && s.charAt(e) == '\n') --e;

        return s.substring(b, e + 1);
    }
}
