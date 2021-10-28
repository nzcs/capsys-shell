package hu.capsys.shell.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class RedisCommands {

    final RedissonClient client;


    @ShellMethod("List DB")
    public String clear_db() {
        client.getKeys().flushdb();
        return "Clear";
    }

    @ShellMethod("List DB")
    public String list_db() {
        return client.getKeys().getKeysStream().collect(Collectors.joining("\n"));
    }
}
