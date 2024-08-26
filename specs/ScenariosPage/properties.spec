Properties Sayfası Test Senaryoları
=====================

 * "qa@testinium.io" ve "Q6XpZ?6GsMbA" bilgileriyle geçerli giriş yapılır ve dashboard sayfasına ulaşılır.

Seçilen senaryoda edite tıklanması ve properties sayfasının açılması
----------------
Tags: SecilenSenaryodaEditeTiklanmasiVePropertiesSayfasininAcilmasi

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür

Properties sayfasında seçilen projenin Copy URL butonunun çalışması
-----------------
Tags: PropertiesSayfasindaSecilenProjeninCopyURLButonununCalismasi

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Copy URL butonuna tıklanır
// Butona tıklandığında "Copied" ifadesinin ekrana geldiği görülür

Properties sayfasında seçilen senaryonun Source File bölümünün kontrolü
----------------
Tags: PropertiesSayfasindaSecilenSenaryonunSourceFileBolumununKontrolü

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Spec ve cpt dosyalarının select source file başlığının altında sıralandığı görülür

Seçilen senaryonun Source File seçtikten sonra test methodunun seçilmesi kontrolü
-----------------
Tags: SecilenSenaryonunSourceFileSectiktenSonraTestMethodununSecilmesiKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Select Source File sayfasından cpt dosya seçilir
* Select Source File başlığının altında spec dosyasına tıklanır
// Select test methods başlığı altındaki tag dosyalarının sıralandığı görülür

Seçilen senaryo için File Content kısmında Save butonunun active olmama kontrolü
-----------------
Tags: SecilenSenaryoIcinFileContentKismindaSaveButonununActiveOlmamaKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* File Content tarafında herhangi değişiklik yapmadan save butonuna tıklanır
* Save butonunun active olmadığı görülür

Seçilen senaryo için File Content kısmında Save butonunun active olması kontrolü
-----------------
Tags: SecilenSenaryoIcinFileContentKismindaSaveButonununActiveOlmasiKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* File Content tarafında değişiklik yapıldıktan sonra save butonuna tıklanır
* Save butonunun active olduğu görülür

Seçilen senaryo için Set System Parameters kısmında Name kutucuğuna değer girilip ekleme kontrolü
-----------------
Tags: SecilenSenaryoIcinSetSystemParametersKismindaNameKutucugunaDegerGirilipEklemeKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Set System Parameters Name kısmına "Name" kelimesini yaz
* Add butonuna tıklanır

Seçilen senaryo için Set System Parameters kısmında hem Name kutucuğuna hemde Value  kutucuğuna değer girilip ekleme kontrolü
-----------------
Tags : SecilenSenaryoIcinNameVeValueKutucugunaDegerGirilipEklemeKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Set System Parameters Name kısmına "name" kelimesini yaz
* Add butonuna tıklanır

Properties sayfasında Apply butonunun çalışma kontrolü
-----------------
Tags : PropertiesSayfasindaApplyButonununCalısmaKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* sayfayı aşağı kaydır
* Apply butonuna tıklanır
/* "Scenario successfully updated" yazısının ekrana geldiği görülür
* Properties sayfasında kalınır


Properties sayfasında Save butonunun çalışma kontrolü
-----------------
Tags : PropertiesSayfasindaSaveButonununCalısmaKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Save butonuna tıklanır
* "Scenario successfully updated" yazısının ekrana geldiği görülür
* All Scenarios sayfasına yönlendirildiği görülür

Properties sayfasında Cancel butonunun çalışma kontrolü
-----------------
Tags : PropertiesSayfasindaCancelButonununCalısmaKontrolu

* Scenarios tabına tıklanır.
* "EnterpriseOtomasyonSena" adlı proje seçilir
* Scenarios sayfasında seçilen senaryoda edite tıklanır
* Properties sayfasının ekrana geldiği görülür
* Cancel butonuna tıklanır
* All Scenarios sayfasına yönlendirildiği görülür
