package hu.capsys.shell.redis;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.redisson.api.RedissonClient;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;


@ShellComponent
@RequiredArgsConstructor
public class RedisCommands {

    final RedissonClient redissonClient;
    final RestHighLevelClient elasticsearchClient;


    @ShellMethod("Clear DB")
    public String clear_db() {
        redissonClient.getKeys().flushdb();
//        deleteDocuments();
        return "Clear";
    }

    @ShellMethod("List DB")
    public String list_db() {
        return redissonClient.getKeys().getKeysStream().collect(Collectors.joining("\n"));
    }


    @ShellMethod("Get DB")
    public String get_db(String key) {
        return redissonClient.getBucket(key).get().toString();
    }


//    @ShellMethod("List Queue")
//    public String list_queue(String name) {
//        int size = client.getBlockingDeque(name).;
//        return "List Queue: " + size;
//    }


    private void deleteDocuments() {
        try {
            String[] indices = getIndices(elasticsearchClient);
            System.out.println(Arrays.toString(indices));
            elasticsearchClient.deleteByQuery(
                    new DeleteByQueryRequest(indices)
                            .setQuery(matchAllQuery())
                            .setRefresh(true),
                    RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getIndices(RestHighLevelClient client) throws IOException {
        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        return response.getIndices();
    }
}
