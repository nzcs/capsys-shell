package hu.capsys.shell.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;


@ShellComponent
@RequiredArgsConstructor
public class RedisCommands {

    final RedissonClient redissonClient;


    @ShellMethod("Clear Redis")
    public String clear_db() {
        redissonClient.getKeys().flushdb();
        return "Clear";
    }

    @ShellMethod("List Redis Keys")
    public String list_db() {
        return redissonClient.getKeys().getKeysStream().collect(Collectors.joining("\n"));
    }
}
