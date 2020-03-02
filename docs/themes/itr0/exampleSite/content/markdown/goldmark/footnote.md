---
title: "Goldmark Extension - Footnote"
date: 2020-02-29T02:10:28+09:00
---

---

## 1

```markdown
That's some text with a footnote.[^1]

[^1]: And that's the footnote.

    That's the second paragraph.
```

That's some text with a footnote.[^1]

[^1]: And that's the footnote.

    That's the second paragraph.

<!--more-->

---

## 2

```markdown
[^000]:0	[^]:
```

[^000]:0	[^]:

---

## 3

```markdown
This[^4] is[^2] text with footnotes[^3].

[^2]: Footnote two
[^3]: Footnote three
[^4]: Footnote four
```

This[^4] is[^2] text with footnotes[^3].

[^2]: Footnote two
[^3]: Footnote three
[^4]: Footnote four

---

## 4

```markdown
test![^5]

[^5]: footnote five
```

test![^5]

[^5]: footnote five

---

## 5


```markdown
test![^footnote💣^]

[^footnote💣^]: footnote six
```

test![^footnote💣^]] is called as `angle`

[^footnote💣^]: footnote💣^
