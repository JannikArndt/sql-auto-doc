# data.users

Employees who have software licenses.

| ID   | Name   | Type(Length)   | Nullable   | Default   | Example                      | Comment                                   |
| ---- | ------ | -------------- | ---------- | --------- | ---------------------------- | ----------------------------------------- |
| 1    | id     | bigint(8)      | false      | 0         | 5                            | Primary Key, technical                    |
| 2    | name   | nvarchar(800)  | false      | 0         | “Peter Peterson”             | Name of the employee                      |
| 3    | email  | nvarchar(800)  | false      | 0         | “peter.peterson@company.com” | E-mail address of the employee            |
| 4    | age    | int(4)         | true       | 0         | 42                           | The age of the employee                   |
| 5    | joined | datetime2(8)   | true       | 0         | 2017-01-01                   | Date when the employee joined our company |


# data.software

Software bought for or by the company

| ID   | Name      | Type(Length)   | Nullable   | Default   | Example          | Comment                                    |
| ---- | --------- | -------------- | ---------- | --------- | ---------------- | ------------------------------------------ |
| 1    | id        | int(4)         | false      | 0         | 5                | Primary Key, technical                     |
| 2    | name      | nvarchar(800)  | true       | 0         | “Office 2016”    | Name of the software                       |
| 3    | creator   | nvarchar(800)  | true       | 0         | “Microsoft”      | Creator behind the software                |
| 4    | website   | nvarchar(800)  | true       | 0         | “www.office.com” | Website of the software product or creator |
| 5    | installed | datetime2(8)   | true       | 0         | 2016-04-16       | Date the software was first installed      |

