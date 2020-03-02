---
title: "Goldmark Extension - Linkify"
date: 2020-02-29T02:10:28+09:00
---

---

## 1

```markdown
www.commonmark.org
```

www.commonmark.org

<!--more-->

---

## 2

```markdown
Visit www.commonmark.org/help for more information.
```

Visit www.commonmark.org/help for more information.

---

## 3

```markdown
www.google.com/search?q=Markup+(business)

www.google.com/search?q=Markup+(business)))

(www.google.com/search?q=Markup+(business))

(www.google.com/search?q=Markup+(business)
```

www.google.com/search?q=Markup+(business)

www.google.com/search?q=Markup+(business)))

(www.google.com/search?q=Markup+(business))

(www.google.com/search?q=Markup+(business)

---

## 4

```markdown
www.google.com/search?q=(business))+ok
```

www.google.com/search?q=(business))+ok

---

## 5

```markdown
www.google.com/search?q=commonmark&hl=en

www.google.com/search?q=commonmark&hl;
```

www.google.com/search?q=commonmark&hl=en

www.google.com/search?q=commonmark&hl;

---

## 6

```markdown
www.commonmark.org/he<lp
```

www.commonmark.org/he<lp

---

## 7

```markdown
http://commonmark.org

(Visit https://encrypted.google.com/search?q=Markup+(business))

Anonymous FTP is available at ftp://foo.bar.baz.
```

http://commonmark.org

(Visit https://encrypted.google.com/search?q=Markup+(business))

Anonymous FTP is available at ftp://foo.bar.baz.

---

## 8

```markdown
foo@bar.baz
```

foo@bar.baz

---

## 9

```markdown
hello@mail+xyz.example isn't valid, but hello+xyz@mail.example is.
```

hello@mail+xyz.example isn't valid, but hello+xyz@mail.example is.

---

## 10

```markdown
a.b-c_d@a.b

a.b-c_d@a.b.

a.b-c_d@a.b-

a.b-c_d@a.b_
```

a.b-c_d@a.b

a.b-c_d@a.b.

a.b-c_d@a.b-

a.b-c_d@a.b_

---

## 11

```markdown
https://github.com#sun,mon
```

https://github.com#sun,mon

---

## 12

```markdown
https://github.com/sunday's
```

https://github.com/sunday's

---

## 13

```markdown
https://github.com?q=stars:>1
```

https://github.com?q=stars:>1

---

## 14

```markdown
[https://google.com](https://google.com)
```

[https://google.com](https://google.com)

---

## 15

```markdown
This is a `git@github.com:vim/vim`
```

This is a `git@github.com:vim/vim`

---

## 16

```markdown
https://nic.college
```

https://nic.college

---

## 17

```markdown
footnote.md
```

footnote.md

---

## 18

```markdown
[footnote.md]({{< relref "footnote.md" >}})
```

[footnote.md]({{< relref "footnote.md" >}})

---

## 19

```markdown
[/markdown/goldmark/footnote.md]({{< relref "/markdown/goldmark/footnote.md" >}})
```

[/markdown/goldmark/footnote.md]({{< relref "/markdown/goldmark/footnote.md" >}})

## 20

```markdown
<{{< relref "/markdown/goldmark/footnote.md" >}}>
```

<{{< relref "/markdown/goldmark/footnote.md" >}}>
