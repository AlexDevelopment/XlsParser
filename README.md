# XlsParser
## Sample

```java
XlsParser.takeFirstSheetWithType(Item.class)
                .onError(this::dispatchError)
                .parse(file);
```
## Integration
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    implementation 'com.github.khodanovich:XlsParser:Tag'
}
```
