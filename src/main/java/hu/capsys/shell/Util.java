package hu.capsys.shell;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class Util {

    public static void print(String operation, HttpStatus httpStatus, Object... args) {
        System.out.printf("%s %s: %s%n", operation, httpStatus, Arrays.toString(args));
    }
}
