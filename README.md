# IBL4J

### Posting

```java
IBL ibl = new IBL.Builder("BOT_ID","IBL_TOKEN");

// Post only server count
ibl.postStats(100);

// Post server count and shard count
ibl.postStats(100, 5);
```

### Auto Posting
```java
// IBLClient can also post normally -> IBLClient#postStats()
// jda -> the JDA instance of your bot
IBLClient ibl = new IBLClient.Builder(jda, "IBL_TOKEN");

// Post stats every 1 hour
ibl.autoPostStats();

// Post stats every (1000 * 60 * 60) milliseconds [1 hour]
ibl.autoPostStats(1000 * 60 * 60);

// Post stats every 1 TimeUnit#HOURS
ibl.autoPostStats(1, TimeUnit.HOURS);
```

## Installation
### Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
    <groupId>com.github.Zone-Infinity1</groupId>
    <artifactId>IBL4J</artifactId>
    <version>1.0</version>
</dependency>
```
### Gradle
```gradle
maven { url 'https://jitpack.io' }
```
```gradle
implementation 'com.github.Zone-Infinity:IBL4J:1.0'
```

