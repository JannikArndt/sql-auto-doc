# data.software

Software bought for or by the company

| ID   | Name      | Type(Length)   | Nullable   | Default   | Example          | Comment                                    |
| ---- | --------- | -------------- | ---------- | --------- | ---------------- | ------------------------------------------ |
| 1    | id        | int(4)         | false      | 0         | 5                | Primary Key, technical                     |
| 2    | name      | nvarchar(800)  | true       | 0         | “Office 2016”    | Name of the software                       |
| 3    | creator   | nvarchar(800)  | true       | 0         | “Microsoft”      | Creator behind the software                |
| 4    | website   | nvarchar(800)  | true       | 0         | “www.office.com” | Website of the software product or creator |
| 5    | installed | datetime2(8)   | true       | 0         | 2016-04-16       | Date the software was first installed      |

