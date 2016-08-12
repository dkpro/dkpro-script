---
layout: page-fullwidth
title: "Documentation"
permalink: "/documentation/"
---

## User Documentation

{% assign stable = (site.data.releases | where:"status", "stable" | first) %}
### DKPro Script {{ stable.version }}
_latest release_

* [Release Notes]({{site.sourceurl}}/releases/tag/dkpro-script-{{stable.version}}){% for link in stable.doclinks %}
* [{{ link.title }}]({{site.url}}/releases/{{stable.version}}/{{ link.url }}){% endfor %}

{% for unstable in site.data.releases reversed %}
{% if unstable.status == 'unstable' %}
### DKPro Script {{ unstable.version }}
_upcoming release - links may be temporarily broken while a build is in progress_

{% for link in unstable.doclinks %}
* [{{ link.title }}]({{ link.url }}){% endfor %}
{% endif %}
{% endfor %}
