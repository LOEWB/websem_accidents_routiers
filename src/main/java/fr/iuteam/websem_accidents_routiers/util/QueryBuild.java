package fr.iuteam.websem_accidents_routiers.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuild {
    private List<String> select;
    private List<String> where;
    private List<String> filter;
    private List<String> orderBy;
    private List<String> groupBy;
    private List<String> having;
    private int limit;
    private int offset;

    public QueryBuild select(String ...field){
        if(this.select== null){
            this.select = new ArrayList<>();
        }
        this.select.addAll(Arrays.asList(field));
        return this;
    }

    public QueryBuild where(String where){
        if(this.where==null)
            this.where = new ArrayList<>();
        this.where.add(where);
        return this;
    }

    public QueryBuild filter(String filter){
        if(this.filter==null)
            this.filter = new ArrayList<>();
        this.filter.add(filter);
        return this;
    }

    public QueryBuild and() throws QueryBuilderException {
        if(this.filter==null)
            throw new QueryBuilderException("Impossible to create filter condition");
        this.filter.add("&&");
        return this;
    }

    public QueryBuild or() throws QueryBuilderException {
        if(this.filter==null)
            throw new QueryBuilderException("Impossible to create filter condition");
        this.filter.add("||");
        return this;
    }

    public QueryBuild limit(int limit){
        this.limit = limit;
        return this;
    }

    public QueryBuild limit(int limit,int offset){
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public QueryBuild orderByAsc(String field){
        if(this.orderBy==null){
            this.orderBy = new ArrayList<>();
        }
        this.orderBy.add("asc("+field+")");

        return this;
    }

    public QueryBuild orderByDesc(String field){
        if(this.orderBy==null){
            this.orderBy = new ArrayList<>();
        }
        this.orderBy.add("desc("+field+")");
        return this;
    }

    public QueryBuild orderBy(String ...field){
        if(this.orderBy==null){
            this.orderBy = new ArrayList<>();
        }
        this.orderBy.add(Arrays.asList(field).stream().collect(Collectors.joining(" ")));
        return this;
    }

    public QueryBuild having(String having){
        if(this.having==null)
            this.having = new ArrayList<>();
        this.having.add(having);
        return this;
    }

    public QueryBuild orHaving() throws QueryBuilderException {
        if(this.having==null)
            throw new QueryBuilderException("Impossible to create having condition");
        this.having.add("||");
        return this;
    }

    public QueryBuild andHaving() throws QueryBuilderException {
        if(this.having==null)
            throw new QueryBuilderException("Impossible to create having condition");
        this.having.add("&&");
        return this;
    }

    public  QueryBuild groupBy(String ...groupBy){
        if(this.groupBy==null){
            this.groupBy = new ArrayList<>();
        }
        this.groupBy.add(Arrays.asList(groupBy).stream().collect(Collectors.joining(" ")));
        return this;
    }



    private String getFormattedWhereAndFilter(){
        /*String wf = "";
        if(this.getFormattedWhere()!=null){
            wf += this.getFormattedWhere();
            if(this.getFormattedFilter()!=null){
                wf+= " FILTER ( " +this.getFormattedFilter() + ")";
            }
            return wf;
        }
        return null;*/
        String wf = "";
        if(this.getFormattedWhere()!=null){
            wf += this.getFormattedWhere();
        }
        if(this.getFormattedFilter()!=null){
            wf+= " FILTER ( " +this.getFormattedFilter() + ")";
        }
        return wf;
    }

    private String getFormattedOrderBy(){
        if(this.orderBy==null)
            return null;
        return this.orderBy.stream().collect(Collectors.joining(" "));
    }

    private String getFormattedFilter(){
        if(this.filter==null)
            return null;
        return this.filter.stream().collect(Collectors.joining(" "));
    }

    private String getFormattedWhere(){
        if(this.where==null)
            return null;
        return this.where.stream().collect(Collectors.joining(".")) + ".";
    }

    private String getFormattedSelect(){
        if(this.select==null)
            return "*";
        return this.select.stream().collect(Collectors.joining(" "));
    }

    private String getFormattedHaving(){
        if(this.having==null)
            return null;
        return this.having.stream().collect(Collectors.joining(" "));
    }

    private String getFormattedGroupBy(){
        if(this.groupBy==null)
            return null;
        return this.groupBy.stream().collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        String pattern = "SELECT " + this.getFormattedSelect();
        pattern += " WHERE {" + this.getFormattedWhereAndFilter() + "}";
        if(this.getFormattedGroupBy()!=null) pattern += " GROUP BY " + this.getFormattedGroupBy();
        if(this.getFormattedHaving()!=null) pattern += " HAVING (" + this.getFormattedHaving() + ")";
        if(this.getFormattedOrderBy()!=null) pattern += " ORDER BY " + this.getFormattedOrderBy();

        if(this.limit!=0) pattern += " LIMIT " + this.limit;
        if(this.offset!=0) pattern += " OFFSET " + this.offset;
        return pattern;
    }
}
