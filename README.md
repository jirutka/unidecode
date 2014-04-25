Unidecode
=========
[![Build Status](https://travis-ci.org/jirutka/unidecode.svg)](https://travis-ci.org/jirutka/unidecode)
[![Coverage Status](https://img.shields.io/coveralls/jirutka/unidecode.svg)](https://coveralls.io/r/jirutka/unidecode)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.unidecode/unidecode/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.unidecode/unidecode)

Unidecode is a Java port of Perl library [Text::Unidecode] that solves transliteration of an Unicode text to US-ASCII.
This implementation is not limited only to ASCII characters, currently supports also ISO-8859-2 (aka Latin 2) and can
be easily extended to more charsets (contributions are welcome).

Please note that this is just a quick and dirty method of transliteration, **it’s not a silver bullet!** Read a detailed
[description][Text::Unidecode] of it’s limitations from the original Text::Unidecode by Sean M. Burke.


How to Use
----------

### Transliterate to ASCII

```java
Unidecode unidecode = Unidecode.toAscii();

unidecode.decode("České „uvozovky“");
>>> Ceske "uvozovky"

unidecode.decode("42 ≥ 24");
>>> 42 >= 24

unidecode.decode("em-dash — is not in ASCII");
>>> em-dash -- is not in ASCII

unidecode.decode("南无阿弥陀佛");
>>> Nan Wu A Mi Tuo Fo

unidecode.decode("あみだにょらい");
>>> amidaniyorai
```

### Transliterate to ISO-8859-2

```java
Unidecode unidecode = Unidecode.toLatin2();

unidecode.decode("České „uvozovky“");
>>> České "uvozovky"
```

### Initials

```java
Unidecode unidecode = Unidecode.toAscii();

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
    <version>1.0</version>
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

Character transliteration tables used in this project are converted (and slightly modified) from the tables provided in
the Perl library [Text::Unidecode] by Sean M. Burke and are distributed under the Perl license.


[Text::Unidecode]: http://search.cpan.org/~sburke/Text-Unidecode/lib/Text/Unidecode.pm
