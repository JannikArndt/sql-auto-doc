# data.users

Employees who have software licenses.

| ID        | Name   | Type(Length)   | Nullable   | Default   | Example                      | Comment                                   |
| --------- | ------ | -------------- | ---------- | --------- | ---------------------------- | ----------------------------------------- |
| 455672671 | id     | bigint(8)      | false      | 0         | 5                            | Primary Key, technical                    |
| 455672671 | name   | nvarchar(800)  | false      | 0         | “Peter Peterson”             | Name of the employee                      |
| 455672671 | email  | nvarchar(800)  | false      | 0         | “peter.peterson@company.com” | E-mail address of the employee            |
| 455672671 | age    | int(4)         | true       | 0         | 42                           | The age of the employee                   |
| 455672671 | joined | datetime2(8)   | true       | 0         | 2017-01-01                   | Date when the employee joined our company |

