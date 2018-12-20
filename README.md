# Email server
Test automation server to recieve emails in to an anonymous mailbox. This application works as a local substitute for mailinator. It also provides a REST api to query any inbox using a browser or automation tools.

Build the workspace:
## Compile:
```bash
foo@bar:~$ gradle wrapper
foo@bar:~$ ./gradlew clean
foo@bar:~$ ./gradlew build -x test
```

## Usage:
Run the mail server (port 25)
```bash
foo@bar:~$ sudo ./gradlew bootRun
```

In another terminal send yourself some sample emails
```bash
foo@bar:~$ ./gradlew --rerun-tasks test
```

Check an inbox for unopened email (returns list of email id's)
```bash
foo@bar:~$ curl http://localhost:8080/email/inbox?emailAddress=bob@example.com
```

Open an email in an inbox using an email id (marks as read)
```bash
foo@bar:~$ http://localhost:8080/email/open?id=1
```

Check all emails
```bash
foo@bar:~$ curl http://localhost:8080/email/all
```
