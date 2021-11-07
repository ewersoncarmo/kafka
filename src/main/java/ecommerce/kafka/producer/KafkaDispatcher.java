package ecommerce.kafka.producer;

import ecommerce.kafka.utils.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> kafkaProducer;

    public KafkaDispatcher() {
        kafkaProducer = new KafkaProducer<>(properties());
    }

    public void send(String topico, String key, T value) throws ExecutionException, InterruptedException {
        ProducerRecord<String, T> producerRecord = new ProducerRecord<>(topico, key, value);

        Callback callback = (data, exception) -> {
            if (exception != null) {
                System.out.println(exception.getMessage());
                return;
            }

            System.out.println("tópico " + data.topic() + " -- partição " + data.partition() + " -- offset " + data.offset() + " -- timestamp " + data.timestamp());
        };

        kafkaProducer.send(producerRecord, callback).get();
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // key do KafkaProducer
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName()); // value do KafkaProducer

        return properties;
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
