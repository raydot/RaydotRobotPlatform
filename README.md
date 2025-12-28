# AntBot Robot Controller - Android App

Android application for controlling the AntBot tank robot via Bluetooth with FPV camera streaming capability.

## Features

- **Bluetooth Control** - Connect to HC-05 module on robot
- **Device Discovery** - Automatic Bluetooth device scanning and pairing
- **Motor Control** - Forward, backward, left/right tank turns, stop
- **Servo Control** - Tilt camera/sensor mechanism
- **Sensor Monitoring** - Real-time ultrasonic distance readings
- **Camera Streaming** - FPV (First Person View) capability (planned)
- **Experimentation Platform** - Foundation for ML/RL experiments

## Requirements

- Android 5.0 (API 21) or higher
- Bluetooth-enabled Android device
- HC-05 Bluetooth module on robot (paired)

## Setup

1. **Clone repository:**
```bash
   git clone <repository-url>
   cd RobotController
```

2. **Open in Android Studio:**
   - File → Open → select `RobotController` directory
   - Sync Gradle files

3. **Connect Android device:**
   - Enable USB debugging (Settings → Developer Options)
   - Connect via USB
   - Allow USB debugging when prompted

4. **Build and run:**
   - Click Run (green triangle) or Shift+F10
   - App installs and launches on device

## Usage

1. **Power on robot** - Ensure Arduino and HC-05 are powered
2. **Launch app** on Android device
3. **Scan for devices** - Tap "Scan for Bluetooth Devices"
4. **Select HC-05** from list (may appear as "HC-05", "HC-06", or generic name)
5. **Pair if needed** - Enter PIN 1234 or 0000 if prompted
6. **Tap device** to connect
7. **Control robot** - Use Forward, Backward, Left, Right, Stop buttons

## Communication Protocol

**Bluetooth Serial (9600 baud):**

**Commands sent to Arduino:**
- `F<speed>\n` - Forward (e.g., `F200\n`)
- `B<speed>\n` - Backward
- `L<speed>\n` - Turn left
- `R<speed>\n` - Turn right
- `STOP\n` - Stop motors
- `S<angle>\n` - Servo angle (e.g., `S90\n`)

**Data received from Arduino:**
- `D:<distance>` - Distance in inches (e.g., `D:15`)

## Architecture

**BluetoothManager.kt** - Handles Bluetooth connection, device discovery, and serial communication

**MainActivity.kt** - UI and user interaction, sends commands and displays sensor data

**Future Modules:**
- Camera streaming (IP Webcam integration or custom implementation)
- Computer vision (TensorFlow Lite)
- Reinforcement learning agent integration
- Autonomous navigation modes

## Project Structure
```
app/
├── src/
│   └── main/
│       ├── java/com/yourpackage/robotcontroller/
│       │   ├── MainActivity.kt
│       │   └── BluetoothManager.kt
│       ├── res/
│       │   └── layout/
│       │       └── activity_main.xml
│       └── AndroidManifest.xml
└── build.gradle
```

## Permissions

Required permissions in `AndroidManifest.xml`:
- `BLUETOOTH` - Bluetooth communication
- `BLUETOOTH_ADMIN` - Bluetooth device discovery
- `CAMERA` - FPV streaming (future)
- `INTERNET` - Network streaming (future)
- `ACCESS_NETWORK_STATE` - Network status (future)

## Development

**Language:** Kotlin

**Minimum SDK:** 21 (Android 5.0 Lollipop)

**Target SDK:** 34 (Android 14)

**Dependencies:**
- AndroidX AppCompat
- Material Design Components

## Known Issues

- Some Android versions hide Bluetooth MAC addresses in settings
- Bluetooth connection may require app restart if pairing fails
- Background Bluetooth may disconnect on some devices (Android battery optimization)

## Future Enhancements

- [ ] Camera preview and streaming to remote device
- [ ] On-device TensorFlow Lite inference
- [ ] Autonomous navigation modes
- [ ] Joystick control (replace discrete buttons)
- [ ] Sensor data visualization
- [ ] Recording and playback of control sequences
- [ ] RL agent integration

## Companion Arduino Code

Arduino sketches for robot controller available in separate repository.

## License

MIT License - See LICENSE file for details

## Contributing

This is an experimentation platform. Feel free to fork and add your own features!