zookeeper-server-start.bat ..\..\config\zookeeper.properties
kafka-server-start.bat ..\..\config\server.properties
kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_ENVIA_EMAIL
kafka-topics.bat --list --bootstrap-server localhost:9092
kafka-console-producer.bat --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
kafka-topics.bat --list --bootstrap-server localhost:9092
kafka-consumer-groups.bat --all-groups --bootstrap-server localhost:9092 --describe

paralelismo
- número de partições deve ser maior que o número de consumidores de um grupo
- um tópico pode ser dividido em várias partições. quando a mensagem é enviada, ela pode ser alocada em uma das partições
(de acordo com a chave - key da mensagem). os consumidores (separados por grupos.. todos os grupos vão receber todas as mensagens)
escutam o tópico, sendo que cada consumidor pode escutar em um ou mais partições