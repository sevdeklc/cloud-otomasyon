Scenarios Sayfası Create New Scenario Test Senaryoları
=====================

* "qa@testinium.io" ve "Q6XpZ?6GsMbA" bilgileriyle geçerli giriş yapılır ve dashboard sayfasına ulaşılır.

Yeni bir senaryo oluşturma kontrolü
----------------
Tags: YeniBirSenaryoOlusturmaKontrolu

* Scenarios tabına tıklanır
* Create New Scenario tabına tıklanır
* Proje seçilir
* Senaryo adı girilir
* Select Source File sayfasından dosya seçilir
* Save butonuna tıklanır

Yeni bir senaryo oluşturken proje seçmeden Save butonuna tıklama kontrolü
----------------
Tags: YeniBirSenaryoOlusturkenProjeSecmedenSaveButonunaTiklamaKontrolu

* Scenarios tabına tıklanır
* Create New Scenario tabına tıklanır
* Senaryo adı girilir
* Save butonuna tıklanır
* "This field is required." yazısının geldiği görülür.

Yeni bir senaryo oluşturken senaryo name yazmadan Save butonuna tıklama kontrolü
----------------
Tags: YeniBirSenaryoOlusturkenSenaryoNameYazmadanSaveButonunaTiklamaKontrolu

* Scenarios tabına tıklanır.
* Create New Scenario tabına tıklanır
* Proje seçilir
* Save butonuna tıklanır
* "This field is required." yazısının ekrana geldiği görülür.

Yeni bir senaryo oluştururken Select Source File'dan dosya seçmeden Save butonuna tıklama kontrolü
----------------
Tags : YeniBirSenaryoOluştururkenSelectSourceFiledanDosyaSecmedenSaveButonunaTiklamaKontrolu

* Scenarios tabına tıklanır.
* Create New Scenario tabına tıklanır
* Proje seçilir
/ Select Source File kısmının kırmızı renk olduğu görülür

Yeni bir senaryo oluştururken Select Test Methods'dan senaryo seçmeden Save butonuna tıklama kontrolü
----------------
Tags: YeniBirSenaryoOlustururkenSelectTestMethodsdanSenaryoSecmedenSaveButonunaTiklamaKontrolu

* Scenarios tabına tıklanır.
* Create New Scenario tabına tıklanır
* Proje seçilir
* Select Source File sayfasından dosya seçilir
* Save butonuna tıklanır
* "2" saniye bekle
* Select Source File sayfasından cpt dosya seçilir
* Select Test Methods kısmının kırmızı renk olduğu görülür

Yeni bir senaryo oluşturduktan sonra o senaryonun düzenlenmesi kontrolü
----------------
Tags: YeniBirSenaryoOlusturduktanSonraOSenaryonunDuzenlenmesiKontrolu

* Scenarios tabına tıklanır.
* Create New Scenario tabına tıklanır
* Proje seçilir
* Senaryo adı girilir
* Select Source File sayfasından dosya seçilir
* "2" saniye bekle
* Select Source File sayfasından cpt dosya seçilir
* Select Source File sayfasından dosya seçilir
* Select Test Methods sayfasından senaryo seçilir
* Save butonuna tıklanır

Yeni senaryo oluşturma sayfasında cancel butonunun çalışma kontrolü
----------------
Tags: YeniSenaryoOlusturmaSayfasindaCancelButonununCalısmaKontrolu

* Scenarios tabına tıklanır
* Create New Scenario tabına tıklanır
* Cancel butonuna tıklanır
* Scenarios sayfasına geldiği görülür
