package ecommerce.kafka.consumer;

import ecommerce.kafka.utils.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> kafkaConsumer;
    private final ConsumerFunction parse;

    private KafkaService(String groupId, ConsumerFunction parse, Class<T> type, Map<String, String> extraProperties) {
        kafkaConsumer = new KafkaConsumer<>(properties(groupId, type, extraProperties));
        this.parse = parse;
    }

    public KafkaService(String groupId, String topico, ConsumerFunction parse, Class<T> type, Map<String, String> extraProperties) {
        this(groupId, parse, type, extraProperties);
        kafkaConsumer.subscribe(Collections.singletonList(topico));
    }

    public KafkaService(String groupId, Pattern topico, ConsumerFunction parse, Class<T> type, Map<String, String> extraProperties) {
        this(groupId, parse, type, extraProperties);
        kafkaConsumer.subscribe(topico);
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, T> records = kafkaConsumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println(records.count() + " registros encontrados");

                records.forEach(r -> {
                    parse.consume(r);
                });
            }
        }
    }

    private Properties properties(String groupId, Class<T> type, Map<String, String> extraProperties) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(extraProperties);

        return properties;
    }

    @Override
    public void close() {
        kafkaConsumer.close();
    }
}
