##1.1 根据ID查找用户信息

调用该API，可以获取当前用户基本信息

- **获取用户的当前基本信息**

```json
url:/user/getById
method：GET
successResponse：
{
    "userId": 1,
    "username": "liu.sun",
    "firstName": "liu",
    "lastName": "xingxuan",
    "email": "853555374@qq.com",
    "avatarUrl": "",
    "roles": [
        "user"
    ]
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    userId|   bigint |  用户id |
|    username|   Varchar |  用户名 |
|    firstName|   Varchar |  名 |
|    lastName|   Varchar |  姓 |
|    email|   Varchar |  邮箱 |
|    roles|   Varchar |  用户角色 |
  
  



##m.1.2 新增用户

调用该API，可以新增用户

- **新增用户信息**

```json
url:/user/add
method：POST

successResponse：
{
    "username": "laowa2233ng",
    "firstName": "SD32333F",
    "lastName": "laowa2333ng",
    "email": "214123343@qq.com",
    "password":123456,
    "avatarUrl": "SDFF33DS",
    "roles": [
        "user"
    ]
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    username|   Varchar |  用户名 |
|    firstName|   Varchar |  名 |
|    lastName|   Varchar |  姓 |
|    email|   Varchar |  邮箱 |
|    password|   Varchar |  密码 |
|    roles|   Varchar |  用户角色 |

##m.1.3 用户登录操作

调用该API，可以根据当前用户登录操作后的状态

- **设置状态**

```json
url:/user/login
method：GET


successResponse：
{
    "username": "liu.sun",
    "password":123456
}
```
| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    username|   Varchar |  用户名 |
|    password|   Varchar |  密码 |

##m.1.4 修改用户信息

调用该API，可以修改用户信息

- **修改用户信息**

```json
url:/user/add
method：POST
successResponse：
{
    "userId": "1",
    "username": "laowa2233ng",
    "firstName": "SD32333F",
    "lastName": "laowa2333ng",
    "email": "214123343@qq.com",
    "password":123456,
    "avatarUrl": "SDFF33DS",
    "roles": [
        "user"
    ]
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    userId|   Varchar |  用户Id |
|    username|   Varchar |  用户名 |
|    firstName|   Varchar |  名 |
|    lastName|   Varchar |  姓 |
|    email|   Varchar |  邮箱 |
|    password|   Varchar |  密码 |
|    roles|   Varchar |  用户角色 |

##m.1.5 分页获取用户信息

调用该API，可以分页获取用户信息

- **分页获取用户信息**

```json
url:/user/getUsersWithPage
method：GET
successResponse：
{
    "total": 8,
    "list": [
        {
            "userId": 18,
            "username": "laowa2233ng"
        },
        {
            "userId": 5,
            "username": "laowang"
        },
        {
            "userId": 1,
            "username": "liu.sun"
        },
        {
            "userId": 3,
            "username": "sfs"
        },
        {
            "userId": 4,
            "username": "xiaobai"
        },
        {
            "userId": 15,
            "username": "夏红"
        },
        {
            "userId": 14,
            "username": "爽肤水"
        },
        {
            "userId": 16,
            "username": "玩玩"
        }
    ],
    "pageNum": 1,
    "pageSize": 10,
    "size": 8,
    "startRow": 1,
    "endRow": 8,
    "pages": 1,
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
        1
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    username|   Varchar |  用户名 |
|    password|   Varchar |  密码 |

##m.1.6 批量删除用户

调用该API，可以批删用户信息

- **批删用户信息**

```json
url:/user/batchdelete
method：POST
successResponse：
{
    "userIds":[1,2],
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    userIds|   Varchar |  用户Id集合 |

##m.1.6 重置密码

调用该API，重置用户密码

- **重置用户密码**

```json
url:/user/resetPassword
method：GET
successResponse：
{
   
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    email|   Varchar |  邮箱 |


##m.1.6 重置效验密码

调用该API，重置效验用户密码

- **重置效验用户密码**

```json
url:/user/verifyCode
method：GET
successResponse：
{
   
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    code|   Varchar |  随机码 |
|    email|   Varchar |  邮箱 |

##m.1.7 上传图片

调用该API，上传图片

- **上传图片**

```json
url:/user/uploadAvatar2
method：POST
successResponse：
{

}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    MultipartFile|   file |  文件 |

##m.2.1 根据ID获取商品信息

调用该API，获取商品信息

- **商品信息**

```json
url:/product/getById
method：GET
successResponse：
{
    "productId": 16,
    "productCode": "324",
    "name": "234",
    "price": 234,
    "vat": null,
    "brand": "2344",
    "point": null,
    "pictureMainUrl": "53cb43c0-7929-4a50-9a4e-d7c8ddb9ea7d.png",
    "pictureUrls": "null",
    "detail": null
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    productId|   bigint |  商品ID |
|    productCode|   Varchar |  商品码 |
|    name|   Varchar |  商品名 |
|    price|   Varchar |  商品价格 |
|    vat|   int |  增值税 |
|    brand|   Varchar |  品牌 |
|    point|   Double |  积分 |
|    pictureMainUrl|   Varchar |  图片的主要路径 |
|    pictureUrls|   Varchar |  图片的utl |
|    detail|   Varchar |  商品详情 |

##m.2.2 根据ID获取商品信息

调用该API，获取商品信息

- **商品信息**

```json
url:/product/getWithPage
method：GET
successResponse：
{
    "total": 4,
    "list": [
        {
            "productId": 1,
            "productCode": "1231",
            "name": "2sdfs",
            "price": 213,
            "vat": null,
            "brand": "1sddf",
            "point": null,
            "pictureMainUrl": "f1e15b67-fa11-4994-94d1-f60827db73ae.png",
            "pictureUrls": "null",
            "detail": null
        },
        {
            "productId": 2,
            "productCode": "213",
            "name": "sf",
            "price": 234,
            "vat": null,
            "brand": "fs",
            "point": null,
            "pictureMainUrl": "0ae0259c-453d-4d2d-9899-c360885e4b15.png",
            "pictureUrls": "null",
            "detail": null
        }
    ],
    "pageNum": 1,
    "pageSize": 2,
    "size": 2,
    "startRow": 1,
    "endRow": 2,
    "pages": 2,
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
        1,
        2
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 2
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    productId|   bigint |  商品ID |
|    productCode|   Varchar |  商品码 |
|    name|   Varchar |  商品名 |
|    price|   Varchar |  商品价格 |
|    vat|   int |  增值税 |
|    brand|   Varchar |  品牌 |
|    point|   Double |  积分 |
|    pictureMainUrl|   Varchar |  图片的主要路径 |
|    pictureUrls|   Varchar |  图片的utl |
|    detail|   Varchar |  商品详情 |

##m.2.3 添加商品信息

调用该API，添加商品信息

- **商品信息**

```json
url:/product/add
method：POST
successResponse：
{
  
        "productCode": "1231",
        "name": "2sdfs",
        "price": 213,
        "vat": null,
        "brand": "1sddf",
        "point": null,
        "pictureMainUrl":"f1e15b67-fa11-4994-94d1-f60827db73ae.png",
        "pictureUrls": "null",
        "detail": null


}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    productCode|   Varchar |  商品码 |
|    name|   Varchar |  商品名 |
|    price|   Varchar |  商品价格 |
|    vat|   int |  增值税 |
|    brand|   Varchar |  品牌 |
|    point|   Double |  积分 |
|    pictureMainUrl|   Varchar |  图片的主要路径 |
|    pictureUrls|   Varchar |  图片的utl |
|    detail|   Varchar |  商品详情 |


##m.2.4 修改商品信息

调用该API，修改商品信息

- **修改商品信息**

```json
url:/product/update
method：POST
successResponse：
{
        "productId":1,
        "productCode":"1231",
        "name": "2sdfs",
        "price": 213,
        "vat": null,
        "brand": "1sddf",
        "point": null,
        "pictureMainUrl":"f1e15b67-fa11-4994-94d1-f60827db73ae.png",
        "pictureUrls": "null",
        "detail": null


}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    productCode|   Varchar |  商品码 |
|    name|   Varchar |  商品名 |
|    price|   Varchar |  商品价格 |
|    vat|   int |  增值税 |
|    brand|   Varchar |  品牌 |
|    point|   Double |  积分 |
|    pictureMainUrl|   Varchar |  图片的主要路径 |
|    pictureUrls|   Varchar |  图片的utl |
|    detail|   Varchar |  商品详情 |

##m.2.5 批删商品信息

调用该API，批删商品信息

- **修改商品信息**

```json
url:/product/batchdelete
method：POST
successResponse：
{
        "productIds":[1,2],
}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    productIds|   List |  商品ID集合 |

##m.2.6 上传图片

调用该API，上传图片

- **上传图片**

```json
url:/product/uploadProductMainPhoto
method：POST
successResponse：
{

}
```

| ResponseField     |     Type |   Description   | 
| :--------------: | :--------:| :------: |
|    MultipartFile|   file |  文件 |