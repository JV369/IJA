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

## Spuštění
 - 'ant run' spustí aplikace
 - 'ant compile' zkompiluje zdrojové soubory, vytvoří jar archív a vygeneruje dokumentaci
 - 'ant test' zkompiluje zdrojové soubory a spustí testy
 - 'ant doc' vygeneruje dokumentaci 
 - 'ant clean' vymaže zkompilované soubory, jar archív a dokumentaci

## Zdroje pro obrázky
 - BlockCook - https://www.shareicon.net/cover-cook-kitchen-cooking-727446
 - BlockWork - https://www.shareicon.net/people-furniture-computers-work-chair-working-humanpictos-766192
 - BlockSport - https://commons.wikimedia.org/wiki/File:Runner_stickman.png
 - BlockFood - https://www.shareicon.net/eating-spoon-cutlery-restaurant-eat-knive-lunch-dinner-fork-93286
