/* 
 * Ad Soyad: Fırat Seçkin
 * Öğrenci No: 250541042
 * Proje Adı: Sinema Bileti Fiyatlandırma Sistemi
 * Açıklama : Farklı gün, saat, yaş, meslek ve film formatlarına göre özel indirimler ve 
 ek ücretler uygulayarak nihai sinema bileti fiyatını hesaplayan detaylı bir bilet satış sistemidir.
 * Tarih: 27.11.2025
 */

import java.util.Scanner;


/**
 * Proje 2: Sinema Bileti Fiyatlandırma Sistemi
 * Kullanıcının girdilerine (gün, saat, yaş, meslek, film türü) göre
 * bilet fiyatını hesaplayan sistem.
 */
public class SinemaBileti {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        // --- Girdilerin Alınması ---
        System.out.print("Gün (1=Pzt, 2=Sal, ..., 7=Paz): ");
        int gun = scanner.nextInt();

        System.out.print("Saat (8-23 arası): ");
        int saat = scanner.nextInt();

        System.out.print("Yaşınız: ");
        int yas = scanner.nextInt();

        System.out.print("Meslek (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
        int meslek = scanner.nextInt();

        System.out.print("Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
        int filmTuru = scanner.nextInt();

        scanner.close();

        // --- Hesaplama İşlemleri (Metot Çağrıları) ---
        
        // 1. Temel Fiyat Hesabı
        double temelFiyat = calculateBasePrice(gun, saat);

        // 2. İndirim Oranı Hesabı
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double indirimTutari = temelFiyat * indirimOrani;

        // 3. Format Ekstra Ücret Hesabı
        double formatEkstrasi = getFormatExtra(filmTuru);

        // 4. Nihai Toplam Fiyat Hesabı
        double sonFiyat = calculateFinalPrice(temelFiyat, indirimTutari, formatEkstrasi);

        // --- Rapor Oluşturma ---
        generateTicketInfo(gun, saat, yas, meslek, filmTuru, 
                           temelFiyat, indirimTutari, formatEkstrasi, sonFiyat);
    }

    // --- UZMAN METOTLAR ---

    /**
     * Günün hafta sonu (Cumartesi-Pazar) olup olmadığını kontrol eder.
     */
    public static boolean isWeekend(int gun) {
        return (gun == 6 || gun == 7);
    }

    /**
     * Saatin matine (12:00 öncesi) olup olmadığını kontrol eder.
     */
    public static boolean isMatinee(int saat) {
        return (saat < 12);
    }

    /**
     * Gün ve saate göre temel bilet fiyatını belirler.
     */
    public static double calculateBasePrice(int gun, int saat) {
        double fiyat;
        boolean haftaSonu = isWeekend(gun);
        boolean matine = isMatinee(saat);

        if (haftaSonu) {
            fiyat = matine ? 55.0 : 85.0;
        } else {
            fiyat = matine ? 45.0 : 65.0;
        }
        return fiyat;
    }

    /**
     * Yaş, meslek ve güne göre uygulanacak en yüksek indirim oranını hesaplar.
     */
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double oran = 0.0;

        // Yaş indirimleri önceliklidir
        if (yas >= 65) {
            oran = 0.30;
        } else if (yas <= 12) {
            oran = 0.25;
        } else {
            // Meslek indirimleri (switch-case)
            switch (meslek) {
                case 1: // Öğrenci
                    oran = isWeekend(gun) ? 0.15 : 0.20;
                    break;
                case 2: // Öğretmen (Sadece Çarşamba)
                    if (gun == 3) {
                        oran = 0.35;
                    }
                    break;
                default:
                    oran = 0.0;
            }
        }
        return oran;
    }

    /**
     * Seçilen film türüne göre ekstra ücreti döndürür.
     */
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 2: return 25.0; // 3D
            case 3: return 35.0; // IMAX
            case 4: return 50.0; // 4DX
            default: return 0.0; // 2D veya diğerleri
        }
    }

    /**
     * İndirimleri düşüp ekstraları ekleyerek son fiyatı hesaplar.
     */
    public static double calculateFinalPrice(double temel, double indirim, double ekstra) {
        double toplam = temel - indirim + ekstra;
        return (toplam < 0) ? 0 : toplam;
    }

    /**
     * Bilet detaylarını ve fiyatlandırmayı ekrana yazdırır.
     */
    public static void generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru,
                                          double temel, double indirim, double ekstra, double toplam) {
        
        System.out.println("\n=== SİNEMA BİLETİ DETAYLARI ===");
        
        // Gün isimlendirme
        String gunAdi;
        switch(gun) {
            case 1: gunAdi="Pazartesi"; break; case 2: gunAdi="Salı"; break;
            case 3: gunAdi="Çarşamba"; break; case 4: gunAdi="Perşembe"; break;
            case 5: gunAdi="Cuma"; break; case 6: gunAdi="Cumartesi"; break;
            case 7: gunAdi="Pazar"; break; default: gunAdi="Hatalı Gün";
        }
        
        String formatAdi;
        switch(filmTuru) {
            case 2: formatAdi="3D"; break; case 3: formatAdi="IMAX"; break;
            case 4: formatAdi="4DX"; break; default: formatAdi="Standard 2D";
        }

        System.out.println("Zaman: " + gunAdi + " " + saat + ":00");
        System.out.println("Format: " + formatAdi);
        System.out.println("--------------------------------");
        System.out.printf("Temel Fiyat:     %6.2f TL\n", temel);
        if(indirim > 0) System.out.printf("İndirim:        -%6.2f TL\n", indirim);
        if(ekstra > 0)  System.out.printf("Format Farkı:   +%6.2f TL\n", ekstra);
        System.out.println("--------------------------------");
        System.out.printf("TOPLAM TUTAR:    %6.2f TL\n", toplam);
        System.out.println("================================");
    }
}
