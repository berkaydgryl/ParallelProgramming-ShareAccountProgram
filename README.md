# Shared Account - Parallel Programming

SWE449 Parallel Programming dersi projesidir. Java thread mekanizmalarini kullanarak ortak bir banka hesabi uzerinde es zamanli para yatirma ve cekme islemlerini simule eder.

## Proje Amaci

Birden fazla thread'in ayni kaynak (banka hesabi) uzerinde guvenli sekilde islem yapmasini `synchronized`, `wait()` ve `notifyAll()` mekanizmalari ile saglamak.

## Yapi

| Sinif | Aciklama |
|-------|----------|
| `SharedAccount` | Ortak banka hesabi. `synchronized` metotlarla thread-safe deposit/withdraw islemleri yapar. Bakiye yetersizse `wait()` ile bekler, para yatinca `notifyAll()` ile bekleyenleri uyarir. |
| `Depositor` | Hesaba belirli miktarda para yatiran thread. |
| `Withdrawer` | Hesaptan para ceken thread. Bakiye yetersizse yeterli bakiye olana kadar bekler. |
| `Main` | 2 depositor ve 3 withdrawer thread olusturur, baslatir ve sonucta beklenen bakiye ile gercek bakiyeyi karsilastirir. |

## Calistirma

```bash
cd src
javac *.java
java Main
```

## Senaryo

- Baslangic bakiyesi: **100 TL**
- 2 Depositor thread: her biri 3 islemde 150 TL yatirir (toplam +900 TL)
- 3 Withdrawer thread: her biri 2 islemde 120 TL ceker (toplam -720 TL)
- Beklenen son bakiye: **280 TL**

Program sonunda gercek bakiye ile beklenen bakiyeyi karsilastirarak senkronizasyonun dogru calisip calismadigini dogrular.
