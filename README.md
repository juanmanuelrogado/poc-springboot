# API calls to Liferay from Spring Boot API

## Introduction

Proof of Concept to use a Spring Boot service as a bridge to call Liferay Headless APIs, in a LXC scope, using OAuth2 Authorization with PCKE flow.

The use case will be some web or mobile app getting an OAuth2 access token for a logged user, and calling a Spring Boot service, which will call the Liferay APIs propagating the access token.

For this PoC we won't implement the web client, but will use Postman for the original request instead.

## Install
### Checkout the project:

```
git clone https://github.com/juanmanuelrogado/poc-springboot.git
```


### Run Spring Boot service:

```
./gradlew bootRun
```

This will run a Tomcat listening at port 9080, with the endpoints: 

http://localhost:9080/hello

http://localhost:9080/get-users

### Start Liferay DXP

```
docker run -itd --name poc-springboot -p 8080:8080 liferay/dxp:7.4.13-u38
```

### Create new OAuth2 application
- Register a new OAuth2 application in Control Panel, with the following info:

    - **Callback URIs**: myapp://myapp
    - **Client Authentication method**: None
    - **Client Profile**: User Agent Application
    - **ALLOWED AUTHORIZATION TYPES**: PKCE Extended Authorization Code
    - **TRUSTED APPLICATION**: true

- After submit: 
    - edit **client_id**, and set value "**POC-Springboot**"
    - edit **Scopes** and enable **Liferay.Headless.Admin.User.everything**


### Import Postman collection
Import in Postman the collection included in [postman/PoC Spring Boot.postman_collection.json] (postman/PoC%20Spring%20Boot.postman_collection.json)

There are two HTTP requests included: 
- Test API Call to Liferay: direct access to Liferay APIs
- Test API Call to Spring Boot: access to Liferay APIs through a Spring Boot service

Both of them have Authorization settings for OAuth2.

## How to Use
- In Postman, select the "**Test API Call to Spring Boot**" request
- Open tab **Authorization**
- Click on "**Get New Access Token**" button. It will open a Liferay login form
- If everything goes OK, the user will be authenticated and a token will be retrieved on the callback
- Click on "**Use Token**" button
- Send request

