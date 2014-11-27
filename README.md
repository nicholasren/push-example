stable-push
===========
This is a example of build rest api with spray.

tasks:

- Device registration api
    - AC:
        - save PlatformEndpointArn for each device
        - returns device id

- App registration
    - Assumption:
        - PlatformPrincipal is provided
        - PlatformCredential is provided

    - AC:
        - register app and save PlatformApplicationArn

- Push API
    - AC:
        - receive device id and message
        - message got delivered



#### API Doc:

##### Register device:
```
POST  http://localhost:8080/devices

{
    "token" : "",
    "platform": "apple"
}
```

#### List registered devices:
```
GET  http://localhost:8080/devices

```


##### send push message:

```
POST  http://localhost:8080/notifications

{
  "target": "arn:aws:sns:ap-southeast-2:369407384105:endpoint/APNS_SANDBOX/xiaojun_push/d81ba6c5-d462-3f0c-bfef-54e9552872bf",
  "text": "this is xiaojun from API",
  "title": "this is xiaojun from API"
}
```



Thoughts on Actor:

- actor is good for asynchronous operation, don't need to care about return value

- actor is good for scalable work

- you need to define a segregation line between synchronize code and actor(asynchronize) code.

- if you care about the return value of a actor, you may want to imply `ask pattern`

- `Future` could also be used to represent a time-consuming operation, and use `mapTo` method to collect the result of `Future`

- json serialization/deserialization, use `spray-json`, have to import a `XXXJsonProtocol` to get implicit Marshaller/Unmarshaller while defining routes

