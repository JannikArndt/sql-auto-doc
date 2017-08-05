# data.software

Software bought for or by the company

| ID        | Name      | Type(Length)   | Nullable   | Default   | Example          | Comment                                    |
| --------- | --------- | -------------- | ---------- | --------- | ---------------- | ------------------------------------------ |
| 487672785 | id        | int(4)         | false      | 0         | 5                | Primary Key, technical                     |
| 487672785 | name      | nvarchar(800)  | true       | 0         | “Office 2016”    | Name of the software                       |
| 487672785 | creator   | nvarchar(800)  | true       | 0         | “Microsoft”      | Creator behind the software                |
| 487672785 | website   | nvarchar(800)  | true       | 0         | “www.office.com” | Website of the software product or creator |
| 487672785 | installed | datetime2(8)   | true       | 0         | 2016-04-16       | Date the software was first installed      |

