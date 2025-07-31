# HowTo

### 1. Enter OOP3_Assignment2

```
cd OOP3_Assignment2

```



### 2. Compile

```
javac -d out \
  src/utilities/*.java \
  src/implementations/*.java \
  src/exceptions/*.java \
  src/appDomain/XMLParser.java
```



### 3. Package jar

```shell
 jar cvfm Parser.jar manifest.txt -C bin .
```



### 4. Run

```shell
java -jar Parser.jar res/sample1.xml
```



```shell
java -jar Parser.jar res/sample2.xml
```

Output:

```shell
$ java -jar Parser.jar res/sample2.xml
Error at line 8 <i> is not constructed correctly.
Error at line 32 <i> is not constructed correctly.
Error at line 32 <b> is not constructed correctly.
Error at line 8 </i> is not constructed correctly.
Error at line 9 </Language> is not constructed correctly.
Error at line 12 </Language> is not constructed correctly.
Error at line 15 </Language> is not constructed correctly.
Error at line 18 </Language> is not constructed correctly.
Error at line 21 </I> is not constructed correctly.
Error at line 22 </Language> is not constructed correctly.
Error at line 25 </Language> is not constructed correctly.
Error at line 28 </Language> is not constructed correctly.
Error at line 29 </Driver> is not constructed correctly.
Error at line 30 </Category> is not constructed correctly.
Error at line 31 </Submission> is not constructed correctly.

```



```shell
$ java -jar Parser.jar res/sample1.xml
Error at line 8 </Language> is not constructed correctly.
Error at line 11 </Language> is not constructed correctly.
Error at line 14 </Language> is not constructed correctly.
Error at line 17 </Language> is not constructed correctly.
Error at line 20 </Language> is not constructed correctly.
Error at line 23 </Language> is not constructed correctly.
Error at line 26 </Language> is not constructed correctly.
Error at line 27 </Driver> is not constructed correctly.
Error at line 28 </Category> is not constructed correctly.
Error at line 29 </Submission> is not constructed correctly.

```



