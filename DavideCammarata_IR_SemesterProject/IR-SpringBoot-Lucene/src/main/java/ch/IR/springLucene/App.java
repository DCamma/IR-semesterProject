package ch.IR.springLucene;

import ch.IR.springLucene.indexing.Indexer;
import ch.IR.springLucene.utils.LuceneConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class App {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {container.setPort(8090);});
    }

    public static void main( String[] args) throws Exception {
        final Indexer indexer = new Indexer(LuceneConstants.LUCENE_DOC);
        final int total = indexer.createIndex();
        indexer.close();
        System.out.println("Indexed " + total + " documents");
        SpringApplication.run(App.class, args);
    }
}

