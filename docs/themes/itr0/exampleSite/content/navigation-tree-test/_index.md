---
date: "2020-02-18T20:50:31+09:00"

title: "ナビゲーションツリーのテスト"
weight: -300
---

## 関連ファイル

``

## ツリーの抽出

* 対象
  * セクション（ツリー内）
  * セクション（ツリー外）
  * ページ（サブディレクトリ形式）
  * ページ（ファイル形式）

* `nav.skip`
  * `true`
  * `false`
  * `null`


## ツリーのソート

* 対象
  * セクション（ツリー内）
  * ページ（サブディレクトリ形式）
  * ページ（ファイル形式）

* 対象
  * `weight`
    * 負数
    * 正数
    * `0`
    * `null`

Goのtemplateでは、`0`と`null`は区別なく扱われるようなので、`weight`によるソートは「負数」＞「正数」＞「`0`」=「`null`」となる。

* [\[Solved\] Content summaries not sorted by weight](https://discourse.gohugo.io/t/solved-content-summaries-not-sorted-by-weight/8590/9)
* [Zero Weight should not be considered in page sorting](https://github.com/gohugoio/hugo/issues/2673)

### Expected

* `weight`に関わらず、セクションがページよりも前に並べられること
* 同一種類（セクション、ページ）内では、`weight`の昇順に並べられること
  * ただし、`weight: 0`は`weight`指定なしと同一の順序に並べられる
* `nav.skip` が true に設定されている時は、ツリーに表示されないこと
* ツリーの中では、ディレクトリ形式のページもファイル形式のページも同等に扱われること
