package ch.IR.springLucene.controllers;

import ch.IR.springLucene.response.ErrorResponse;
import ch.IR.springLucene.retrievers.Retriever;
import ch.IR.springLucene.utils.LuceneConstants;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/query")
public class QueryController {

    private static class QueryResult{
        public String title;
        public String content;
        public String url;
        public String score;

        public QueryResult(Document doc, ScoreDoc scoreDoc){
            this.title = doc.get(LuceneConstants.TITLE);
            this.content = doc.get(LuceneConstants.CONTENT);
            if (this.content != null) {
                this.content = this.content.substring(
                        Math.min(this.content.length(), 500),
                        Math.min(this.content.length(), 1000)
                );
                this.content = "..." + this.content + "...";
            }
            this.url = doc.get(LuceneConstants.URL);
            this.score = scoreDoc.score > 10 ?
                    new DecimalFormat("#.##").format(scoreDoc.score) :
                    new DecimalFormat("#.###").format(scoreDoc.score);
        }
    }

    private static class ResultsObject {
        public String query;
        public int count;
        public ArrayList<QueryResult> results;

        public ResultsObject(String query){
            this.query = query;
            this.results = new ArrayList<>();
        }
    }

    private final Retriever retriever;

    public QueryController() throws IOException {
        retriever = new Retriever(LuceneConstants.LUCENE_DOC);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @RequestParam(value="query", required=true) String query){

        try {
            final TopDocs topDocs = retriever.runQuery(query);
            ResultsObject resultsObject = new ResultsObject(query);
            for (final ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = retriever.getDocument(scoreDoc);
                QueryResult qr = new QueryResult(doc, scoreDoc);
                resultsObject.results.add(qr);
            }
            resultsObject.count = resultsObject.results.size();
            return new ResponseEntity<>(resultsObject, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Index file not found"), HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid Query"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

