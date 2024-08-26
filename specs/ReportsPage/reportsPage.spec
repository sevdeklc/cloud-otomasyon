Report sayfası spec dosyası
=====================

* Geçerli login yapılır ve hesaba giriş yapılır

Test Executions Auto sayfası element kontrolü
---------------------------------------------
Tags:  TestExecutionsAutoSayfaElementKontrolu

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* Test Executions Auto sayfa kontrollerini yap

Proje filterdan bir proje seç ve listedeki bütün planların bu projeye ait olduğunu doğrula
------------------------------------------------------------------------------------------
Tags: ProjeFilterFonksiyonaliteKontrolu

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* Proje filtreleme kısmından "EnterpriseOtomasyonEnes" adlı projeyi seç
* Listedeki bütün planların "enterpriseotomasyonenes" adlı projeye ait olduğunu doğrula

Plan Filterdan bir plan seç ve listedeki bütün planların bu plana ait olduğunu doğrula
--------------------------------------------------------------------------------------
Tags: PlanFilterFonksiyonaliteKontrolu

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* Plan filtreleme kısmından "dailyRunPl" adlı planı seç
* Listedeki bütün planların "dailyrunpl" adlı plana ait olduğunu doğrula

Run date filterda tarih aralığı ayarla ve o tarihteki planların listelendiğini gör
----------------------------------------------------------------------------------
Tags: RunDateFilterFonksiyonaliteKontrolu

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* RunDate Filterda from "03/10/2022" to "03/10/2022" tarih aralığını seç
* Listedeki bütün planların "03/10/2022" tarihli planlar olduğunu doğrula

Show only failed test fonksiyonalite kontrolü
---------------------------------------------
Tags: ShowOnlyFailedTestsFonksiyonaliteKontrolu

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* Show only Failed tests checkboxını işaretle
* Sadece fail testlerin gösterildiğini gör

Herhangi bir planın detailsine git ve gittiğini doğrula
-------------------------------------------------------
Tags: TextExecutionAutoDetailsControl

* Reports tabına tıkla
* Test Executions Auto tabına tıkla
* Proje filtreleme kısmından "EnterpriseOtomasyonEnes" adlı projeyi seç
* "testt" Adlı plan için details butonuna tıkla
* Şu anki url "https://clouddev.testinium.io/report/detail" içeriyor mu

