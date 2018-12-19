# Email server
Test automation server to recieve emails in to an anonymous mailbox. This application works as a local substitute for mailinator. It also provides a REST api to query any inbox using a browser or automation tools.

Build the workspace:
```
gradle wrapper
./gradlew clean
./gradlew build -x test
```

Run the mail server (port 25)
```
sudo ./gradlew bootRun
```

Send yourself some sample emails
```
./gradlew --rerun-tasks test
```
