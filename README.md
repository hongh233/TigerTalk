# Tiger Talk

The social media platform for Dalhousie University, written in Java, React

_[Gitlab Repository](https://git.cs.dal.ca/courses/2024-summer/csci3130/Group02)_

---
## Build and Run
To build on your own, follow these instructions. First, make sure you have [JDK 17](https://www.oracle.com/ca-en/java/technologies/downloads/#java17) installed. Other JDk may not work. Open a terminal in the Group02 directory and run the following commands:
### Frontend
To set up and run the frontend, navigate to the `frontend` directory and run the following commands:

_Install Dependencies:_
```shell
cd src/main/java/com/group2/Tiger_Talks/frontend
npm install
```
_Run the Frontend:_
```shell
cd src/main/java/com/group2/Tiger_Talks/frontend
npm start
```
_Stop Frontend Running:_
```shell
chmod +x stop-frontend.sh
./stop-frontend.sh
```

### Backend
To build and run the backend, navigate to the `Group02` (root) directory and run the following commands:

_Build the Backend:_
```shell
mvn clean package
```
_Run the Backend:_
```shell
java -jar target/Tiger_Talks-0.0.1-SNAPSHOT.jar
```
_Stop Backend Running:_
```shell
chmod +x stop-backend.sh
./stop-backend.sh
```


### Unit Test
To run all unit tests in the project, run this command:

`mvn clean test -X`


---
## Tips
1. Backend Runner is working on Dal MySQL server, it only runs on Dal Internet. To use Dal MySQL server remotely, please install the [VPN Client Software Application](https://dalu.sharepoint.com/sites/its/SitePages/vpn.aspx?_ga=2.182314855.1320124946.1719698724-195095931.1712083041&xsdata=MDV8MDJ8fGE0YjJmZjljZDc2YTRkYjY3YTAyMDhkYzk5MzFmOTRifDYwYjgxOTk5MGI3ZjQxMmQ5MmEzZTE3ZDhhZTllM2UwfDB8MHw2Mzg1NTM2ODcwOTQ2ODgyNTh8VW5rbm93bnxWR1ZoYlhOVFpXTjFjbWwwZVZObGNuWnBZMlY4ZXlKV0lqb2lNQzR3TGpBd01EQWlMQ0pRSWpvaVYybHVNeklpTENKQlRpSTZJazkwYUdWeUlpd2lWMVFpT2pFeGZRPT18MXxMMk5vWVhSekx6RTVPak5oWlRNMU9ESmtMV0psWXpNdE5ETXpaUzA1TVRFMExXRTJNVEZpWTJFNE16UTBZbDlsTVdSbE5tTTFZUzAzTTJKbUxUUmxNREV0T0RRek9TMDRZekppWlRabE1qTXpabVJBZFc1eExtZGliQzV6Y0dGalpYTXZiV1Z6YzJGblpYTXZNVGN4T1RjM01Ua3dPRE0xTVE9PXxjZWFlZGRlODkxMWE0NzY4N2EwMjA4ZGM5OTMxZjk0YnxlYjRjODVjOGU5MzI0NGI5YmZlYzIwODI1MTNmOTFhYw%3D%3D&sdata=MGRUY3Zhb2pyVE0vaXdTM3VJUU1EVWtwWUlQaUhnMVk5bjM5YXlKaklrdz0%3D&ovuser=60b81999-0b7f-412d-92a3-e17d8ae9e3e0%2Chn582183%40dal.ca&OR=Teams-HL&CT=1721367432507&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiI1MC8yNDA2MTMxODQwOCIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D) with your NetID and password.
2. To work on your own database (local host), change files in this path: /src/main/resources. 

   In application.properties, set prod to be dev. 

   In application-dev.properties, feel free to set your database info.
3. Ensure you have Maven installed on your system. You can check by running `mvn -v` in your terminal. If Maven is not installed, you can download and install it from [Apache Maven's official site](https://maven.apache.org/download.cgi).