


# ✅ EXERCISE 1 – Log4j2 Logging in a Maven Java Project (Using IntelliJ IDEA)

---

### 🧱 Step 1: Create a New Empty Maven Project

1. **Open IntelliJ IDEA**
2. Click **"New Project"** (or go to **File > New > Project**)
3. In the **New Project** screen:

   * Select **Maven**
   * Do **not** check "Add sample code"
   * Make sure a valid **JDK** is selected (e.g., Java 17 or higher)
4. Click **Next**
5. Fill in the fields:

   * **GroupId**: `com.example`
   * **ArtifactId**: `logging-demo`
   * **Version**: leave as-is
   * **Base package**: leave blank (you’ll create it manually)
6. Click **Create**

You should now see a minimal project structure like this:

```
logging-demo/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   └── resources/
```

---

### 🛠 Step 2: Create the Package and `App.java` File

1. In the **Project view**, navigate to:
   `src/main/java`
2. Right-click the `java` folder → **New > Package**

   * Name it: `com.example`
3. Right-click on the new `com.example` package → **New > Java Class**

   * Name it: `App`
4. Replace the contents of `App.java` with this:

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

### 📦 Step 3: Add Log4j2 Dependencies to `pom.xml`

1. Open the `pom.xml` file (in the root of the project)
2. Add the following inside the `<dependencies>` tag:

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

3. IntelliJ will prompt you to **import Maven changes**. Click **“Load Maven Changes”** or use the **refresh button** in the Maven tab.

---

### 🗂 Step 4: Create the `log4j2.properties` File

1. In the **Project view**, go to:
   `src/main/resources`
2. Right-click the `resources` folder → **New > File**

   * Name it: `log4j2.properties`
3. Paste the following configuration into the file:

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

> ✅ Make sure `resources` is marked as a **Resources Root**:
> Right-click the `resources` folder → **Mark Directory as > Resources Root**

---

### ▶️ Step 5: Run the App

1. Right-click `App.java` → **Run 'App.main()'**
2. You should see output in the console like:

```
2025-06-06 10:30:00 DEBUG App:10 - This is debug : ☕
2025-06-06 10:30:00 INFO  App:13 - This is info : ☕
2025-06-06 10:30:00 WARN  App:15 - This is warn : ☕
2025-06-06 10:30:00 ERROR App:16 - This is error : ☕
2025-06-06 10:30:00 FATAL App:17 - This is fatal : ☕
```

3. A log file will also be created here:
   `log/application.log`

---

### 🔧 BONUS: Change the Logging Level

If you want to show only messages from INFO and above, change this line in `log4j2.properties`:

```properties
rootLogger.level = INFO
```

Valid levels (from lowest to highest): `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `FATAL`

---

### 🧯 Troubleshooting Tips

| Problem                            | Fix                                                                               |
| ---------------------------------- | --------------------------------------------------------------------------------- |
| No logs appear                     | Ensure `log4j2.properties` is in `src/main/resources`                             |
| Only WARN and above logs show      | Check that `rootLogger.level` is set to `TRACE` or `DEBUG`                        |
| `LogManager` or `Logger` not found | Make sure Maven dependencies were added and Maven was reloaded                    |
| Log file not created               | Make sure the app has permission to create a `log/` folder, or create it manually |

---

Let me know if you’d like this as:

* A **PDF handout**
* A **Markdown version** for GitHub or LMS
* A **starter IntelliJ project ZIP file**

I can generate any of those for you.
