Register sayfası spec dosyası
==========================

Kayıt ol-Kayıt ol sayfasından login sayfasına geçiş
---------------------------------------------------
Tags: KayitOlKayitOlSayfasindanLoginSayfasınaGecis

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında giriş yap butonuna tıklanır
* Giriş yap panelinin ekrana geldiği görülür

Kayıt ol-Hesap adı boş bırakılır
------------------------------------
Tags: KayitOlHesapAdiGecersizKarakter

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "" ismi girilir
* Kayıt ol sayfasında kayıt ol butonuna tıklanır
* Hesap ismi kısmında "Please fill out this field." mesajı alınır

Kayıt ol-boş mail adresi
------------------------
Tags: KayitOlBosMailAdresi

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "test" ismi girilir
* Kayıt ol sayfasında email kısmına "" maili girilir
* Kayıt ol sayfasında kayıt ol butonuna tıklanır
* Mail kısmında "Please fill out this field." mesajı alınır

Kayıt ol-geçersiz mail-@siz mail girişi
---------------------------------------
Tags: KayitOlGecersizMAilAtsizMailGirisi

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "test" ismi girilir
* Kayıt ol sayfasında email kısmına "testgmail.com" maili girilir
* Kayıt ol sayfasında kayıt ol butonuna tıklanır
* Mail kısmında "Please include an '@' in the email address. 'testgmail.com' is missing an '@'." mesajı alınır

Kayıt ol-geçersiz mail-geçersiz karakter
----------------------------------------
Tags: KayitOlGecersizMailGecersizKarakter

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "test" ismi girilir
* Kayıt ol sayfasında email kısmına "test<@gmail.com" maili girilir
* Kayıt ol sayfasında kayıt ol butonuna tıklanır
* Mail kısmında "A part followed by '@' should not contain the symbol '<'." mesajı alınır

Kayıt ol-Boş şifre
------------------
Tags: KayitOlBosSfire

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "test" ismi girilir
* Kayıt ol sayfasında email kısmına "test<@gmail.com" maili girilir
* Kayıt ol sayfasında şifre kısmına "" şifresi girilir
* Kayıt ol sayfasında kayıt ol butonuna tıklanır
* Kayıt ol sayfası şifre kısmında "Please fill out this field." mesajı alınır

Kayıt ol-8 karakterden az şifre
-------------------------------
Tags: KayitOl8KarakterdenAzSifre

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür
* Kayıt ol sayfasında hesap ismi kısmına "test" ismi girilir
* Kayıt ol sayfasında email kısmına "test<@gmail.com" maili girilir
* Kayıt ol sayfasında şifre kısmına "Aa12345" şifresi girilir
* Kayıt ol sayfası şifre kısmında "Your password must be at least 8 characters with one uppercase one lowercase and one number." hata mesajı alınır

