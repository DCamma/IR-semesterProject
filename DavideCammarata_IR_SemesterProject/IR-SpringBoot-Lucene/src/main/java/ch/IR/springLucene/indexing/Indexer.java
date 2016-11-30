package ch.IR.springLucene.indexing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import ch.IR.springLucene.utils.LuceneConstants;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException{
        final Path indexPath = Paths.get(indexDirectoryPath);

        if (Files.exists(indexPath)) {
            Files.walk(indexPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        final Directory indexDirectory = FSDirectory.open(indexPath);
        writer = new IndexWriter(indexDirectory, new IndexWriterConfig());
    }

    public int createIndex() throws IOException {
        try (final BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(getClass().getResourceAsStream("/finalDump")))) {
            String line;
            Document document = new Document();
            boolean notEmpty = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Recno::")) {
                    document = new Document();
                } else if (line.startsWith("URL:: ")) {
                    if (line.substring(6).split("\\?").length > 1 && line.substring(6).split("\\?")[1].split("=").length > 1 && line.substring(6).split("\\?")[1].split("=")[0].equals("page")){
                        while (!line.startsWith("Recno::")){
                            line = reader.readLine();
                        }
                    }
                    document.add(new TextField(LuceneConstants.URL, line.substring(6), Field.Store.YES));
                } else if (line.startsWith("ParseText::")) {
                    line = reader.readLine();
                    if (!line.isEmpty()){
                        notEmpty = true;
                        document.add(new TextField(LuceneConstants.CONTENT, line, Field.Store.YES));
                    } else {
                        while (!line.startsWith("Recno::")){
                            line = reader.readLine();
                        }
                        document = new Document();
                    }
                } else if (line.startsWith("ParseData::")) {
                    while (!line.startsWith("Title:")){
                        line = reader.readLine();
                    }
                    if (!line.substring(7).isEmpty()) document.add(new TextField(LuceneConstants.TITLE, line.substring(7), Field.Store.YES));
                    if (notEmpty) writer.addDocument(document);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.numDocs();
    }

    public void close() throws IOException{
        writer.close();
    }
}