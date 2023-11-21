# TASK 1 // Rasterizace úsečky, tečkované čáry a n-úhelníku
- [x] Vytvořte program pro kreslení úsečky zadané dvěma libovolnými koncovými body [x1,y1] a [x2,y2].
- [x] Koncové body zadávejte interaktivně pomocí tzv. pružné čáry. Stisknutím tlačítka myši zadáte první vrchol, při tažení myší se bude vykreslovat aktuální úsečka společně s již vykreslenou scénou a při uvolnění tlačítka se potvrdí koncový vrchol.
- [x] K implementaci použijte rozhraní a třídy definované na cvičeních. Třídy případně upravte nebo doplňte o potřebné metody. Návrhy třídy Point, Line a LineRasterizer naleznete v modulu task1 (viz Oliva-obsah-ukázky a návody).
- [x] Vytvořte třídu FilledLineRasterizer dědící z abstraktní třídy LineRasterizer a správně implementující libovolný algoritmus pro rasterizaci úsečky. Do komentáře zapište, o jaký algoritmus se jedná, jeho výhody a nevýhody, případně jiná jeho specifika.
- [x] Prozkoumejte třídy Canvas, CanvasKey, CanvasPaint, …, CanvasRasterBufferedImage (v BB ukázky a návody), řešící hlavní aplikační třídu a UI. Soustřeďte se na interface Raster a třídu RasterBufferedImage a použijte ji pro vaše řešení.
- [x] Třídy zakomponujte do aplikace, která bude interaktivně zadávat body tvořící vrcholy n-úhelníku. Využijte ukázku CanvasRasterBufferedImage.
- [x] Zkušenější studenti mohou použít aplikační logiku z modulu task2, primárně určeného pro druhou úlohu. Tyto ukázky aplikačního řešení nejsou dogma, můžete je modifikovat nebo navrhnout vlastní. Snažte se o rozdělení aplikace na smysluplné třídy a zachovejte koncept rozhraní a tříd Raster, Point, Line a LineRasterizer.
- [x] Vytvořte si vhodnou třídu Polygon pro ukládání vrcholů. Je vhodnější ukládat vrcholy ne hrany z důvodu zajištění uzavřenosti útvaru, viz druhá úloha.
- [x] Vrcholy zadávejte interaktivně: stisknutím tlačítka myši vytvořte nový bod spojený s dvěma již vytvořenými vrcholy, např. s prvním a posledním. Tažením kreslete pružnou čáru k oběma vrcholům a uvolněním tlačítka přidejte bod do seznamu vrcholů n-úhelníku.
- [x] Přidejte řežim (po stisku klávesy Shift) pro kreslení vodorovných, svislých a úhlopříčných úseček. První bod je zadán stiskem, druhý určen na základě polohy při tažení myši, tak aby se vybral nejbližší koncový bod z možné vodorovné, svislé nebo úhlopříčné úsečky.
- [x] Implementujte algoritmus vykreslující tečkovanou úsečku. Zakomponujte jej do aplikace, například při editaci se bude vykreslovat tečkovaná čára a překreslení celého útvaru bude plnou čarou.
- [x] Implementujte funkci na klávesu C pro mazání plátna a všech datových struktur.
- [0.5/1] *Bonus: Upravte program tak, aby bylo možné souřadnice jednotlivých vrcholů editovat myší. Například při stisku pravého tlačítka myši naleznete nejbližší vrchol a tažením mu nastavíte novou souřadnici. Podobně i přidávání nových vrcholů bude nový vrchol umístěn do nejbližší hrany.*
- [0/1] *Bonus2: u tečkované úsečky modifikujte algoritmus tak, aby tečky byly vzdáleny stejně daleko, bez ohledu na strmost čáry, vzdálenost teček je vhodné parametrizovat. První a poslední mezeru mezi tečkami upravte tak, aby byly stejné, ostatní podle parametru. Zkuste navrhnout řešení pro různé typy čar, čárkovanou, tečkovanou, čerchovanou, …*
- [1/1] *Bonus3: Vytvořte si GITový repozitář a pravidelně commitujte postup.*

---

- Hodnoceno: 16/20 
