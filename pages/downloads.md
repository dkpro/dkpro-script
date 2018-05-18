---
layout: page-fullwidth
title: "Downloads"
permalink: "/downloads/"
---

{% assign stable = site.data.releases | where: "status", "stable" | first %}

## Maven

{{ site.title }} is available via the Maven infrastructure.

{% highlight xml %}
<properties>
  <dkpro.script.version>{{ stable.version }}</dkpro.script.version>
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>{{ stable.groupId }}</groupId>
      <artifactId>{{ stable.artifactId }}</artifactId>
      <version>${dkpro.script.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>{{ stable.groupId }}</groupId>
    <artifactId>dkpro-script-groovy</artifactId>
  </dependency>
</dependencies>
{% endhighlight xml %}

A full list of artifacts is available from [Maven Central][1]! 
  
## Sources

Get the sources from [GitHub](https://github.com/dkpro/dkpro-script/releases/tag/dkpro-script-{{ stable.version }}).

[1]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22{{ stable.groupId }}%22%20AND%20v%3A%22{{ stable.version }}%22


