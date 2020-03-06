---
title: "リンクのテスト"
date: 2020-02-29T03:22:28+09:00
---

## 外部リンク

### Markdown書式で書くと、外部リンクは別タブで開くようになる（`layouts/_default/_markup/render-link.html`が効く）

```markdown
[Google](https://google.com)
```

[Google](https://google.com)

### URLだけで書くと、同じタブで開く（`layouts/_default/_markup/render-link.html`が効かない）

```markdown
https://google.com
```

https://google.com

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

{{< relref "/markdown/emoji-cheat-sheet.md" >}}
