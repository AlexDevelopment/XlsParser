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
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    compile'org.apache.poi:poi-ooxml:4.0.0'
    implementation 'com.github.khodanovich:XlsParser:1.0.2-beta'
}
```
