Reset sayfası spec dosyası
==========================

Şifremi unuttum sayfasından login sayfasına geçiş
-------------------------------------------------
Tags: SifremiUnuttumSayfasindanLoginSayfasinaGecis

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum sayfasında Sign In butonuna tıklanır
* Giriş yap panelinin ekrana geldiği görülür

Şifremi unuttum-kayıtlı email ile reset
---------------------------------------
Tags: SifremiUnuttumKayitliEmailIleReset

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "qa@testinium.io" maili girilir
* Reset butonuna tıklanır
* Şifremi unuttum sayfasında "<We sent you an email to reset your password.>" mesajının ekrana geldiği görülür

Şifremi unuttum-kayıtlı ama onaylanmamış mail ile reset
-------------------------------------------------------
Tags: SifremiUnuttumKayitliAmaOnaylanmamisMailIleReset

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "sena.eroglu@testinium.com" maili girilir
* Reset butonuna tıklanır
* Onay maili gönderme sayfasında "I've sent a link to sena.eroglu@testinium.com click it and start using Testinium" mesajının ekrana geldiği görülür

Şifremi unuttum-kayıtsız mail ile reset
---------------------------------------
Tags: SifremiUnuttumKayitsizMailIleReset

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "kayitsizMail@testinium.com" maili girilir
* Reset butonuna tıklanır
/* "SOORY, WE COULDN'T fIND THAT EMAIL IN THE SYSTEM" hata mesajı alınır

Şifremi unuttum-Email boş bırakılarak reset
-------------------------------------------
Tags: SifremiUnuttumMailBosBirakilarakReset

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "" maili girilir
* Reset butonuna tıklanır
* Şifremi unuttum mail kısmında "Please fill out this field." hata mesajı alınır

Şifremi unuttum-Geçersiz mail-@siz
----------------------------------
Tags: SifremiUnuttumGecersizMailAtsiz

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "testgmail.com" maili girilir
* Reset butonuna tıklanır
* Şifremi unuttum mail kısmında "Please include an '@' in the email address. 'testgmail.com' is missing an '@'. eslesmedi ==> expected: <true> but was: <false>" hata mesajı alınır

Şifremi unuttum-Geçersiz mail-geçersiz karakter
-----------------------------------------------
Tags: SifremiUnuttumGecersizMailGecersizKarakter

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür
* Şifremi unuttum email kısmına "test<@gmail.com" maili girilir
* Reset butonuna tıklanır
* Şifremi unuttum mail kısmında "A part followed by '@' should not contain the symbol '<'." hata mesajı alınır