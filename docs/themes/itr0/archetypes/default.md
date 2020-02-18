---
date: "{{ .Date }}"
{{ "" -}}
{{ range $taxonomy, $terms := site.Data.site.taxonomies }}
{{ $taxonomy }}:
{{- range sort $terms }}
- {{ . }}
{{- end }}
{{ end -}}
{{- "" }}
title: "{{ .Name | humanize | title}}"
---
