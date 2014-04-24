Unidecode
=========
[![Build Status](https://travis-ci.org/jirutka/unidecode.svg)](https://travis-ci.org/jirutka/unidecode)
[![Coverage Status](https://img.shields.io/coveralls/jirutka/unidecode.svg)](https://coveralls.io/r/jirutka/unidecode)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.unidecode/unidecode/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.unidecode/unidecode)

Unidecode is a Java port of the [Text::Unidecode](http://search.cpan.org/~sburke/Text-Unidecode/lib/Text/Unidecode.pm)
from Perl that solves transliteration of an Unicode text to US-ASCII. However, this implementation is not limited only
to ASCII characters, currently supports also ISO-8859-2 (aka Latin 2).


How to Use
----------

### Transliterate to ASCII

```java
Unidecode unidecode = Unidecode.withCharset("ASCII");

unidecode.decode("České „uvozovky“");
>>> Ceske "uvozovky"

unidecode.decode("42 ≥ 24");
>>> 42 >= 24

unidecode.decode("南无阿弥陀佛");
>>> Nan Wu A Mi Tuo Fo

unidecode.decode("あみだにょらい");
>>> amidaniyorai
```

### Transliterate to ISO-8859-2

```java
Unidecode unidecode = Unidecode.withCharset("ISO-8859-2");

unidecode.decode("České „uvozovky“");
>>> České "uvozovky"
```

### Initials

```java
Unidecode unidecode = Unidecode.withCharset("ASCII");

unidecode.initials("南无阿弥陀佛");
>>> NWAMTF

unidecode.initials("Κνωσός");
>>> K
```


Maven
-----

Released versions are available in The Central Repository. Just add this artifact to your project:

```xml
<dependency>
    <groupId>cz.jirutka.unidecode</groupId>
    <artifactId>unidecode</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

However if you want to use the last snapshot version, you have to add the Sonatype OSS repository:

```xml
<repository>
    <id>sonatype-snapshots</id>
    <name>Sonatype repository for deploying snapshots</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```


Other implementations
---------------------

*  [Text::Unidecode for Perl](http://search.cpan.org/~sburke/Text-Unidecode/lib/Text/Unidecode.pm) (the original implementation)
*  [Unidecode for Python](https://pypi.python.org/pypi/Unidecode)
*  [unidecoder for Ruby](https://github.com/norman/unidecoder)
*  [unidecode for JavaScript](https://github.com/FGRibreau/node-unidecode)


Credits
-------

This project is a fork of the [unidecode](https://github.com/xuender/unidecode) written by 徐晨阳
([xuender](https://github.com/xuender)). 


License
-------

This project is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
