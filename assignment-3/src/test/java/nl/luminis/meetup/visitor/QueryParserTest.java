package nl.luminis.meetup.visitor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.MatchNoneQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.junit.jupiter.api.Test;

public class QueryParserTest {

    QueryParser parser = new QueryParser();

    @Test
    public void testParseKeyValuePairSingleTerm() {
        AbstractQueryBuilder builder = parser.parse("(KEY:value)");
        assertThat(builder).isEqualTo(
                new MatchQueryBuilder("KEY", "value")
        );
    }

    @Test
    public void testParseKeyValuePairMultipleTerms() {
        AbstractQueryBuilder builder = parser.parse("(KEY:value1,value2)");

        assertThat(builder).isEqualTo(
                new MatchQueryBuilder("KEY", "value1 value2")
        );
    }

    @Test
    public void testParseMultipleKeyValuePairs() {
        AbstractQueryBuilder builder = parser.parse("(KEY1:value)(KEY2:value1,value2)");

        assertThat(builder).isEqualTo(
                boolQuery()
                        .should(new MatchQueryBuilder("KEY1", "value"))
                        .should(new MatchQueryBuilder("KEY2", "value1 value2"))
        );
    }

    @Test
    public void testParseInvalidKeyValuePairKey() {
        AbstractQueryBuilder builder = parser.parse("(9KEY:value)");

        assertThat(builder).isEqualTo(
                new MatchNoneQueryBuilder()
        );
    }

    @Test
    public void testParseInvalidKeyValuePairTerm() {
        AbstractQueryBuilder builder = parser.parse("(KEY:9value)");

        assertThat(builder).isEqualTo(
                new MatchNoneQueryBuilder()
        );
    }
}
