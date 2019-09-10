package name.guyue.backend.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import java.io.IOException;
import java.net.URL;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019/9/10
 */
@Component
public class GraphQLProvider {

    private final GraphQLDataFetchers graphQLDataFetchers;
    private final GraphQL graphQL;

    public GraphQLProvider(GraphQLDataFetchers graphQLDataFetchers) throws IOException {
        this.graphQLDataFetchers = graphQLDataFetchers;

        URL url = Resources.getResource("schema.graphql");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .type(TypeRuntimeWiring.newTypeWiring("Query")
                .dataFetcher("hello", graphQLDataFetchers.getHelloWorldDataFetcher())
                .dataFetcher("echo", graphQLDataFetchers.getEchoDataFetcher())
                .build())
            .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
