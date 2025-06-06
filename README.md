# E-Shop Client-Server

This project is a simple client-server application for managing business purchases in an e-shop. It is implemented in Java using sockets for communication and Swing for the client GUI.

## Project Structure

- `src/GUI/Business.java` - Represents a business entity.
- `src/GUI/CentralServer.java` - The server application that handles client connections and business logic.
- `src/GUI/ConnectionHandler.java` - Handles individual client connections on the server side.
- `src/GUI/GUIclient.java` - The client application with a graphical user interface.
- `src/module-info.java` - Java module descriptor.

## How It Works

- The server (`CentralServer`) listens for client connections on port 9999.
- Each client (`GUIclient`) connects to the server and can submit purchase requests for items (Sun glasses, Belt, Scarf) for a business.
- The server validates and processes requests, maintaining a list of businesses and their purchase counts.
- The client GUI provides fields for business name, business number, item selection, and amount.

## Running the Project

1. **Compile the project**  
   Make sure you have Java 22 or compatible JDK installed.
   ```
   javac -d bin src/module-info.java src/GUI/*.java
   ```

2. **Start the server**  
   ```
   java -cp bin GUI.CentralServer
   ```

3. **Start the client** (in a new terminal window, can run multiple clients)
   ```
   java -cp bin GUI.GUIclient
   ```

## Features

- Multi-client support (each client runs in its own thread).
- Input validation for business name, number, item, and amount.
- User-friendly GUI for the client.
- Server-side business tracking.

## Requirements

- Java 22 or later
- No external dependencies

## Notes

- The server must be started before any clients connect.
- All communication is done over localhost (`127.0.0.1:9999`).

---

*Created for educational purposes.*