package fr.iuteam.websem_accidents_routiers;

import fr.iuteam.websem_accidents_routiers.sparql.SparqlConn;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlException;
import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import org.apache.jena.rdfconnection.RDFConnection;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
class WebsemAccidentsRoutiersApplicationTests {

    @Test
    RDFConnection contextLoads() throws ParseException, SparqlException, IOException {
        SparqlConn sparqlConn = SparqlConn.getInstance();
        RDFConnection conn = sparqlConn.getConn();
        Assert.isTrue(conn.isClosed()==false,"Load sparql dataset");
        return conn;
    }

    @Test
    QueryBuild testSelect() throws  QueryBuilderException {
        QueryBuild build = new QueryBuild();
        build.select("?subject","?predi","?x");
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x"), "Sparql query Select");
        return build;
    }

    @Test
    QueryBuild testWhere() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.where("?subject ?predi ?x");
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x WHERE {?subject ?predi ?x.}"), "Sparql query Where");

        return build;
    }
    @Test
    QueryBuild testFilter() throws QueryBuilderException {
        QueryBuild build = this.testWhere();
        build.filter("?x > 5");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x WHERE {?subject ?predi ?x. FILTER ( ?x > 5)}"), "Sparql query Filter");
        return build;
    }
    @Test
    void testAndFilter() throws QueryBuilderException {
        QueryBuild build = this.testFilter();
        build.and().filter("?x = 10");
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x WHERE {?subject ?predi ?x. FILTER ( ?x > 5 && ?x = 10)}"), "Sparql query And Filter");
    }
    @Test
    void testOrFilter() throws QueryBuilderException {
        QueryBuild build = this.testFilter();
        build.or().filter("?x = 10");
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x WHERE {?subject ?predi ?x. FILTER ( ?x > 5 || ?x = 10)}"), "Sparql query Or Filter");
    }
    @Test
    void testLimit() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.limit(5);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x LIMIT 5"), "Sparql query Limit");
    }
    @Test
    void testLimitOffset() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.limit(5,2);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x LIMIT 5 OFFSET 2"), "Sparql query Limit with Offset");
    }

    @Test
    void testOrderBy() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.orderBy("?x", "?r");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x ORDER BY ?x ?r"), "Sparql query OrderBy");
    }

    @Test
    void testOrderByAsc() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.orderByAsc("?x");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x ORDER BY asc(?x)"), "Sparql query OrderByAsc");
    }

    @Test
    void testOrderByDesc() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.orderByDesc("?x");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x ORDER BY desc(?x)"), "Sparql query OrderByDesc");
    }

    @Test
    QueryBuild testHaving() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.having("?x > 5");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x HAVING (?x > 5)"), "Sparql query Having");
        return build;
    }
    @Test
    void testAndHaving() throws QueryBuilderException {
        QueryBuild build = this.testHaving();
        build.andHaving().having("?x < 10");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x HAVING (?x > 5 && ?x < 10)"), "Sparql query AndHaving");
    }

    @Test
    void testOrHaving() throws QueryBuilderException {
        QueryBuild build = this.testHaving();
        build.orHaving().having("?x < 10");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x HAVING (?x > 5 || ?x < 10)"), "Sparql query AndHaving");
    }

    @Test
    void testGroupBy() throws QueryBuilderException {
        QueryBuild build = this.testSelect();
        build.groupBy("?x", "?y");
        System.out.println(build);
        Assert.isTrue(build.toString().equals("SELECT ?subject ?predi ?x GROUP BY ?x ?y"), "Sparql query AndHaving");
    }

}
