# SQL
- SQL: structured query language
- storing, manipulating and retrieving data stored in a relational database
- RDMS/RDBMS: Relational Database Management System

## SQL Commands
- DDL (Data Definition Language, object: table)
    - CREATE
    - ALTER
    - DROP
- DML (Data Manipulation Language, object: record)
    - SELECT
    - INSERT
    - UPDATE
    - DELETE
- DCL (Data Control Language, object: privilege)
    - GRANT
    - REVOKE

### SELECT
complicated select
```sql
SELECT DISTINCT column_list
FROM table_list
  JOIN table ON join_condition
WHERE row_filter
ORDER BY column
LIMIT count OFFSET offset
GROUP BY column
HAVING group_filter;
```
basic select
```sql
SELECT column_list FROM table;
SELECT * FROM table;
```
select distinct: remove duplicate rows
```sql
SELECT DISTINCT select_list FROM table;
```
order by: sort by ascending or descending order
```sql
SELECT
    select_list
FROM
    table
ORDER BY
    column_1 ASC,
    column_2 DESC;
```
limit: constrain the number of rows returned by a query
get the n or nth highest and lowest value: order by + limit
```sql
SELECT
    column_list
FROM
    table
LIMIT row_count;

LIMIT row_count OFFSET offset_count;
LIMIT offset_count, row_count;
```

where: filter data returned by the query
```sql
SELECT
    column_list
FROM
    table
WHERE
    search_condition;

WHERE column = 100;
WHERE column IN (1, 2, 3);
WHERE column LIKE 'An%';
WHERE column BETWEEN 10 AND 20;
WHERE column = 1 OR column = 2;
```

operators: in, between, like, glob, is [not] null
```sql
expression [NOT] IN (value_list|subquery)

test_expression BETWEEN low_expression AND high_expression
test_expression NOT BETWEEN low_expression AND high_expression
test_expression < low_expression OR test_expression > high_expression

LIKE pattern;
LIKE 'h_llo%';
PRAGMA case_sensitive_like = true;
column_1 LIKE pattern ESCAPE expression;
```

join: qurey data from two or more tables
- inner join
- left join
- cross join
- right join
- full outer join


### Changing data
```sql
INSERT INTO table1 (column1,column2 ,..)
VALUES 
   (value1,value2 ,...),
   (value1,value2 ,...),
    ...
   (value1,value2 ,...);

UPDATE table
SET column_1 = new_value_1,
    column_2 = new_value_2
WHERE
    search_condition 
ORDER column_or_expression
LIMIT row_count OFFSET offset;

DELETE FROM table
WHERE search_condition
ORDER BY criteria
LIMIT row_count OFFSET offset;

REPLACE INTO table(column_list)
VALUES(value_list);
```