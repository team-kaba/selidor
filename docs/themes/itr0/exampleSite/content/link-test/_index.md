---
title: "リンクのテスト"
date: 2020-02-29T03:22:28+09:00
weight: -200
---

## 外部リンク

### Markdown書式で書くと、外部リンクは別タブで開くようになる（`layouts/_default/_markup/render-link.html`が効く）

```markdown
[`relref`](https://gohugo.io/content-management/shortcodes/#ref-and-relref)
```

[`relref`](https://gohugo.io/content-management/shortcodes/#ref-and-relref)

### URLだけで書くと、同じタブで開く（`layouts/_default/_markup/render-link.html`が効かない）

```markdown
https://gohugo.io/content-management/shortcodes/#ref-and-relref
```

https://gohugo.io/content-management/shortcodes/#ref-and-relref

## 内部リンク

どの書式で書いても、同じタブで開く。

### Markdown書式

```markdown
[Emoji Cheat Sheet]({{< relref "/markdown/emoji-cheat-sheet.md" >}})
```

[Emoji Cheat Sheet]({{< relref "/markdown/emoji-cheat-sheet.md" >}})

### Custom Shortcode

```hugo
{{< echo "{{< page \"/markdown/emoji-cheat-sheet.md\" >}}" >}}
```

{{< page "/markdown/emoji-cheat-sheet.md" >}}
