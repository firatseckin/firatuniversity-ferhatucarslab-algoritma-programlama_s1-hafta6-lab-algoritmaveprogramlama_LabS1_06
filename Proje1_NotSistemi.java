/* 
 * Ad Soyad: Fırat Seçkin
 * Öğrenci No: 250541042
 * Proje Adı: Öğrenci Not Değerlendirme Sistemi
 * Açıklama : Kullanıcıdan alınan vize, final ve ödev notlarına göre ağırlıklı ortalamayı, 
 harf notunu ve başarı durumunu hesaplayıp raporlayan kapsamlı bir not değerlendirme sistemidir.
 * Tarih: 27.11.2025
 */

import java.util.Scanner;

public class Proje1_NotSistemi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Öğrenci Not Değerlendirme Sistemi ---");

        System.out.print("Vize Notunu Giriniz: ");
        int vize = scanner.nextInt();

        System.out.print("Final Notunu Giriniz: ");
        int finalNotu = scanner.nextInt();

        System.out.print("Ödev Notunu Giriniz: ");
        int odev = scanner.nextInt();

        double ortalama = calculateAverage(vize, finalNotu, odev);
        boolean gectiMi = isPassingGrade(ortalama);
        char harfNotu = getLetterGrade(ortalama);
        boolean onurListesi = isHonorList(ortalama, vize, finalNotu, odev);
        boolean butunlemeHakki = hasRetakeRight(ortalama);

        System.out.println("\n--- Değerlendirme Raporu ---");
        System.out.printf("Ortalama: %.2f\n", ortalama);
        System.out.println("Harf Notu: " + harfNotu);
        
        if (gectiMi) {
            System.out.println("Durum: Dersi GEÇTİ.");
            if (onurListesi) {
                System.out.println("Tebrikler! Onur Listesine girdiniz.");
            }
        } else {
            System.out.println("Durum: Dersi GEÇEMEDİ.");
            if (butunlemeHakki) {
                System.out.println("Bilgi: Bütünleme sınavına girme hakkınız var.");
            } else {
                System.out.println("Bilgi: Bütünleme hakkı yok, ders tekrarı gerekiyor.");
            }
        }
        
        scanner.close();
    }

    public static double calculateAverage(int vize, int finalNotu, int odev) {
        return (vize * 0.30) + (finalNotu * 0.40) + (odev * 0.30);
    }
  
    public static boolean isPassingGrade(double ortalama) {
        return ortalama >= 50;
    }

    public static char getLetterGrade(double ortalama) {
        if (ortalama >= 90) return 'A';
        else if (ortalama >= 80) return 'B';
        else if (ortalama >= 70) return 'C';
        else if (ortalama >= 60) return 'D';
        else if (ortalama >= 50) return 'E';
        else return 'F';
    }

    public static boolean isHonorList(double ortalama, int vize, int finalNotu, int odev) {
        return (ortalama >= 85) && (vize >= 70) && (finalNotu >= 70) && (odev >= 70);
    }

    public static boolean hasRetakeRight(double ortalama) {
        return (ortalama >= 40) && (ortalama < 50);
    }
}
