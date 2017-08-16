CREATE SCHEMA [data]
GO

-- Create tables

CREATE TABLE [data].[users](
    [id] [bigint] IDENTITY(1,1) PRIMARY KEY NOT NULL,
    [name] [nvarchar](400) NOT NULL,
    [email] [nvarchar](400) NOT NULL,
    [age] INT,
    joined DATETIME2)

CREATE TABLE data.software(
    id int IDENTITY (1,1) PRIMARY KEY NOT NULL,
    name NVARCHAR(400),
    creator NVARCHAR(400),
    website NVARCHAR(400),
    installed DATETIME2
)

CREATE TABLE data.licenses(
    id int IDENTITY (1,1) PRIMARY KEY NOT NULL ,
    userId BIGINT NOT NULL ,
    softwareId INT NOT NULL,
    CONSTRAINT fk_license_to_user FOREIGN KEY (userid) REFERENCES data.users (id),
    CONSTRAINT fk_license_to_software FOREIGN KEY (softwareId) REFERENCES data.software (id)
)

GO

-- Insert example data

INSERT INTO data.users (name, email, age, joined) VALUES
    ('Phil', 'phil@example.com', 37, '2002-03-01'),
    ('Monica', 'monica@example.com', 43, '2005-01-01')

INSERT INTO data.software (name, creator, website, installed) VALUES
    ('Office 2017', 'Microsoft', 'www.office.com', '2017-01-01'),
    ('Windows 10', 'Microsoft', 'www.windows.com', '2016-10-12')

INSERT INTO data.licenses (userId, softwareId) VALUES
    ((SELECT id from data.users WHERE name = 'Phil'),   (SELECT id from data.software WHERE name = 'Office 2017')),
    ((SELECT id from data.users WHERE name = 'Phil'),   (SELECT id from data.software WHERE name = 'Windows 10')),
    ((SELECT id from data.users WHERE name = 'Monica'), (SELECT id from data.software WHERE name = 'Office 2017')),
    ((SELECT id from data.users WHERE name = 'Monica'), (SELECT id from data.software WHERE name = 'Windows 10'))

GO

-- Add comments

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
EXEC sp_addextendedproperty @comm, N'Date when the employee joined our company', @sch, @schema, @tab, @table, @col,
                            @column;
EXEC sp_addextendedproperty @exam, N'2017-01-01', @sch, @schema, @tab, @table, @col, @column;


SET @table = N'software'
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
EXEC sp_addextendedproperty @comm, N'Website of the software product or creator', @sch, @schema, @tab, @table, @col,
                            @column;
EXEC sp_addextendedproperty @exam, N'“www.office.com”', @sch, @schema, @tab, @table, @col, @column;

SET @column = N'installed'
EXEC sp_addextendedproperty @comm, N'Date the software was first installed', @sch, @schema, @tab, @table, @col, @column;
EXEC sp_addextendedproperty @exam, N'2016-04-16', @sch, @schema, @tab, @table, @col, @column;

GO
