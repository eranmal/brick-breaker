# Brick Breaker - Java Edition 🕹️

A classic, fully functional Brick Breaker game developed in Java. This project emphasizes **Object-Oriented Programming (OOP)**, implementation of **Design Patterns**, and custom **GUI & Physics engine** logic.

---

## 📺 Demo
Check out the game in action:
<video src="https://github.com/user-attachments/assets/d09bc3d8-ea59-4f56-be9d-895c8e2a8677.mp4" width="100%" autoplay loop muted>
</video>


---

## 🚀 Key Features
* **Custom Physics Engine:** Precise collision detection based on geometric intersection (Lines, Rectangles, and Circles).
* **Design Patterns:** Extensive use of the **Observer Pattern** (`HitListener`, `HitNotifier`) to decouple game logic from the rendering engine.
* **Dynamic Gameplay:** Smooth paddle movement, ball velocity changes, and real-time score tracking.
* **Modular Architecture:** Cleanly separated into packages (Geometry, Sprites, Collision, etc.) for high maintainability.

---

## 🏗️ Project Structure

The project is built with modularity in mind:
* **`geometry`**: Handles points, lines, and rectangle math.
* **`collision`**: Manages collision info, velocities, and collidable objects.
* **`sprites`**: Contains all drawable objects (Ball, Paddle, Blocks, Score Indicators).
* **`observe`**: Implementation of the listener-notifier mechanism for game events.
* **`game`**: The core game loop and environment management.

---

## 🛠️ Installation & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/eranmal/brick-breaker.git
2. **Navigate to the source folder:**
   ```bash
   cd src
3. **Compile all files:**
    ```bash
    javac *.java
4. Run the main class:
    ```bash
    java Ass5Game

## 👨‍💻 About Me

**Eran** *Second-year Computer Science Student at Bar-Ilan University*

I specialize in **Java, Python, and C** development. I bring a strong background in leadership and teamwork. Currently, I am focused on mastering algorithms and building high-quality software projects.

---

### 🌐 Connect with me:
* **GitHub:** [github.com/YOUR_GITHUB_USERNAME](https://github.com/eranmal)
* **LinkedIn:** [Connect with me on LinkedIn](https://il.linkedin.com/in/eran-malachi-6797a5393)
