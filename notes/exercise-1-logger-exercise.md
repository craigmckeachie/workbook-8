## 🧪 EXERCISE 1 – Logger Exercise

---

### ✅ Step 1: Create a New Maven Project in IntelliJ

1. **Open IntelliJ IDEA**
2. Go to **File > New > Project**
3. Select **Maven** on the left sidebar
4. Uncheck **"Create from archetype"** unless you want a template
5. Click **Next**
6. Fill in:

   - **GroupId**: e.g., `com.example`
   - **ArtifactId**: e.g., `logger-exercise`
   - **Version**: leave default

7. Click **Finish**

IntelliJ will create a project structure like:

```
logging-demo/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── App.java
    │   └── resources/
```

---

### ✅ Step 2: Replace the Code in `App.java`

1. Navigate to `src/main/java/com/example/App.java`
2. Replace its contents with the following:

```java
package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logMeLikeYouDo("☕");
    }

    private static void logMeLikeYouDo(String input) {
        if (logger.isDebugEnabled()) {
            logger.debug("This is debug : " + input);
        }
        if (logger.isInfoEnabled()) {
            logger.info("This is info : " + input);
        }
        logger.warn("This is warn : " + input);
        logger.error("This is error : " + input);
        logger.fatal("This is fatal : " + input);
    }
}
```

---

### ✅ Step 3: Add Log4j2 Dependencies to `pom.xml`

1. Open `pom.xml`
2. Inside the `<dependencies>` section, paste the following:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```

3. IntelliJ should automatically sync the project. If not:

   - Click the **Maven** tab on the right → click the **refresh icon**
   - OR press **Cmd + Shift + O** (Mac) or **Ctrl + Shift + O** (Windows/Linux) to import changes

---

### ✅ Step 4: Create the Log4j2 Configuration File

1. In `src/main/`, right-click `resources` → **New > File** → name it: `log4j2.properties`
2. Paste the following configuration:

```properties
# Status logging for internal Log4j2 events
status = error

# Root logger options
rootLogger.level = TRACE
rootLogger.appenderRefs = console, file
rootLogger.appenderRef.console.ref = Console
rootLogger.appenderRef.file.ref = File

# Console appender
appender.console.type = Console
appender.console.name = Console
appender.console.target = SYSTEM_OUT
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# File appender
appender.file.type = RollingFile
appender.file.name = File
appender.file.fileName = log/application.log
appender.file.filePattern = log/application-%d{yyyy-MM-dd}.log.gz
appender.file.layout.type = PatternLayout
appender.file.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
appender.file.policies.type = Policies
appender.file.policies.time.type = TimeBasedTriggeringPolicy
appender.file.policies.time.interval = 1
appender.file.policies.time.modulate = true
```

> 💡 **Ensure** `resources` is marked as a _Resources Root_:

- Right-click on `resources` → **Mark Directory as > Resources Root**

---

### ✅ Step 5: Run the App

1. Right-click `App.java` → **Run 'App.main()'**
2. You should see logs in the console like:

```
2025-06-06 10:15:00 DEBUG App:10 - This is debug : ☕
2025-06-06 10:15:00 INFO  App:13 - This is info : ☕
2025-06-06 10:15:00 WARN  App:15 - This is warn : ☕
2025-06-06 10:15:00 ERROR App:16 - This is error : ☕
2025-06-06 10:15:00 FATAL App:17 - This is fatal : ☕
```

3. A file will also be created at: `log/application.log` with the same content.

---

### ✅ Bonus: Adjust Logging Level

To filter out lower-level logs (like DEBUG), change this line in the `log4j2.properties`:

```properties
rootLogger.level = INFO
```

Then only INFO and above will print.

---

### 🚨 Common Issues Checklist

| Problem                       | Possible Fix                                                    |
| ----------------------------- | --------------------------------------------------------------- |
| No logs show up in console    | Make sure `log4j2.properties` is under `src/main/resources`     |
| Logs only show WARN and above | Set `rootLogger.level = TRACE` or `DEBUG`                       |
| Build errors on LogManager    | Ensure both `log4j-core` and `log4j-api` are added to `pom.xml` |
| No log file created           | Check that `log/` directory exists or is writable               |

---
