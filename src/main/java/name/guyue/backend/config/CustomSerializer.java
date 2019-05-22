package name.guyue.backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import name.guyue.backend.model.House;

/**
 * @author hujia
 * @date 2019-05-22
 */
public class CustomSerializer  extends JsonSerializer<List<House>> {

    @Override
    public void serialize(List<House> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<Long> list = value.stream().map(House::getId).collect(Collectors.toList());
        gen.writeString("[" + Joiner.on(',').join(list) + "]");
    }
}
