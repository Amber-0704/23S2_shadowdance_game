# ShadowDance Game

A rhythm-based game implemented in Java, developed as **Project 2** for the Object Oriented Software Development course.  
Players hit notes in sync with the music, featuring multiple note types, enemies, power-ups, and special game mechanics.

---

## File Structure
```
.
├── res/                        # Game resources  
│   ├── track1.wav              # Music track 1  
│   ├── track2.wav              # Music track 2  
│   ├── track3.wav              # Music track 3  
│   ├── level1.csv              # Level 1 note map  
│   ├── level2.csv              # Level 2 note map  
│   ├── level3.csv              # Level 3 note map  
│   ├── test1.csv               # Test map 1  
│   ├── test2.csv               # Test map 2  
│   ├── test3.csv               # Test map 3  
│   ├── background.png          # Game background  
│   ├── arrow.PNG               # Arrow indicator  
│   ├── enemy.PNG               # Enemy sprite  
│   ├── guardian.PNG            # Guardian sprite  
│   ├── laneDown.PNG            # Down lane sprite  
│   ├── laneLeft.png            # Left lane sprite  
│   ├── laneRight.png           # Right lane sprite  
│   ├── laneSpecial.PNG         # Special lane sprite  
│   ├── laneUp.PNG              # Up lane sprite  
│   ├── noteUp.png              # Up note sprite  
│   ├── noteDown.png            # Down note sprite  
│   ├── noteLeft.png            # Left note sprite  
│   ├── noteRight.png           # Right note sprite  
│   ├── noteBomb.PNG            # Bomb note sprite  
│   ├── note2x.PNG              # Double score note sprite  
│   ├── noteSpeedUp.png         # Speed-up note sprite  
│   ├── noteSlowDown.PNG        # Slow-down note sprite  
│   └── FSO8BITR.TTF            # Game font  
├── src/                        # Source code  
│   ├── Accuracy.java           # Accuracy calculation  
│   ├── BombNote.java           # Bomb note class  
│   ├── DoubleScoreNote.java    # Double score note class  
│   ├── Enemy.java              # Enemy behavior  
│   ├── Entity.java             # Base entity class  
│   ├── Guardian.java           # Guardian behavior  
│   ├── HoldNote.java           # Hold note class  
│   ├── Lane.java               # Lane management  
│   ├── NormalNote.java         # Normal note class  
│   ├── Note.java               # Base note class  
│   ├── Projectile.java         # Projectile class  
│   ├── ShadowDance.java        # Main game logic  
│   ├── SlowDownNote.java       # Slow-down note class  
│   ├── SpeedUpNote.java        # Speed-up note class  
│   └── Track.java              # Track management  
├── target/                     # Compiled output (ignored in .gitignore)  
└── pom.xml                     # Maven configuration file

```

## Usage
1. **Clone the repository**
   ```bash
   git clone https://github.com/Amber-0704/23S2_shadowdance_game.git
   cd 23S2_shadowdance_game
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the game**
   ```bash
   mvn exec:java -Dexec.mainClass="ShadowDance"
   ```

---

## Features
- Multiple **note types**: Normal, Hold, Bomb, Speed Up, Slow Down, Double Score.
- **Lane mechanics** for four directions plus a special lane.
- **Enemies and guardians** with projectile attacks.
- CSV-based **level design** for easy customisation.
- Pixel art sprites and retro game font.

---

## Requirements
- **Java 17** or above  
- **Maven 3.6+**  
- Audio output capability  

---

## Authors
Developed by Muhan Chu as part of the University of Melbourne **Object Oriented Software Development** course.
