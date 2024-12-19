### Авторизированные запросы, нуждаются в заголовке Authorization со значением "Bearer " + accessToken
#### Example:
```
Authorization : Bearer d4a76068f5104f26975499d22bcd11cc1665995491673
```


## 1.1. Получить список всех пользователей

## GET
#####  /api/v1/profile/admin/userProfileList
### Request

_**parameters**_

```
- int page (defaultValue = "1")
- int size (defaultValue = "50")
```
### Response

### Success:
##### status 200
##### body:
```json
{
  "content": [
    {
      "id": 1,
      "username": "user1",
      "email": "user1@example.com",
      "company": "Company A"
    },
    {
      "id": 2,
      "username": "user2",
      "email": "user2@example.com",
      "company": "Company B"
    },
    {
      "id": 3,
      "username": "user3",
      "email": "user3@example.com",
      "company": "Company C"
    },
    {
      "id": 4,
      "username": "user4",
      "email": "user4@example.com",
      "company": "Company D"
    }
  ],
  "page": 1,
  "size": 50,
  "totalElements": 4,
  "totalPages": 1,
  "last": true
}
```


## 1.2. Получить список штампов времени по имени пользователя или email

## GET
#####  /api/v1/device/admin/timestamp
### Request
_**parameters**_

```
- int page (defaultValue = "1")
- int size (defaultValue = "50")
- string username (required = false)
- string email (required = false)
- long beginTime (required = false)
- long endTime (required = false)
- string deviceModel (required = false)
- string deviceSerial (required = false)
```
### Response

### Success:
##### status 200
##### body:
```json
{
  "content": [
    {
      "unixtime": 1700004000,
      "deviceModel": "Model A",
      "deviceSerial": "Serial-1-E",
      "firmwareVersion": "v1.4"
    },
    {
      "unixtime": 1700003000,
      "deviceModel": "Model A",
      "deviceSerial": "Serial-1-D",
      "firmwareVersion": "v1.3"
    },
    {
      "unixtime": 1700002000,
      "deviceModel": "Model A",
      "deviceSerial": "Serial-1-C",
      "firmwareVersion": "v1.2"
    },
    {
      "unixtime": 1700001000,
      "deviceModel": "Model A",
      "deviceSerial": "Serial-1-B",
      "firmwareVersion": "v1.1"
    },
    {
      "unixtime": 1700000000,
      "deviceModel": "Model A",
      "deviceSerial": "Serial-1-A",
      "firmwareVersion": "v1.0"
    }
  ],
  "page": 1,
  "size": 50,
  "totalElements": 5,
  "totalPages": 1,
  "last": true
}
```

### Error
##### status 400
##### body:
```json
{
  "code": "NOT_FOUND",
  "message": "User not found"
}
```


## 1.3. Удалить пользователя по id

## DELETE (not POST)
### Request
#####  api/v1/profile/admin/deleteUser
##### body:
```json
{
  "id": 3
}
```
### Response
### Success:
##### status 200

### Error:
##### status 400
