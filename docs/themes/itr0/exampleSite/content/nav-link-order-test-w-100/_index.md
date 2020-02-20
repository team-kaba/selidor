---
date: "2020-02-18T20:50:44+09:00"

title: "セクションリンクのテスト（負数）"

weight: -100
---


## 関連ファイル

`funcs/menu-sections`, `atoms/page-link`, `molecules/menu-sections-navlink`

## セクションリンクに表示するセクションの抽出

* 対象
  * `nav.skip`

* データパターン
  * `true`
  * `false`
  * `null`

## セクションリンクのソート

* 対象
  * `weight`

* データパターン（Goのtemplateでは、`0`と`null`が同一視されるため、下記の順序にソートされる）
  * 負数
  * 正数
  * `0`
  * `null`
