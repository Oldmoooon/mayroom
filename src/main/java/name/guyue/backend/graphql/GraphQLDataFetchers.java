package name.guyue.backend.graphql;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019/9/10
 */
@Component
public class GraphQLDataFetchers {

    public DataFetcher getHelloWorldDataFetcher() {
        return environment -> "world";
    }

    public DataFetcher getEchoDataFetcher() {
        return environment -> environment.getArgument("toEcho");
    }
}
