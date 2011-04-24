DELIMITER $$

DROP PROCEDURE IF EXISTS `zcms`.`pt_init` $$
CREATE PROCEDURE `zcms`.`pt_init` ()
BEGIN

/* Clear the table TablesRemark which the table has being delete from sys tables */
DELETE FROM tablesremark
  WHERE tablename NOT IN (
    SELECT t.table_name AS tablename
      FROM information_schema.tables t
      WHERE t.table_schema = 'zcms');

/* Check the sys tables,add the tables
   which does exist in the sys tables
   but does not exist in the table TablesRemark */
INSERT INTO tablesremark(tablename)
  SELECT LCASE(t.table_name) AS tablename
    FROM information_schema.tables t
      WHERE t.table_schema = 'zcms'
    AND t.table_name NOT IN (SELECT tablename FROM tablesremark);

/* Clear the table ColsRemark which the table_column has being delete from the sys tables,columns */
CREATE TEMPORARY TABLE temp
  SELECT CONCAT(CONCAT(LCASE(t.table_name) ,'_'), LCASE(c.column_name)) AS tablecolname
    FROM information_schema.tables t
    INNER JOIN information_schema.columns c
          ON t.table_name = c.table_name
          WHERE t.table_schema = 'zcms';
DELETE FROM colsremark WHERE id not IN (SELECT tablecolname FROM temp);
DROP TABLE temp;

/* Add the table_column data into the table ColsRemark
   which does exist in the sys tables,columns
   but does not exist in the table ColsRemark */
INSERT INTO ColsRemark (id , tablename, colname, colorder, addtime)
  SELECT CONCAT(CONCAT(LCASE(t.table_name) ,'_'), LCASE(c.column_name)) AS id,
         LCASE(t.table_name) AS tablename,
         LCASE(c.column_name) AS colname,
         c.ordinal_position AS colorder,
         now() AS addtime
    FROM information_schema.tables t
    INNER JOIN information_schema.columns c
          ON t.table_name = c.table_name
          WHERE t.table_schema = 'zcms'
          AND (
            NOT EXISTS
                (SELECT id FROM colsremark col
                  WHERE col.tablename = t.table_name and col.colname = c.column_name
                )
          );

/* Update the table TablesRemark's column colchanged
   if the col has be changed in the table ColsRemark */
UPDATE tablesremark SET colchanged = 1
  WHERE tablename IN (SELECT tablename FROM colsremark WHERE colchanged=1);

/* Update the table ColsRemark's column colorder
   so that the order can as the same as the sys columns
   optimized sql */
UPDATE colsremark,(
 SELECT c.column_name,t.table_name,c.ordinal_position 
	FROM information_schema.columns c,information_schema.tables t 
	WHERE c.table_name=t.table_name
) temp 
	SET colsremark.colorder=temp.ordinal_position 
	WHERE temp.column_name=colsremark.colname;
/* not optimized sql
UPDATE colsremark SET colorder =
  (SELECT c.ordinal_position
    FROM information_schema.columns c
    INNER JOIN information_schema.tables t
    ON c.table_name=t.table_name
    WHERE c.column_name=colsremark.colname
    AND t.table_name=colsremark.tablename);
*/

UPDATE colsremark SET categoryid = '{612F4576-4BF6-424A-BB92-04E836CAB4FF}' WHERE colname IN ('adder' , 'moder');
UPDATE colsremark SET categoryid = '{0ECC56D4-9F55-4633-8A41-172CB220ABF9}' WHERE colname IN ('delstatus');
UPDATE colsremark SET categoryid = '{57460F9A-7F41-4733-84F5-AB1DDEAFBB0F}' WHERE colname IN ('rdeptid');

END $$

DELIMITER ;