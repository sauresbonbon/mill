# <p style= "text-align: center; font-size: 50px"> Mühle </p>

## Kurzbeschreibung

Das Spiel Mühle ist ein Spiel, das von zwei Spielern gespielt wird.

### Aufbau

Das Spielbrett besteht aus einem Gitter mit 24 Punkten und jeder Spieler hat 9 Steine in einer 
eigenen Farbe(üblicherweise Weiß und Schwarz).

### Spielphasen

Das Spiel läuft durch 3 Phasen, der Setzphase, Zugphase und der Sprungphase.
1. Setzphase
   * Die Spieler setzen nacheinander ihre Steine auf ein freies Feld auf dem Spielbrett.
2. Zugphase
   * Sobald alle Spielsteine gesetzt wurden beginnt die Zugphase.
   * Abwechselnd dürfen die Spieler einen ihrer Spielsteine auf ein benachbartes Feld setzen, sollte dieses frei sein.
3. Sprungphase
   * Sobald ein Spieler nur noch 3 Spielsteine auf dem Spielbrett liegen hat, wechselt dieser Spieler
     in die Sprungphase.
   * In dieser Phase ist der Spieler bei dem bewegen seiner Spielsteine nicht mehr an die benachbarten Felder gebunden, 
     sondern darf sich frei auf dem Spielbrett bewegen.

### Mühlen

Sobald ein Spieler drei seiner Spielsteine in einer Reihe liegen hat, hat er eine Mühle gebildet.
Nun darf er einen Spielstein des Gegners entfernen, solange dieser nicht Teil einer eigenen Mühle ist.  

### Sieg

Es gibt zwei Gewinnmöglichkeiten:
1. Wenn der Gegner nur noch zwei Spielsteine hat.
2. Wenn der Gegner keinen legalen Zug mehr machen kann.

## Verwendete Bibliotheken

#### [Processing:](https://processing.org/) 

Eine Java-basierte Kreativ-Coding-Umgebung, die für die Erstellung der grafischen
Darstellung im Spiel verwendet wird.

#### [ControlP5:](https://www.sojamo.de/libraries/controlP5/)

ControlP5 ist eine Java-Bibliothek für Processing, die die Erstellung von grafischen Benutzeroberflächen
(GUIs) erleichtert. Sie bietet vorgefertigte Steuerelemente wie Buttons und Slider sowie 
Funktionen zur Anpassung von Position, Größe und Ereignisbehandlung.

#### Quellen:

Die Titelbilder "windmillDay.png" und "windmillNight.png" wurden erstellt mit: https://www.freepik.com/ai

## Notwendige Schritte zum Starten von Mühle
    
1. Öffne deine bevorzugte Java-Entwicklungsumgebung(z.B. IntelliJ IDEA)
2. Stelle sicher, dass du das Projekt in deiner Entwicklungsumgebung geöffnet hast.
3. Die 'Main'-Klasse wird unter folgendem Pfad gefunden`: ```Mill/src/Main.java```
4. Starte das Spiel, indem du die 'Main'-Klasse ausführst.
   * Um das Spiel im Light View(Starteinstellung) zu starten stelle sicher, dass ```var board = new BoardDay();``` nicht auskommentiert ist, sondern ```var board = new BoardNight();```.
   * Um das Spiel im Dark View zu starten stelle sicher, dass ```var board = new BoardNight();``` nicht auskommentiert ist, sondern ```var board = new BoardDay();```.

## Screenshots
<h3>Light View:</h3>
<figure>
    <img src="images/day/startScreenDay.png" alt="StartScreenImage">
    <figcaption>Dies ist der Startbildschirm der LightView.</figcaption>
</figure>
<figure>
    <img src="images/day/playingScreenDay.png" alt="StartScreenImage">
    <figcaption>Dies ist der Spielbildschirm der LightView.</figcaption>
</figure>
<figure>
    <img src="images/day/endScreenDay.png" alt="StartScreenImage">
    <figcaption>Dies ist der Endbildschirm der LightView.</figcaption>
</figure>

<h3>Dark View:</h3>

<figure>
    <img src="images/night/startScreenNight.png" alt="StartScreenImage">
    <figcaption>Dies ist der Startbildschirm der DarkView.</figcaption>
</figure>
<figure>
    <img src="images/night/playingScreenNight.png" alt="StartScreenImage">
    <figcaption>Dies ist der Spielbildschirm der DarkView.</figcaption>
</figure>
<figure>
    <img src="images/night/endScreenNight.png" alt="StartScreenImage">
    <figcaption>Dies ist der Endbildschirm der DarkView.</figcaption>
</figure>

## Beispiel zum Starten in der JShell

Öffnet die JShell in der Konsole mit:

    jshell.exe --class-path .\out\production\Mill 

Importiert das Model und startet ein neues Spiel:

    import Model.*
    Game game = new Game()
    game.startNewGame(false)
    game
    
Setzt einige Spielsteine während der Setzphase:

    game.placePiece(9)  
    game.switchPlayer()
    game.placePiece(1)  
    game.switchPlayer()
    game.placePiece(10)  
    game.switchPlayer()
    game.placePiece(13)  
    game.switchPlayer()
    game.placePiece(8)  
    game

Beim setzen ist eine Mühle entstanden Spieler 1 kann nun ein Stein des Gegners entfernen:

    game.findMill(game.getCurrentPlayer())
    game.removePiece(13)
    game
