/* 
 * Ad Soyad: Fırat Seçkin
 * Öğrenci No: 250541042
 * Proje Adı: restoran sipariş hesaplama
 * Açıklama : Sipariş içeriğine göre en uygun indirim kombinasyonlarını belirleyip nihai tutarı hesaplayan kapsamlı restoran otomasyonudur.
 * Tarih: 27.11.2025
 */

import java.util.Scanner;


/**
 * Proje 3: Akıllı Restoran Sipariş Sistemi
 * Sipariş anında en uygun fiyatı hesaplayan; menü kombinasyonlarını ve özel saatleri (Happy Hour)
 * algılayıp müşteriye maksimum indirimi sunan akıllı hesap yönetim sistemi.
 */
public class RestoranSistemi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("=== RESTORAN SİPARİŞ SİSTEMİ ===");

        // --- Girdilerin Alınması ---
        
        // 1. Ana Yemek
        System.out.println("Ana Yemekler: 1-Tavuk(85), 2-Kebap(120), 3-Levrek(110), 4-Mantı(65), 0-Yok");
        System.out.print("Seçiminiz (0-4): ");
        int anaYemekSecim = scanner.nextInt();

        // 2. Başlangıç
        System.out.println("Başlangıçlar: 1-Çorba(25), 2-Humus(45), 3-Börek(55), 0-Yok");
        System.out.print("Seçiminiz (0-3): ");
        int baslangicSecim = scanner.nextInt();

        // 3. İçecek
        System.out.println("İçecekler: 1-Kola(15), 2-Ayran(12), 3-Meyve Suyu(35), 4-Limonata(25), 0-Yok");
        System.out.print("Seçiminiz (0-4): ");
        int icecekSecim = scanner.nextInt();

        // 4. Tatlı
        System.out.println("Tatlılar: 1-Künefe(65), 2-Baklava(55), 3-Sütlaç(35), 0-Yok");
        System.out.print("Seçiminiz (0-4): ");
        int tatliSecim = scanner.nextInt();

        // 5. Diğer Bilgiler
        System.out.print("Saat kaç? (8-23): ");
        int saat = scanner.nextInt();

        System.out.print("Öğrenci misiniz? (E/H): ");
        String ogrenciCevap = scanner.next();
        boolean ogrenciMi = ogrenciCevap.equalsIgnoreCase("E");

        System.out.print("Hangi gün? (1-7): ");
        int gun = scanner.nextInt();

        scanner.close();

        // --- Fiyat Hesaplamaları ---

        // 1. Ürün Fiyatlarını Getir (Uzman Metotlar)
        double anaYemekFiyati = getMainDishPrice(anaYemekSecim);
        double baslangicFiyati = getAppetizerPrice(baslangicSecim);
        double tatliFiyati = getDessertPrice(tatliSecim);
        
        // İçecek fiyatı ve Happy Hour İndirimi (Özel Durum)
        // PDF'te Happy Hour sadece içeceklerde %20 indirim diyor.
        double icecekFiyati = getDrinkPrice(icecekSecim);
        if (isHappyHour(saat) && icecekFiyati > 0) {
            System.out.println(">> Happy Hour yakaladınız! İçecekte %20 indirim.");
            icecekFiyati = icecekFiyati * 0.80; // %20 düşürüldü
        }

        // 2. Ara Toplam
        double araToplam = anaYemekFiyati + baslangicFiyati + icecekFiyati + tatliFiyati;

        // 3. Combo Durumu Kontrolü
        // (Ana Yemek, İçecek ve Tatlı varsa Combo sayılır)
        boolean comboVar = isComboOrder(anaYemekFiyati > 0, icecekFiyati > 0, tatliFiyati > 0);

        // 4. Genel İndirimleri Hesapla (Uzman Metot)
        double genelIndirimTutari = calculateDiscount(araToplam, comboVar, ogrenciMi, gun);

        // 5. Son Fiyat ve Bahşiş
        double odenecekTutar = araToplam - genelIndirimTutari;
        double bahsis = calculateServiceTip(odenecekTutar);

        // --- Fiş Yazdırma ---
        System.out.println("\n--------------------------------");
        System.out.println("        HESAP FİŞİ");
        System.out.println("--------------------------------");
        if(anaYemekFiyati > 0) System.out.printf("Ana Yemek:       %6.2f TL\n", anaYemekFiyati);
        if(baslangicFiyati > 0)System.out.printf("Başlangıç:       %6.2f TL\n", baslangicFiyati);
        if(icecekFiyati > 0)   System.out.printf("İçecek:          %6.2f TL\n", icecekFiyati);
        if(tatliFiyati > 0)    System.out.printf("Tatlı:           %6.2f TL\n", tatliFiyati);
        
        System.out.println("--------------------------------");
        System.out.printf("Ara Toplam:      %6.2f TL\n", araToplam);
        System.out.printf("İndirimler:     -%6.2f TL\n", genelIndirimTutari);
        System.out.println("--------------------------------");
        System.out.printf("ÖDENECEK TUTAR:  %6.2f TL\n", odenecekTutar);
        System.out.println("================================");
        System.out.printf("Önerilen Bahşiş: %6.2f TL\n", bahsis);
    }

    // --- UZMAN METOTLAR ---

    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0; // Izgara Tavuk
            case 2: return 120.0; // Adana Kebap
            case 3: return 110.0; // Levrek
            case 4: return 65.0;  // Mantı
            default: return 0.0;
        }
    }

    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0; // Çorba
            case 2: return 45.0; // Humus
            case 3: return 55.0; // Sigara Böreği
            default: return 0.0;
        }
    }

    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0; // Kola
            case 2: return 12.0; // Ayran
            case 3: return 35.0; // Meyve Suyu
            case 4: return 25.0; // Limonata
            default: return 0.0;
        }
    }

    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0; // Künefe
            case 2: return 55.0; // Baklava
            case 3: return 35.0; // Sütlaç
            default: return 0.0;
        }
    }

    // Combo Kontrolü: Ana Yemek, İçecek ve Tatlı hepsi varsa true döner
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return (anaVar && icecekVar && tatliVar);
    }

    // Happy Hour Kontrolü: 14:00 - 17:00 arası
    public static boolean isHappyHour(int saat) {
        return (saat >= 14 && saat <= 17);
    }

    // Genel İndirim Hesaplayıcı (Combo, 200 TL üzeri, Öğrenci)
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int gun) {
        double toplamIndirim = 0.0;

        // 1. Combo İndirimi (%15)
        if (combo) {
            toplamIndirim += tutar * 0.15;
        }

        // 2. 200 TL Üzeri İndirimi (%10)
        if (tutar > 200) {
            toplamIndirim += tutar * 0.10;
        }

        // 3. Öğrenci İndirimi (Hafta içi ise %10)
        // Hafta içi: Gün 1,2,3,4,5
        boolean haftaIci = (gun >= 1 && gun <= 5);
        if (ogrenci && haftaIci) {
            toplamIndirim += tutar * 0.10;
        }

        return toplamIndirim;
    }

    // Bahşiş Hesaplayıcı (%10)
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
}
