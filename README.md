# IBL4J
![jitpack] ![workflowStatus] ![lastcommit] <br>
Java Wrapper for interacting with the Infinity Bots API

### Posting

##### IBL with bot id

```java
IBL ibl = new IBL.Builder("BOT_ID", "IBL_TOKEN");

// Post only server count
ibl.postStats(100);

// Post server count and shard count
ibl.postStats(100, 5);
```

##### IBL with jda instance

```java
// jda -> the JDA instance of your bot
IBLClient ibl = new IBLClient.Builder(jda, "IBL_TOKEN");

// Post only server count
ibl.postStats(100);

// Post server count and shard count
ibl.postStats(100, 5);
```

### Auto Posting

```java
// jda -> the JDA instance of your bot
IBLClient ibl = new IBLClient.Builder(jda, "IBL_TOKEN");

// Post stats every 1 hour
ibl.autoPostStats();

// Post stats every (1000 * 60 * 60) milliseconds [1 hour]
ibl.autoPostStats(1000*60*60);

// Post stats every 1 TimeUnit#HOURS
ibl.autoPostStats(1, TimeUnit.HOURS);
```

###### Custom ScheduledExecutorService for AutoPosting

```java
// executor -> Your instance of ScheduledExecutorService
IBLClient ibl = new IBLClient.Builder(jda, "IBL_TOKEN", executor);
```

**Note** : If you are using `Discord4J` and `Javacord` , you cannot use `IBLClient`.<br>
The other libraries will be added soon... 

## Installation

- Replace `VERSION` by the latest version

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.Zone-Infinity</groupId>
    <artifactId>IBL4J</artifactId>
    <version>VERSION</version>
</dependency>
```

### Gradle

```gradle
maven { url 'https://jitpack.io' }
```

```gradle
implementation 'com.github.Zone-Infinity:IBL4J:VERSION'
```

[lastcommit]:https://img.shields.io/github/last-commit/Zone-Infinity/IBL4J?style=flat-square

[workflowStatus]:https://img.shields.io/github/workflow/status/Zone-Infinity/IBL4J/Java%20CI%20with%20Maven?style=flat-square

[jitpack]:https://img.shields.io/jitpack/v/github/Zone-Infinity/IBL4J?style=flat-square
