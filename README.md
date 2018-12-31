# Email server
Test automation server to recieve emails in to an anonymous mailbox. This application works as a local substitute for mailinator. It also supports full-text search and provides a useful REST api to perform search queries across the emails in an inbox.

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

Check an inbox (paginated)
```bash
foo@bar:~$ curl http://localhost:8025/email/inbox?inboxName=bob@example.com&limit=2&sort=DESC&page=0
```

Open an email using an email id (marks as read)
```bash
foo@bar:~$ curl http://localhost:8025/email/open?id=1
```

Delete email
```bash
foo@bar:~$ curl http://localhost:8025/email/delete?id=1
```

Full-text search subject/body/fromAddress in an inbox (paginated)
```bash
foo@bar:~$ curl http://localhost:8025/email/inbox/search?inboxName=bob@example.com&query=lorem&limit=2&page=0
```

You may need to decode the emails. Use one of the following tools:
* [Base64 Decoder](https://www.base64decode.org/)
* [Quoted Printable Decoder](https://www.webatic.com/quoted-printable-convertor)
