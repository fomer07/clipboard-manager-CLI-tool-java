# Clipboard Manager CLI

A simple clipboard manager CLI tool written in Java that allows you to manage your clipboard history directly from the terminal.

## Features

- **View** clipboard history.
- **Save** new clipboard content to history.
- **Delete** clipboard entry by ID.
- **Clear** all clipboard history.
- **Restore** clipboard entry by ID.

## Installation

### Step 1: Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/fomer07/clipboard-manager-CLI-tool-java.git
cd clipboard-manager-CLI-tool-java
```

### Step 2: Build the Application with Maven

```bash
mvn clean package
```

### Step 3: Create a Global Command

#### 1. Create the Script

Open a terminal and create a new shell script:

```bash
nano ~/clipboard-cli
```

#### 2. Add the Script Content

```bash
#!/bin/bash
java -cp /path/to/your/target/clipboard-manager-1.0-SNAPSHOT.jar com.example.ClipboardCLI "$@"
```

#### 3. Save and Close the Editor

To save and close the editor, press:

- `CTRL + X`
- `Y` to confirm saving
- `ENTER` to confirm the file name

### Step 4: Make the Script Executable

Run the following command to make the script executable:

```bash
chmod +x ~/clipboard-cli
```

### Step 5: Move the Script to a Directory in Your PATH

```bash
sudo mv ~/clipboard-cli /usr/local/bin/clipboard-cli
```

### Step 6: Open your terminal and test it 

 ```bash
  clipboard-cli
  ```

## How to use it

- **View Clipboard History**:

  ```bash
  clipboard-cli view
  ```

- **Save Clipboard Content**:

  ```bash
  clipboard-cli save "My clipboard content"
  ```

- **Delete Clipboard Entry**:

  ```bash
  clipboard-cli delete <id>
  ```

- **Clear All Clipboard History**:

  ```bash
  clipboard-cli clear
  ```

- **Restore Clipboard Entry**:

  ```bash
  clipboard-cli restore <id>
  ```