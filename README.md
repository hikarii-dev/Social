# Social
**Social Interaction System with Messaging & AFK**

Social is a lightweight communication plugin featuring private messaging, ignore system, and AFK status management. Perfect for improving player interactions and communication on your server.

## ✅ Features

* **Private Messaging** - Send direct messages to other players
* **Quick Reply** - `/reply` to respond to last sender
* **Ignore System** - Block unwanted messages from specific players
* **AFK Mode** - Automatic status with server-wide notifications
  * Auto-exit on movement or chat
  * Visual AFK indicator
* **Anti-Spam** - Ignore list prevents message spam
* **Message History** - Tracks last sender for quick replies

## 📦 Installation

1. Download `Social.jar`
2. Place in `/plugins` folder
3. Restart server
4. Customize messages in `config.yml`

## 🎮 Commands & Permissions

**Commands:**
* `/msg <player> <message>` - Send private message
  * Aliases: `/m`, `/tell`, `/whisper`, `/w`
* `/reply <message>` - Reply to last message
  * Alias: `/r`
* `/ignore <player>` - Ignore a player's messages
* `/unignore <player>` - Remove player from ignore list
* `/afk` - Toggle AFK status
  * Alias: `/away`

**Permissions:**
* `social.msg` - Send private messages (default: true)
* `social.reply` - Use reply command (default: true)
* `social.ignore` - Ignore players (default: true)
* `social.afk` - Use AFK mode (default: true)

## ⚙️ Configuration

```yaml
# SocialPlugin Configuration
# The list of ignored players will be automatically saved here

# Ignore storage structure:
# ignore:
# <Player UUID>:
# - <UUID of ignored player 1>
# - <UUID of ignored player 2>
# - ...
```

## 💬 Usage Examples

**Private Messaging:**
```
/msg Steve Hello!
/r How are you?
/w Alex Want to play?
```

**Ignore System:**
```
/ignore Griefer123
/unignore Griefer123
```

**AFK Mode:**
```
/afk
[Auto] Player moved → AFK disabled
```

## 🔧 Technical Details

* **Platform:** Paper/Spigot 1.21+
* **Java:** 21
* **Storage:** In-memory (ignore lists persist in config)
* **Dependencies:** None
* **Performance:** Minimal overhead

## 📝 Additional Info

* AFK auto-exits on any movement
* AFK auto-exits on chat message
* Ignore list persists across restarts
* Message formatting fully customizable
* Color code support in messages
* No database required

---

**Author:** hikarii  
**Version:** 1.0.0  
**Support:** Discord: hikarii.dev | Telegram: @hikarii_dev
