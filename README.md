# IJA
## Autoři:
- Jan Vávra (xvavra20)
- Aleš Postulka (xpostu03)

## Adresářová struktura
    src/          - (adres.) zdrojové soubory (hierarchie balíků)
    examples/     - (adres.) připravené datové soubory
    build/        - (adres.) přeložené class soubory
    doc/          - (adres.) vygenerovaná programová dokumentace
    dest-client/  - (adres.) umístění výsledného jar archivu (+ dalších potřebných) po kompilaci klientské aplikace, 
                         příp. samostatné aplikace (pokud není řešena varianta klient-server), 
                         tento adresář bude představovat adresář spustitelné aplikace
    dest-server/  - (adres.) umístění výsledného jar archivu (+ dalších potřebných) po kompilaci serverové aplikace (pokud je 
                         vyžadována při řešení klient-server varianty),
                         tento adresář bude představovat adresář spustitelné aplikace
    lib/          - (adres.) externí soubory a knihovny (balíky třetích stran, obrázky apod.), které vaše aplikace využívá
    readme.txt    - (soubor) základní popis projektu (název, členové týmu, ...)
    rozdeleni.txt - (soubor) soubor obsahuje rozdělení bodů mezi členy týmu (pokud tento soubor neexistuje, předpokládá se 
                         rovnoměrné rozdělení, vizte hodnocení projektu)
    build.xml     - (soubor) build file pro aplikaci ant
