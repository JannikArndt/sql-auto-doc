DECLARE @sch NVARCHAR(50) = N'SCHEMA'
DECLARE @tab NVARCHAR(50) = N'TABLE'
DECLARE @col NVARCHAR(50) = N'COLUMN'
DECLARE @comm NVARCHAR(50) = N'MS_Description'
DECLARE @exam NVARCHAR(50) = N'Example'
DECLARE @schema NVARCHAR(50) = N'data'

DECLARE @table NVARCHAR(50) = N'users'
EXEC sp_addextendedproperty @comm, N'Employees who have software licenses.', @sch, @schema, @tab, @table;

DECLARE @column NVARCHAR(50) = N'id'
EXEC sp_addextendedproperty @comm, N'Primary Key, technical', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'5', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'name'
EXEC sp_addextendedproperty @comm, N'Name of the employee', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'“Peter Peterson”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'email'
EXEC sp_addextendedproperty @comm, N'E-mail address of the employee', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'“peter.peterson@company.com”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'age'
EXEC sp_addextendedproperty @comm, N'The age of the employee', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'42', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'joined'
EXEC sp_addextendedproperty @comm, N'Date when the employee joined our company', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'2017-01-01', @sch, @schema, @tab, @table, @col, @column;


Set @table = N'software'
EXEC sp_addextendedproperty @comm, N'Software bought for or by the company', @sch, @schema, @tab, @table;

SET @column = N'id'
EXEC sp_addextendedproperty @comm, N'Primary Key, technical', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'5', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'name'
EXEC sp_addextendedproperty @comm, N'Name of the software', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'“Office 2016”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'creator'
EXEC sp_addextendedproperty @comm, N'Creator behind the software', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'“Microsoft”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'website'
EXEC sp_addextendedproperty @comm, N'Website of the software product or creator', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'“www.office.com”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'installed'
EXEC sp_addextendedproperty @comm, N'Date the software was first installed', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'2016-04-16', @sch, @schema, @tab, @table, @col, @column;
