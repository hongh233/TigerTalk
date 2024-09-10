# Tiger Talk

The social media platform for Dalhousie University, written in Java, React, Springboot, see the [deployment website](http://hongh651.s3-website.ca-west-1.amazonaws.com): 

http://hongh651.s3-website.ca-west-1.amazonaws.com

---
## Build and Run
To build on your own, follow these instructions:

1. Make sure you have [Maven](https://maven.apache.org/install.html) and [JDK 17](https://www.oracle.com/ca-en/java/technologies/downloads/#java17) installed. Other JDk may not work. 

2. Going to ./backend/src/main/resources, set the application.properties and related files, instruction is written there

3. Going to ./frontend/env, set the REACT_APP_API_URL property, instruction is written there

4. Open a terminal in the TigerTalk directory and run the following commands:
<!--------------------------------------- frontend --------------------------------------->

### Frontend
To set up and run the frontend, navigate to the `frontend` directory and run the following commands:

<div class="inline-container">
<span class="inline-title">Install Dependencies :</span>

```shell
cd ./frontend && npm install
```
</div>


<div class="inline-container">
<span class="inline-title">Run :</span>

```shell
cd ./frontend && npm start
```
</div>


<div class="inline-container">
<span class="inline-title">Build :</span>

```shell
cd ./frontend && npm run build
```
</div>


<div class="inline-container">
<span class="inline-title">Stop :</span>

```shell
chmod +x ./shell/stopFrontend.sh && ./shell/stopFrontend.sh
```
</div>


<!--------------------------------------- backend --------------------------------------->
### Backend
To build and run the backend, navigate to the `backend` (root) directory and run the following commands:

<div class="inline-container">
<span class="inline-title">Build :</span>

```shell
cd ./backend && mvn clean package
```
</div>


<div class="inline-container">
<span class="inline-title">Run :</span>

```shell
java -jar ./backend/target/Tiger_Talks-0.0.1-SNAPSHOT.jar
```
</div>


<div class="inline-container">
<span class="inline-title">Stop :</span>

```shell
chmod +x ./shell/stopBackend.sh && ./shell/stopBackend.sh
```
</div>

*** Stop only works for linux/macOS ***



---
## Unit Test
To run all unit tests in the project, run this command:
```shell
cd ./backend && mvn clean test -X
```

---
## Database Setting
<div class="inline-container">
<span class="inline-title">Drop Database:</span>

```shell
curl -X POST http://localhost:8085/api/scripts/dropTables
```
</div>


<div class="inline-container">
<span class="inline-title">Initialize Database:</span>

```shell
curl -X POST http://localhost:8085/api/scripts/setUp
```
</div>

---
## Tips
1.Ensure you have Maven installed on your system. You can check by running `mvn -v` in your terminal. If Maven is not installed, you can download and install it from [Apache Maven's official site](https://maven.apache.org/download.cgi).

2.If you have installed maven but backend code still showing wrong syntax, going to ./backend/pom.xml, right click on it and choose: Add as Maven Project

2.IntelliJ IDEA is a recommended IDE on this project.


---

<style>
   pre {
      font-size: 14px;
      padding: 2px 5px 2px 5px;
      border-radius: 5px;
      display: inline-block;
      margin: 0;
      vertical-align: middle;
   }
   .inline-title {
      margin-right: 8px;
      line-height: 1.5;
   }
   .inline-container {
      margin-bottom: 3px;
margin-left: 2px;
   }
</style>

