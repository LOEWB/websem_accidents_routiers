package fr.iuteam.websem_accidents_routiers.util;


import java.util.*;
import java.util.stream.Collectors;

public class QueryBuilder {

    private List<String> select;
    private String from = "";
    private List<String> where;
    private List<String> orderBy;
    private List<String> groupBy;
    private List<String> having;
    private int limit;
    private int offset;


    public QueryBuilder select(String ...field){
        if(this.select== null){
            this.select = new ArrayList<>();
        }
        this.select =  Arrays.asList(field);
        return this;
    }

    private String getFormattedSelect(){
        if(this.select==null)
            return "*";
        return this.select.stream().collect(Collectors.joining(", "));
    }
    private String getFormattedWhere(){
        if(this.where==null)
            return null;
        return this.where.stream().collect(Collectors.joining(" "));
    }
    private String getFormattedGroupBy(){
        if(this.groupBy==null)
            return null;
        return this.groupBy.stream().collect(Collectors.joining(" "));
    }
    private String getFormattedHaving(){
        if(this.having==null)
            return null;
        return this.having.stream().collect(Collectors.joining(" "));
    }
    private String getFormattedOrderBy(){
        if(this.orderBy==null)
            return null;
        return this.orderBy.stream().collect(Collectors.joining(" "));
    }

    public QueryBuilder from(String from){
        this.from = from;
        return this;
    }
    public QueryBuilder whereEquals(String where, String eq){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " = " + eq);
        return this;
    }
    public QueryBuilder between(String where, String btl, String btm){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " BETWEEN " + btl +" AND "+btm);
        return this;
    }
    public QueryBuilder between(String where, Long btl, Long btm){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " BETWEEN " + btl +" AND "+btm);
        return this;
    }
    public QueryBuilder between(String where, Integer btl, Integer btm){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " BETWEEN " + btl +" AND "+btm);
        return this;
    }
    public QueryBuilder in(String where, String ...in){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " IN( " + Arrays.asList(in).stream().collect(Collectors.joining(", "))+")");
        return this;
    }
    public QueryBuilder like(String where, String lk){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where+ " LIKE " + lk);
        return this;
    }

    public  QueryBuilder where(String where){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where);
        return this;
    }
    public QueryBuilder and() throws QueryBuilderException {
        if(this.where==null)
            throw new QueryBuilderException("Impossible to create where condition");
        this.where.add("AND");
        return this;
    }
    public QueryBuilder or() throws QueryBuilderException {
        if(this.where==null)
            throw new QueryBuilderException("Impossible to create where condition");
        this.where.add("OR");
        return this;
    }

    public QueryBuilder orderBy(String ...field){
        if(this.orderBy==null){
            this.orderBy = new ArrayList<>();
        }
        this.orderBy.add(Arrays.asList(field).stream().collect(Collectors.joining(", ")));
        return this;
    }

    public  QueryBuilder groupBy(String ...groupBy){
        if(this.groupBy==null){
            this.groupBy = new ArrayList<>();
        }
        this.groupBy.add(Arrays.asList(groupBy).stream().collect(Collectors.joining(", ")));
        return this;
    }

    public QueryBuilder having(String func, String op, String val){
        if(this.having==null)
            this.having = new ArrayList<>();
        this.having.add(func + op + val);
        return this;
    }
    public QueryBuilder orHaving() throws QueryBuilderException {
        if(this.having==null)
            throw new QueryBuilderException("Impossible to create having condition");
        this.having.add("OR");
        return this;
    }
    public QueryBuilder andHaving() throws QueryBuilderException {
        if(this.having==null)
            throw new QueryBuilderException("Impossible to create having condition");
        this.having.add("AND");
        return this;
    }

    public QueryBuilder limit(Integer limit){
        this.limit = limit;
        return this;
    }
    public QueryBuilder limit(Integer limit,Integer offset){
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    @Override
    public String toString() {
        int count = 2;
        String pattern = "SELECT " +this.getFormattedSelect() +" FROM " + this.from;

        if(this.getFormattedWhere()!=null) pattern += " WHERE " + this.getFormattedWhere();
        if(this.getFormattedGroupBy()!=null) pattern += " GROUP BY " + this.getFormattedGroupBy();
        if(this.getFormattedHaving()!=null) pattern += " HAVING " + this.getFormattedHaving();
        if(this.getFormattedOrderBy()!=null) pattern += " ORDER BY " + this.getFormattedOrderBy();
        if(this.limit!=0) pattern += " LIMIT " + this.limit;
        if(this.offset!=0) pattern += " OFFSET " + this.offset;

        return pattern;
    }
}
